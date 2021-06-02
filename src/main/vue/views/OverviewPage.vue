<template>
    <div style="background-color: var(--whitesmoke); height: 100vh; overflow: hidden">
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
                <div class="overflow-auto" style="height: 68vh">
                    <div v-for="envelope in this.getEnvs(this.filters.open, this.filters.closed, false)"
                         :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <!-- Different styles for open/closed documents TODO-->
                        <div v-if="envelope.documents.length === 1">
                            <!-- Default -->
                            <div
                                v-if="(envelope.documents[0].signatory === false || envelope.documents[0].signed === true)
                                && (envelope.documents[0].reader === false || envelope.documents[0].read === true)
                                && envelope.documents[0].state === 'open'">
                                <DocumentBox
                                    @click.native="$router.push({name: 'document', params: {docId: envelope.documents[0].id, envId: envelope.id}})"
                                    :doc="envelope.documents[0]">
                                </DocumentBox>
                            </div>

                            <!-- Closed -->
                            <div
                                v-if="envelope.documents[0].state === 'closed'">
                                <DocumentBoxClosed
                                    @click.native="$router.push({name: 'document', params: {docId: envelope.documents[0].id, envId: envelope.id}})"
                                    :doc="envelope.documents[0]">
                                </DocumentBoxClosed>
                            </div>

                            <!-- To sign/read -->
                            <div
                                v-if="((envelope.documents[0].signatory === true && envelope.documents[0].signed === false)
                                || (envelope.documents[0].reader === true && envelope.documents[0].read === false))
                                && envelope.documents[0].state === 'open'">
                                <DocumentBoxSignRead
                                    @click.native="$router.push({name: 'document', params: {docId: envelope.documents[0].id, envId: envelope.id}})"
                                    :doc="envelope.documents[0]">
                                </DocumentBoxSignRead>
                            </div>
                        </div>
                        <div v-if="!(envelope.documents.length === 1)">
                            <!-- Default -->
                            <div
                                v-if="(getEnvData(envelope).needToSign === false)
                                && getEnvData(envelope).needToRead === false
                                && getEnvData(envelope).open === true">
                                <EnvelopeBox
                                    @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"
                                    :env="envelope">
                                </EnvelopeBox>
                            </div>

                            <!-- Closed -->
                            <div
                                v-if="getEnvData(envelope).open === false">
                                <EnvelopeBoxClosed
                                    :env="envelope">
                                </EnvelopeBoxClosed>
                            </div>

                            <!-- To sign/read -->
                            <div
                                v-if="(getEnvData(envelope).needToSign === true
                                || getEnvData(envelope).needToRead === true)
                                && getEnvData(envelope).open === true">
                                <EnvelopeBoxSignRead
                                    @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"
                                    :env="envelope">
                                </EnvelopeBoxSignRead>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import DocumentBox from "@/main/vue/components/documentBoxes/DocumentBox";
import EnvelopeBox from "@/main/vue/components/envelopeBoxes/EnvelopeBox";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import FilterButton from "@/main/vue/components/FilterButton";
import UploadButton from "@/main/vue/components/UploadMenu";
import DocumentBoxClosed from "@/main/vue/components/documentBoxes/DocumentBoxClosed";
import DocumentBoxSignRead from "@/main/vue/components/documentBoxes/DocumentBoxSignRead";
import EnvelopeBoxClosed from "@/main/vue/components/envelopeBoxes/EnvelopeBoxClosed";
import EnvelopeBoxSignRead from "@/main/vue/components/envelopeBoxes/EnvelopeBoxSignRead";

export default {
    name: "OverviewPage",
    components: {
        DocumentBox,
        DocumentBoxClosed,
        DocumentBoxSignRead,
        EnvelopeBox,
        EnvelopeBoxClosed,
        EnvelopeBoxSignRead,
        Footer,
        Header,
        FilterButton,
        UploadButton
    },
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
        },
        getEnvData(env) {
            let open = false;
            let toSign = false;
            let toRead = false;
            let i;
            for (i = 0; i < env.documents.length; i++) {
                if (env.documents[i].state === "open") {
                    open = true;
                }
                if (env.documents[i].signatory === true && env.documents[i].signed === false) {
                    toSign = true
                }
                if (env.documents[i].reader === true && env.documents[i].read === false) {
                    toRead = true
                }
            }
            return {open: open, needToSign: toSign, needToRead: toRead};
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
