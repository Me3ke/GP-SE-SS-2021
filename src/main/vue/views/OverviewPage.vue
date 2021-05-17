<template>
    <div style="background-color: var(--whitesmoke); height: 100vh">
        <div>
            <Header></Header>
        </div>
        <!-- Page Title -->
        <b-container fluid style="margin-top:6vh; margin-right:2vw; text-align: left">
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
                                              :isActive="this.filters.open"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterClosed()">
                                <FilterButton v-bind:text="$t('OverviewPage.filterClosed')"
                                              :isActive="this.filters.closed"></FilterButton>
                            </span>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>
        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:1vh">
                <div class="overflow-auto" style="height: 71.75vh">
                    <div v-for="envelope in this.getEnvs(this.filters.open, this.filters.closed, false)"
                         :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <!-- Different styles for open/closed documents TODO-->
                        <div v-if="envelope.documents.length === 1">
                            <DocumentBox
                                @click.native="$router.push({name: 'document', params: {docId: envelope.documents[0].id, envId: envelope.id, userId: envelope.owner.eMail}})"
                                :doc="envelope.documents[0]">
                            </DocumentBox>
                        </div>
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeBox
                                @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"
                                :env="envelope">
                            </EnvelopeBox>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import DocumentBox from "@/main/vue/components/DocumentBox";
import EnvelopeBox from "@/main/vue/components/EnvelopeBox";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import FilterButton from "@/main/vue/components/FilterButton";
import UploadButton from "@/main/vue/components/UploadMenu";

export default {
    name: "OverviewPage",
    components: {DocumentBox, EnvelopeBox, Footer, Header, FilterButton, UploadButton},
    data() {
        return {
            // Needs to be replaced with API Request TODO
            filters: {
                open: false,
                closed: false
            }
        }
    },
    methods: {
        // Change filter and make sure closed and open filter is not activated at the same time
        filterOpen() {
            this.filters.open = !this.filters.open;
            if (this.filters.closed && this.filters.open) {
                this.filters.closed = false;
            }
            this.envelopes = this.getEnvs(this.filters.open, this.filters.closed, false);
        },
        filterClosed() {
            this.filters.closed = !this.filters.closed;
            if (this.filters.open && this.filters.closed) {
                this.filters.open = false;
            }
            this.envelopes = this.getEnvs(this.filters.open, this.filters.closed, false);
        }
    },
    computed: {
        getEnvs() {
            return this.$store.getters.getEnvelopesFiltered
        }
    }
}

</script>

<style scoped>
</style>
