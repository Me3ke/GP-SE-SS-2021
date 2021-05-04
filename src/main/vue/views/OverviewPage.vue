<template>
    <div>
        <div>
            <Header></Header>
        </div>
        <!-- Title + Searchbar TODO -->
        <div style="text-align: left; margin-top: 3%; margin-left: 3vw;">
            <h1>
                Übersicht
            </h1>
        </div>
        <!-- Here: Filters and Shit TODO -->
        <div style="margin-top:0.5vw; position:static">
            Filters and Shit
        </div>
        <!-- Documents TODO -->
        <div style="margin-top:0.5vw;">
            <div v-for="envelope in envelopes" :key="envelope.id" style="position: static; margin-top: 1vw; margin-left: 3vw;">
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
        <Footer></Footer>
    </div>
</template>

<script>
import DocumentBox from "@/main/vue/components/DocumentBox";
import EnvelopeBox from "@/main/vue/components/EnvelopeBox";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/Header";
export default {
    name: "OverviewPage",
    components:{DocumentBox, EnvelopeBox, Footer, Header},
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
                        name:"Titel dieses wahnisnnig tollen Dokumentes",
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
