<template>
    <div>
        <Header></Header>
        <BaseHeading :name="env.name" style="position: fixed"></BaseHeading>

        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in env.documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <!-- Different styles for open/closed documents TODO-->
                        <div>
                            <DocumentBox
                                @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envId, userId: document.owner.id}})"
                                :doc="document">
                            </DocumentBox>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import DocumentBox from "@/main/vue/components/DocumentBox";

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, DocumentBox},
    data() {
        return {
            env: {
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
            }
        }
    }

}
</script>

<style scoped>

</style>
