<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>
        <BaseHeading :name="getEnv(envId).name" :translate="false"></BaseHeading>

        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in getEnv(envId).documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">

                        <!-- Default -->
                        <div
                            v-if="(document.signatory === false || document.signed === true)
                                && (document.reader === false || document.read === true)
                                && document.state === 'open'">
                            <DocumentBox
                                @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envId, userId: document.owner.id}})"
                                :doc="document">
                            </DocumentBox>
                        </div>

                        <!-- Closed -->
                        <div
                            v-if="document.state === 'closed'">
                            <DocumentBoxClosed
                                @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envId, userId: document.owner.id}})"
                                :doc="document">
                            </DocumentBoxClosed>
                        </div>

                        <!-- To sign/read -->
                        <div
                            v-if="((document.signatory === true && document.signed === false)
                                || (document.reader === true && document.read === false))
                                && document.state === 'open'">
                            <DocumentBoxSignRead
                                @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envId, userId: document.owner.id}})"
                                :doc="document">
                            </DocumentBoxSignRead>
                        </div>

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
import DocumentBox from "@/main/vue/components/documentBoxes/DocumentBox";
import DocumentBoxClosed from "@/main/vue/components/documentBoxes/DocumentBoxClosed";
import DocumentBoxSignRead from "@/main/vue/components/documentBoxes/DocumentBoxSignRead";
import ProgressBar from "@/main/vue/components/ProgressBar";
import {mapGetters} from "vuex";

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
    components: {ProgressBar, Footer, Header, DocumentBox, DocumentBoxClosed, DocumentBoxSignRead},
    computed: {
        getEnv() {
            return this.$store.getters.getEnvelope
        },


        // get array of the document id in the specific selected envelope
        getDocumentsId() {
            let documentsIdArray = []
            let documents = this.$store.getters.getEnvelope(this.envId).documents
            for(let i = 0; i < documents.length; i++) {
                documentsIdArray.push(documents[i].id)
            }
            return documentsIdArray
        },


        ...mapGetters({
            documentProgress: 'document/getDocumentProgressArray',
            documentProgressById: 'document/getDocumentProgressArrayById'


        })


    },

    // resets the state of documentProgress array to [] if this pages is going to reload
    // otherwise it will add by every refreshing or new loading of the page all documents to the already (previously)
    // added state array
    beforeCreate() {
        this.$store.dispatch('document/resetState', [] )

    },

  async created() {
         await this.$store.dispatch('document/getDocumentProgress', {
             envId: this.envId,
             documentsId: this.getDocumentsId
         })
    },



}
</script>

<style scoped>

</style>
