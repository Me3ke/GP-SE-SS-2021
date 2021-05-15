<template>
    <div>
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
                        <b-col >
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
                                              :isActive="this.filter.open"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterClosed()">
                                <FilterButton v-bind:text="$t('OverviewPage.filterClosed')"
                                              :isActive="this.filter.closed"></FilterButton>
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
                    <div v-for="envelope in this.envelopes" :key="envelope.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <!-- Different styles for open/closed documents TODO-->
                        <div v-if="envelope.documents.length === 1">
                            <DocumentBox
                                @click.native="$router.push({name: 'document', params: {docId: envelope.documents[0].id, envId: envelope.id, userId: envelope.owner.id}})"
                                :doc="envelope.documents[0]">
                            </DocumentBox>
                        </div>
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeBox
                                @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"
                                :env="envelope" >
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
    components:{DocumentBox, EnvelopeBox, Footer, Header, FilterButton, UploadButton},
    data() {
        return {
            // Needs to be replaced with API Request TODO
            envelopes: [{
                id: 1,
                name: "Titel dieses Envelopes!",
                owner: {
                    id: 11,
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto",
                    lastname: "Wehner"
                },
                creationDate: "03.03.2021",
                documents: [
                    {
                        id: 12,
                        title: "Titel dieses Dokumentes",
                        creationDate: "03.03.2021",
                        owner: {
                            id: 11,
                            eMail: "sehrTolle@email.com",
                            firstname: "Otto",
                            lastname: "Wehner"
                        },
                        state: "open",
                        endDate: "25.05.2021",
                        dataType: "PDF",
                        signatureType: "simple",
                        signatory: true,
                        reader: false,
                        signed: false,
                        read: false
                    },
                    {
                        id: 13,
                        title: "Titel dieses Dokumentes",
                        creationDate: "03.03.2021",
                        owner: {
                            id: 11,
                            eMail: "sehrTolle@email.com",
                            firstname: "Otto",
                            lastname: "Wehner"
                        },
                        state: "open",
                        endDate: "25.05.2021",
                        dataType: "PDF",
                        signatureType: "simple",
                        signatory: true,
                        reader: false,
                        signed: false,
                        read: false
                    }
                ]
            }, {
                id: 2,
                name: "Titel dieses tollen Envelopes!",
                owner: {
                    id: 11,
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto",
                    lastname: "Wehner"
                },
                creationDate: "03.03.2021",
                documents: [
                    {
                        id: 21,
                        title: "Titel dieses Dokumentes",
                        creationDate: "03.03.2021",
                        owner: {
                            id: 11,
                            eMail: "sehrTolle@email.com",
                            firstname: "Otto",
                            lastname: "Wehner"
                        },
                        state: "closed",
                        endDate: "05.05.2021",
                        dataType: "PDF",
                        signatureType: "simple",
                        signatory: true,
                        reader: false,
                        signed: false,
                        read: false
                    }
                ]
            }],
            filters: {
                open: false,
                closed: false
            }
        }
    },
    methods: {
        filter() {
            // Needs to be replaced with API Request TODO
            if (this.filter.open) {
                this.envelopes = [{
                    id: 1,
                    name: "Titel dieses Envelopes!",
                    owner: {
                        id: 11,
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    creationDate: "03.03.2021",
                    documents: [
                        {
                            id: 12,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "open",
                            endDate: "25.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        },
                        {
                            id: 13,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "open",
                            endDate: "25.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        }
                    ]
                }]
            } else if (this.filter.closed) {
                this.envelopes = [{
                    id: 2,
                    name: "Titel dieses tollen Envelopes!",
                    owner: {
                        id: 11,
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    creationDate: "03.03.2021",
                    documents: [
                        {
                            id: 21,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "closed",
                            endDate: "05.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        }
                    ]
                }]
            } else {
                this.envelopes = [{
                    id: 1,
                    name: "Titel dieses Envelopes!",
                    owner: {
                        id: 11,
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    creationDate: "03.03.2021",
                    documents: [
                        {
                            id: 12,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "open",
                            endDate: "25.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        },
                        {
                            id: 13,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "open",
                            endDate: "25.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        }
                    ]
                }, {
                    id: 2,
                    name: "Titel dieses tollen Envelopes!",
                    owner: {
                        id: 11,
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    creationDate: "03.03.2021",
                    documents: [
                        {
                            id: 21,
                            title: "Titel dieses Dokumentes",
                            creationDate: "03.03.2021",
                            owner: {
                                id: 11,
                                eMail: "sehrTolle@email.com",
                                firstname: "Otto",
                                lastname: "Wehner"
                            },
                            state: "closed",
                            endDate: "05.05.2021",
                            dataType: "PDF",
                            signatureType: "simple",
                            signatory: true,
                            reader: false,
                            signed: false,
                            read: false
                        }
                    ]
                }]
            }
        },
        // Change filter and make sure closed and open filter is not activated at the same time
        filterOpen() {
            this.filter.open = !this.filter.open;
            if (this.filter.closed && this.filter.open) {
                this.filter.closed = false;
            }
            this.filter();
        },
        filterClosed() {
            this.filter.closed = !this.filter.closed;
            if (this.filter.open && this.filter.closed) {
                this.filter.open = false;
            }
            this.filter();
        }
    }
}
</script>

<style scoped>
</style>
