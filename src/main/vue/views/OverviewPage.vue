<template>
    <div>
        <WelcomePopUp v-if="!user.firstLogin" @welcomeTrigger="setLogin"></WelcomePopUp>

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
                            <b-input-group>
                                <b-form-input v-model="searchInput" :placeholder="$t('OverviewPage.search')"></b-form-input>
                                <h4>
                                    <b-icon class="searchIcon" icon="search" style="margin: 0 0.5em" @click="search(searchInput);"></b-icon>
                                </h4>
                            </b-input-group>
                        </b-col>
                        <b-col>
                            <FilterMenu :filter="filter"></FilterMenu>
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
                            <UploadButton></UploadButton>
                        </b-col>
                    </b-row>
                </div>
                <div class="col-aut">
                    <b-row align-h="end">
                        <!-- Filter Buttons -->
                        <b-col>
                            <span @click="filterOpen()">
                                <FilterButton v-bind:text="$t('Filter.open')"
                                              :isActive="this.filter.state === 'OPENREAD'" ></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterClosed()">
                                <FilterButton v-bind:text="$t('Filter.closed')"
                                              :isActive="this.filter.state === 'CLOSED'"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterOwn()">
                                <FilterButton v-bind:text="$t('Filter.owned')"
                                    :isActive="this.filter.owner"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterSignatory()">
                                <FilterButton v-bind:text="$t('Filter.toSign')"
                                              :isActive="this.filter.signatory && this.filter.reader" ></FilterButton>
                            </span>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>

        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:1vh; background-color: var(--whitesmoke); border-color: var(--whitesmoke); border-top-color: var(--light-grey); border-radius: 0" class="card" >
                <div class="overflow-auto" style="height: 68vh">
                    <div v-for="envelope in this.envelopes(filter, pageLimit, page)"
                         :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeCard :envelope=envelope></EnvelopeCard>
                        </div>
                        <div v-if="envelope.documents.length === 1">
                            <DocumentCard :document=envelope.documents[0] :envelopeId="envelope.id" :show-progress="false"></DocumentCard>
                        </div>
                    </div>
                    <div v-if="this.envelopes(filter, pageLimit, page).length === 0" style="color:var(--dark-grey); padding:1em">
                        <h4>
                            {{$t('OverviewPage.noDocuments')}}
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
import FilterButton from "@/main/vue/components/FilterButton";
import UploadButton from "@/main/vue/components/UploadMenu";
import {mapGetters} from "vuex";
import DocumentCard from "@/main/vue/components/DocumentCard";
import EnvelopeCard from "@/main/vue/components/EnvelopeCard";
import WelcomePopUp from "@/main/vue/components/popUps/WelcomePopUp";
import VueConfetti from 'vue-confetti'
import Vue from 'vue'
import FilterMenu from "@/main/vue/components/FilterMenu";

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
        FilterMenu
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
                dataType: ""
            },
            searchInput: "",
            pageLimit: 10,
            page: 1,
            sort: null
        }
    },
    methods: {
        // Change filter and make sure closed and open filter is not activated at the same time
        filterOpen() {
            if (this.filter.state === "" || this.filter.state === "CLOSED") {
                this.filter.state = "OPENREAD";
            } else {
                this.filter.state = "";
            }
        },
        filterClosed() {
            if (this.filter.state === "" || this.filter.state === "OPENREAD") {
                this.filter.state = "CLOSED";
            } else {
                this.filter.state = "";
            }
        },
        filterSignatory() {
            if ((!this.filter.signatory || !this.filter.reader) && this.filter.state === "CLOSED") {
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
        search(keyword) {
            this.filter.search = keyword
            if(keyword.toLowerCase().includes("schildkröte")|| keyword.toLowerCase().includes("maskottchen")) {
                this.launchMascots();
            } else if(keyword.toLowerCase().includes("erleben, was verbindet")|| (keyword.toLowerCase().includes("magenta") && keyword.toLowerCase().includes("liebe"))) {
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
                        type:'circle',
                        size: 10,
                    },
                    {
                        type:'rect',
                        size:10,
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
                        type:'circle',
                        size: 15,
                        colors: ['#ea0a8e']
                    },
                    {
                        type:'rect',
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
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
        this.$store.dispatch('fetchUser')
    },

    beforeDestroy() {
        this.$store.dispatch('document/resetState')
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

.searchIcon:hover {
    fill: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
