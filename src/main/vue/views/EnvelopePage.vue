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


                        <!-- Update new Version Button

                        <upload-new-version-button
                            :document="document"
                            :doc-i-d="document.id"
                        >
                        </upload-new-version-button>
 -->

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

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, DocumentBox, DocumentBoxClosed, DocumentBoxSignRead},
    computed: {
        getEnv() {
            return this.$store.getters.getEnvelope
        }
    }
}
</script>

<style scoped>

</style>
