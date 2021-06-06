<template>
    <div>
        <Header></Header>

        <DownloadPopUp v-if="showDownload" :doc-id="docId" :env-id="envId"
                       @closedDownload="toggleDownload()"></DownloadPopUp>

        <div v-if="hasError" style="margin-top: 35vh;">
            <div style="display: inline-block;" class="text">
                <img :src="turtle" class="responsive-img" alt="turtle">
            </div>
            <div style="display: inline-block" class="text">
                <div>
                    {{ $t('DocumentPage.errors.notFound') }}
                </div>
                <div>
                    {{ $t('DocumentPage.errors.refresh') }}
                </div>
            </div>
        </div>

        <div v-else>
            <!-- No error, can be shown -->
            <div v-if="document.dataType === 'pdf'">
                <BaseHeading :name="document.title" :translate="false"></BaseHeading>
                <PDFViewer :pdf-src=getPDF() :overflow="showOverflow" @openDownload="toggleDownload()"></PDFViewer>
                <Sidebar @triggerOverflow="toggleOverflow"></Sidebar>
            </div>

            <!-- Error, cannot be shown -->
            <div v-else>
                <BaseHeading :name="document.title" :translate="false"></BaseHeading>
                <div class="text">
                    {{ $t("DocumentPage.noView") }}
                </div>
                <button type="button" class="elsa-blue-btn" @click="toggleDownload">
                <span class="button-txt">
                    {{ $t('DownloadDoc.download') }}
                </span>
                </button>

                <Sidebar @triggerOverflow="toggleOverflow"></Sidebar>
            </div>
        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import Sidebar from "@/main/vue/components/Sidebar";
import DownloadPopUp from "@/main/vue/components/popUps/DownloadPopUp";

import _ from 'lodash';
import {mapGetters} from 'vuex';


export default {
    name: "DocumentPage",
    components: {
        DownloadPopUp,
        Sidebar,
        PDFViewer,
        Footer,
        Header
    },
    data() {
        return {
            turtle: require('../assets/turtle.svg'),
            showOverflow: true,
            showDownload: false,
            showUploadNewVersion: false
        }
    },
    created() {
        this.$store.dispatch('document/fetchDocument', {envId: this.envId, docId: this.docId})
    },
    methods: {
        getPDF() {
            let chars = atob(this.document.data);
            let array = new Uint8Array(chars.length);
            for (let i = 0; i < chars.length; i++) {
                array[i] = chars.charCodeAt(i)
            }
            return array
        },
        toggleOverflow() {
            this.showOverflow = !this.showOverflow
        },
        toggleDownload() {
            this.showDownload = !this.showDownload
            this.showOverflow = !this.showOverflow
        },

    }
    ,
    computed: {
        ...mapGetters({
            document: 'document/getDocument',
            getError: 'document/getErrorGetDocument',
            newDocumentId: 'document/getNewDocumentId',
            newDocumentError: 'document/getNewDocumentError'
        }),
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        },
        hasError() {
            return !_.isEmpty(this.getError);
        },

    }
}
</script>

<style scoped src="../assets/css/documentPage.css">
</style>
