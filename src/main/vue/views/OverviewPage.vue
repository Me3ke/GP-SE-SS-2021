<template>
    <div>
        <div>
            <Header></Header>
        </div>
        <!-- Title + Searchbar TODO -->
        <div style="text-align: left; margin-top: 5%; margin-left: 3vw; position:static; font-size: larger">
            Übersicht
        </div>
        <!-- Here: Filters and Shit TODO -->
        <div style="margin-top:0.5vw; position:static">
            Filters and Shit
        </div>
        <!-- Documents TODO -->
        <div style="margin-top:0.5vw;">
            <div v-for="doc in docs" :key="doc.id" style="position: static; margin-top: 1vw; margin-left: 3vw;">
                <DocumentBox :doc="doc">
                </DocumentBox>
            </div>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import DocumentBox from "@/main/vue/components/DocumentBox";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/Header";
export default {
    name: "OverviewPage",
    components:{DocumentBox, Footer, Header},
    data() {
        return {
            // Needs to be replaced with API Request TODO
            docs:[{
                id:1234,
                name:"Titel dieses wahnisnnig tollen Dokumentes",
                dateCreated:"03.03.2021",
                owner: {
                    id:12345,
                    name:"Otto Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ein Title der dich vom Hocker haut",
                dateCreated:"25.04.2021",
                owner: {
                    id:12345,
                    name:"Otto Werner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ich bin ein wundervolles Dokument!",
                dateCreated:"01.05.2021",
                owner: {
                    id:12345,
                    name:"Hans Dieter Otto von Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Titel dieses wahnisnnig tollen Dokumentes",
                dateCreated:"03.03.2021",
                owner: {
                    id:12345,
                    name:"Otto Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ein Title der dich vom Hocker haut",
                dateCreated:"25.04.2021",
                owner: {
                    id:12345,
                    name:"Otto Werner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ich bin ein wundervolles Dokument!",
                dateCreated:"01.05.2021",
                owner: {
                    id:12345,
                    name:"Hans Dieter Otto von Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Titel dieses wahnisnnig tollen Dokumentes",
                dateCreated:"03.03.2021",
                owner: {
                    id:12345,
                    name:"Otto Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ein Title der dich vom Hocker haut",
                dateCreated:"25.04.2021",
                owner: {
                    id:12345,
                    name:"Otto Werner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },{
                id:1234,
                name:"Ich bin ein wundervolles Dokument!",
                dateCreated:"01.05.2021",
                owner: {
                    id:12345,
                    name:"Hans Dieter Otto von Wehner"
                },
                state:"open",
                signatories:[{
                    id:12345,
                    name:"Sig Natory"
                }],
                readers:[{
                    id:123456,
                    name:"Rea Der"
                }]
            },
                {
                    id:1234,
                    name:"Titel dieses wahnisnnig tollen Dokumentes",
                    dateCreated:"03.03.2021",
                    owner: {
                        id:12345,
                        name:"Otto Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },
                {
                    id:1234,
                    name:"Titel dieses wahnisnnig tollen Dokumentes",
                    dateCreated:"03.03.2021",
                    owner: {
                        id:12345,
                        name:"Otto Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },
                {
                    id:1234,
                    name:"Titel dieses wahnisnnig tollen Dokumentes",
                    dateCreated:"03.03.2021",
                    owner: {
                        id:12345,
                        name:"Otto Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },{
                    id:1234,
                    name:"Ein Title der dich vom Hocker haut",
                    dateCreated:"25.04.2021",
                    owner: {
                        id:12345,
                        name:"Otto Werner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },{
                    id:1234,
                    name:"Ich bin ein wundervolles Dokument!",
                    dateCreated:"01.05.2021",
                    owner: {
                        id:12345,
                        name:"Hans Dieter Otto von Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },{
                    id:1234,
                    name:"Titel dieses wahnisnnig tollen Dokumentes",
                    dateCreated:"03.03.2021",
                    owner: {
                        id:12345,
                        name:"Otto Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },{
                    id:1234,
                    name:"Ein Title der dich vom Hocker haut",
                    dateCreated:"25.04.2021",
                    owner: {
                        id:12345,
                        name:"Otto Werner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                },{
                    id:1234,
                    name:"Ich bin ein wundervolles Dokument!",
                    dateCreated:"01.05.2021",
                    owner: {
                        id:12345,
                        name:"Hans Dieter Otto von Wehner"
                    },
                    state:"open",
                    signatories:[{
                        id:12345,
                        name:"Sig Natory"
                    }],
                    readers:[{
                        id:123456,
                        name:"Rea Der"
                    }]
                }]
        }
    }
}
</script>

<style scoped>
</style>
