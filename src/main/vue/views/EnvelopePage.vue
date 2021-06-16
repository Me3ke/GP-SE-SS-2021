<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>

        <BaseHeading :name="this.envelope(envId).name" :translate="false"></BaseHeading>


        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in this.envelope(envId).documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">

                        <!-- Default -->
                        <DocumentCard :document="document" :envelopeId="envId"></DocumentCard>
                        <div v-if="documentProgressById(document.id)">
                            <ProgressBar
                                :documentProgress="documentProgressById(document.id)"
                                :docId="document.id"
                            >
                            </ProgressBar>
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
import ProgressBar from "@/main/vue/components/ProgressBar";
import {mapGetters} from "vuex";
import DocumentCard from "@/main/vue/components/DocumentCard";

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    data() {
        return {
            dataReady: false
        }
    },

    components: {Footer, Header, DocumentCard, ProgressBar},


    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope',
            documentProgress: 'document/getDocumentProgressArray',
            documentProgressById: 'document/getDocumentProgressArrayById'
        }),


        // get array of the document id in the specific selected envelope
        getDocumentsId() {
            let documentsIdArray = []
            let documents = this.$store.getters.getEnvelope(this.envId).documents
            for (let i = 0; i < documents.length; i++) {
                documentsIdArray.push(documents[i].id)
            }
            return documentsIdArray
        },
    },

// resets the state of documentProgress array to [] if this pages is going to reload
// otherwise it will add by every refreshing or new loading of the page all documents to the already (previously)
// added state array
    beforeCreate() {
        this.$store.dispatch('document/resetState', [])

    },

    async created() {
        await this.$store.dispatch('document/getDocumentProgress', {
            envId: this.envId,
            documentsId: this.getDocumentsId
        })
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    }


}
</script>

<style scoped>

</style>
