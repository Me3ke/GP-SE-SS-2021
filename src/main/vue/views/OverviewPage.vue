<template>
    <div>
        <div>
            <Header></Header>
        </div>
        <!-- Title + Searchbar TODO -->
        <b-container fluid style="margin-top:6vh; margin-right:2vw; text-align: left">
            <b-row align-h="start">
                <b-col>
                    <h2>
                        {{$t('OverviewPage.heading')}}
                    </h2>
                </b-col>
            </b-row>
        </b-container>

        <b-container fluid style="margin-top:2vh; margin-right:2vw; text-align: left">
            <b-row align-h="between" no-gutters>
                <div class="col-auto">
                    <b-row align-h="start">
                        <b-col >
                            Hochladen
                        </b-col>
                    </b-row>
                </div>
                <div class="col-aut">
                    <b-row align-h="end">
                        <b-col >
                            <FilterButton v-bind:text="$t('OverviewPage.filterOpen')"></FilterButton>
                        </b-col>
                        <b-col >
                            <FilterButton v-bind:text="$t('OverviewPage.filterClosed')"></FilterButton>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>

        <!-- Documents TODO -->

        <div class="container-fluid">
            <div style="margin-top:1vh">
                <div class="overflow-auto" style="height: 71.75vh">
                    <div v-for="envelope in envelopes" :key="envelope.id" style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <div v-if="envelope.documents.length === 1">
                            <DocumentBox :doc="envelope.documents[0]">
                            </DocumentBox>
                        </div>
                        <div v-if="!(envelope.documents.length === 1)">
                            <EnvelopeBox :env="envelope">s
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
//import FilterButton from "@/main/vue/components/FilterButton";
export default {
    name: "OverviewPage",
    components:{DocumentBox, EnvelopeBox, Footer, Header, FilterButton},
    data() {
        return {
            // Needs to be replaced with API Request TODO
            envelopes:[{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 1,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:11,
                    name:"Owner of Envelope 1"
                },
                dateCreated:"03.03.2021",
                documents:[
                    {
                        id:12,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:121,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:122,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:123,
                            name:"Rea Der"
                        }]
                    },
                    {
                        id:13,
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:131,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:132,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:133,
                            name:"Rea Der"
                        }]
                    }
                ]
            },{
                id: 2,
                name:"Titel dieses tollen Envelopes!",
                owner: {
                    id:21,
                    name:"Owner of Envelope 2"
                },
                dateCreated:"03.03.2021",
                documents: [
                    {
                        id:21,
                        name:"Titel dieses Dokumentes",
                        dateCreated:"03.03.2021",
                        owner: {
                            id:211,
                            name:"Otto Wehner"
                        },
                        state:"open",
                        signatories:[{
                            id:212,
                            name:"Sig Natory"
                        }],
                        readers:[{
                            id:213,
                            name:"Rea Der"
                        }]
                    }
                ]
            }]
        }
    }
}
</script>

<style scoped>
</style>
