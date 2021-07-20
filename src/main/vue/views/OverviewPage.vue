<template>
    <div>
        <WelcomePopUp v-if="loaded && !user.firstLogin" @welcomeTrigger="setLogin"></WelcomePopUp>

        <Header></Header>

        <!-- Page Title -->
        <b-container fluid style="margin-top:10vh; margin-right:2vw; text-align: left">
            <b-row align-h="between">
                <b-col cols="auto">
                    <h2>
                        {{ $t('OverviewPage.heading') }}
                    </h2>
                </b-col>
                <b-col cols="auto">
                    <b-row>
                        <b-col>
                            <!-- Searchbar -->
                            <b-input-group>
                                <b-input-group-prepend is-text @click="search()">
                                    <b-icon icon="search" id="search" style="fill: var(--elsa-blue); margin: 0 0.5em; cursor: pointer;"></b-icon>
                                </b-input-group-prepend>
                                <b-form-input v-model="filter.search"
                                              :placeholder="$t('OverviewPage.search')" type="search"></b-form-input>
                            </b-input-group>
                        </b-col>
                        <b-col>
                            <FilterMenu :filter="filter" style="cursor: pointer;"></FilterMenu>
                        </b-col>
                        <b-col>
                            <SortMenu @updateSort="updateSort" style="cursor: pointer;" :first="this.filter.sortFirst"
                                      :second="this.filter.sortSecond"></SortMenu>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>

        <!-- Upload and Filter Buttons -->
        <b-container fluid style="margin-top:2vh; margin-right:2vw; text-align: left">
            <b-row align-h="between" no-gutters>
                <div class="col-auto">
                    <b-row align-h="start">
                        <b-col>
                            <UploadButton @refreshOverview="refreshPage"></UploadButton>
                        </b-col>
                    </b-row>
                </div>
                <div class="col-aut">
                    <b-row align-h="end">
                        <!-- Filter Buttons -->
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.open')" style="cursor: pointer;"
                                              :isActive="this.filter.state === 'REVIEWSIGN'"
                                              :switch="true"
                                              @activeChange="filterOpen()"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.closed')" style="cursor: pointer;"
                                              :isActive="this.filter.state === 'ARCHIVED'"
                                              :switch="true"
                                              @activeChange="filterClosed()"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.owned')" style="cursor: pointer;"
                                              :isActive="this.filter.owner"
                                              @activeChange="filterOwn()"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.toSign')" style="cursor: pointer;"
                                              :isActive="this.filter.signatory && this.filter.reader"
                                              @activeChange="filterSignatory()"></FilterButton>
                            </span>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>

        <!-- Documents -->
        <div class="container-fluid">
            <div
                style="margin-top:1vh; background-color: var(--whitesmoke); border-color: var(--whitesmoke); border-top-color: var(--light-grey); border-radius: 0"
                class="card">
                <div class="overflow-auto" style="height: 68vh">
                    <div v-for="envelope in this.envelopes(filter, pageLimit, page)"
                         :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeCard :envelope=envelope></EnvelopeCard>
                        </div>
                        <div v-if="envelope.documents.length === 1">
                            <DocumentCard :document=envelope.documents[0] :envelopeId="envelope.id"
                                          :show-progress="false"></DocumentCard>
                        </div>
                    </div>
                    <div v-if="this.envelopes(filter, pageLimit, page).length === 0"
                         style="color:var(--dark-grey); padding:1em">
                        <h4>
                            {{ $t('OverviewPage.noDocuments') }}
                        </h4>
                    </div>
                </div>
            </div>
        </div>

        <b-pagination
            v-model="page"
            :total-rows="this.allEnvelopes(filter).length"
            :per-page="pageLimit"
            style="margin: 1em 1.5em"
        ></b-pagination>
        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import FilterButton from "@/main/vue/components/overviewPage/FilterButton";
import UploadButton from "@/main/vue/components/uploadDocuments/UploadMenu";
import {mapGetters} from "vuex";
import DocumentCard from "@/main/vue/components/overviewPage/DocumentCard";
import EnvelopeCard from "@/main/vue/components/overviewPage/EnvelopeCard";
import WelcomePopUp from "@/main/vue/components/popUps/WelcomePopUp";
import VueConfetti from 'vue-confetti'
import Vue from 'vue'
import SortMenu from "@/main/vue/components/SortMenu";
import FilterMenu from "@/main/vue/components/overviewPage/FilterMenu";

Vue.use(VueConfetti)
export default {
    name: "OverviewPage",
    components: {
        WelcomePopUp,
        Footer,
        Header,
        FilterButton,
        UploadButton,
        EnvelopeCard,
        DocumentCard,
        FilterMenu,
        SortMenu
    },
    data() {
        return {
            filter: {
                state: "",
                owner: false,
                search: "",
                signatory: false,
                reader: false,
                creationDateMin: "",
                creationDateMax: "",
                endDateMin: "",
                endDateMax: "",
                dataType: "",
                sortFirst: "state",
                sortSecond: "end"
            },
            pageLimit: 10,
            page: 1,
            sort: null,

            // needed for WelcomePopUp, so it does not always appear and then instantly disappear
            // because data has not been completely fetched yet
            loaded: false,
        }
    },
    methods: {
        // Refreshing after a document was uploaded
        async refreshPage() {
            await this.$store.dispatch('envelopes/fetchEnvelopes')
        },
        updateSort: function (sortFirst, sortSecond) {
            this.filter.sortFirst = sortFirst;
            this.filter.sortSecond = sortSecond;
        },
        // Change filter and make sure closed and open filter is not activated at the same time
        filterOpen() {
            if (this.filter.state === "" || this.filter.state === "ARCHIVED") {
                this.filter.state = "REVIEWSIGN";
            } else {
                this.filter.state = "";
            }
        },
        filterClosed() {
            if (this.filter.state === "" || this.filter.state === "REVIEWSIGN") {
                this.filter.state = "ARCHIVED";
            } else {
                this.filter.state = "";
            }
        },
        filterSignatory() {
            if ((!this.filter.signatory || !this.filter.reader) && this.filter.state === "ARCHIVED") {
                this.filter.state = "";
                this.filter.signatory = true;
                this.filter.reader = true;
            } else if (!this.filter.signatory || !this.filter.reader) {
                this.filter.signatory = true
                this.filter.reader = true
            } else {
                this.filter.signatory = false
                this.filter.reader = false
            }
        },
        filterOwn() {
            this.filter.owner = !this.filter.owner
        },
        search() {
            if (this.filter.search.toLowerCase().includes("schildkröte") || this.filter.search.toLowerCase().includes("maskottchen")) {
                this.launchMascots();
            } else if (this.filter.search.toLowerCase().includes("erleben, was verbindet") || (this.filter.search.toLowerCase().includes("magenta") && this.filter.search.toLowerCase().includes("liebe"))) {
                this.launchMagenta();
            }
        },
        launchMascots() {
            this.$confetti.start();
            this.$confetti.update({
                particles: [
                    {
                        type: 'image',
                        url: 'https://i.ibb.co/zQhz9Dq/turtle.png',
                        size: 20,
                    },
                    {
                        type: 'circle',
                        size: 10,
                    },
                    {
                        type: 'rect',
                        size: 10,
                    }
                ],
            });
            window.setTimeout(() => (this.$confetti.stop()), 5000);
        },
        launchMagenta() {
            this.$confetti.start();
            this.$confetti.update({
                particles: [
                    {
                        type: 'image',
                        url: 'https://logodownload.org/wp-content/uploads/2019/11/deutsche-telekom-logo-0.png',
                        size: 40,
                    },
                    {
                        type: 'circle',
                        size: 15,
                        colors: ['#ea0a8e']
                    },
                    {
                        type: 'rect',
                        size: 15,
                        colors: ['#ea0a8e']
                    }
                ],
            });
            window.setTimeout(() => (this.$confetti.stop()), 5000);
        },
        async setLogin() {
            await this.$store.dispatch('putFirstLogin')
            await this.$store.dispatch('fetchUser')
        }
    },
    async mounted() {
        await this.$store.dispatch('envelopes/fetchEnvelopes')
        await this.$store.dispatch('fetchUser')
        await this.$store.dispatch('impressum/fetchImpressum')
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('document/resetState')
        this.loaded = false
    },

    // TODO REMINDER:
    //  Error creating the progress bar because of not updated progress state on overview page
    //  but after switching to the document page and then go back to the overview page it will getting
    // updated (onur)

    computed: {
        ...mapGetters({
            envelopes: 'envelopes/getFilteredPagedEnvelopes',
            allEnvelopes: 'envelopes/getFilteredEnvelopes',
            getError: 'envelopes/getErrorGetEnvelopes',
            user: 'getUser',
        })
    }
}

</script>

<style scoped>
.input-group {
    width: 20em;
}

.input-group:not(.has-validation) > .form-control:not(:last-child), .input-group:not(.has-validation) > .custom-select:not(:last-child), .input-group:not(.has-validation) > .custom-file:not(:last-child) .custom-file-label::after {
    border-radius: 0.5em;
    background-color: var(--whitesmoke);
}

/* Search bar */
#search:hover {
    cursor: pointer;
    fill: var(--light-grey);
    transition-duration: 0.4s;
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: solid 0.1em var(--elsa-blue);
    border-radius: 0.2em;
}

.input-group > .input-group-prepend > .input-group-text {
    fill: var(--elsa-blue);
    margin-top: 0;
}

.form-control:focus {
    color: var(--dark-grey);
    background-color: var(--whitesmoke);
    border-color: var(--elsa-blue);
    outline: 0;
    box-shadow: 0 0 0 0.2rem var(--elsa-blue-transparent);
}

.form-control::placeholder {
    color: var(--shadow-grey);
    opacity: 1; /* Firefox */
}

.form-control:-ms-input-placeholder {
    color: var(--shadow-grey);
}

.form-control::-ms-input-placeholder {
    color: var(--shadow-grey);
}
</style>
