<template>
    <div>

        <WelcomePopUp v-if="!user.firstLogin" @welcomeTrigger="setLogin"></WelcomePopUp>

        <Header></Header>

        <!-- Page Title -->
        <b-container fluid style="margin-top:10vh; margin-right:2vw; text-align: left">
            <b-row align-h="start">
                <b-col>
                    <h2>
                        {{ $t('OverviewPage.heading') }}
                    </h2>
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
                                <FilterButton v-bind:text="$t('OverviewPage.filterOpen')"
                                              :isActive="this.filter.state === 'open'"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterClosed()">
                                <FilterButton v-bind:text="$t('OverviewPage.filterClosed')"
                                              :isActive="this.filter.state === 'closed'"></FilterButton>
                            </span>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>

        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:1vh">
                <div class="overflow-auto" style="height: 68vh">
                    <div v-for="envelope in this.envelopes(filter, pageLimit, page)"
                         :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeCard :envelope=envelope></EnvelopeCard>
                        </div>
                        <div v-if="envelope.documents.length === 1">
                            <DocumentCard :document=envelope.documents[0] :envelopeId="envelope.id"></DocumentCard>
                        </div>
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
import UploadButton from "@/main/vue/components/overviewPage/UploadMenu";
import {mapGetters} from "vuex";
import DocumentCard from "@/main/vue/components/overviewPage/DocumentCard";
import EnvelopeCard from "@/main/vue/components/overviewPage/EnvelopeCard";
import WelcomePopUp from "@/main/vue/components/popUps/WelcomePopUp";

export default {
    name: "OverviewPage",
    components: {
        WelcomePopUp,
        Footer,
        Header,
        FilterButton,
        UploadButton,
        EnvelopeCard,
        DocumentCard
    },
    data() {
        return {
            filter: {
                //title: "",
                //envelopeID: 0,
                state: null,
                //owner: "",
                //creationDateMin: null,
                //creationDateMax: null,
                //endDateMin: null,
                //endDateMax: null,
                //signatureType: "",
                //datatype: "",
                //signatories: null,
                //readers: null,
                //signed: null,
                //read: null
            },
            pageLimit: 10,
            page: 1,
            sort: null
        }
    },
    methods: {
        // Change filter and make sure closed and open filter is not activated at the same time
        filterOpen() {
            if (this.filter.state === null || this.filter.state === "closed") {
                this.filter.state = "open";
            } else {
                this.filter.state = null;
            }
        },
        filterClosed() {
            if (this.filter.state === null || this.filter.state === "open") {
                this.filter.state = "closed";
            } else {
                this.filter.state = null;
            }
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
    computed: {
        ...mapGetters({
            envelopes: 'envelopes/getFilteredPagedEnvelopes',
            allEnvelopes: 'envelopes/getFilteredEnvelopes',
            getError: 'envelopes/getErrorGetEnvelopes',
            user: 'getUser'
        })
    }
}

</script>

<style scoped>

</style>
