<template>
    <div>
        <Header></Header>

        <DownloadPopUp v-if="showDownload" :doc-id="docId" :env-id="envId" :is-protocol="true"
                       @closedDownload="toggleDownload()"></DownloadPopUp>

        <BaseHeading name="DownloadProtocol.protocol"></BaseHeading>

        <div v-if="hasError" style="margin-top: 25vh;">
            <div style="display: inline-block;" class="text">
                <img :src="turtle" class="responsive-img" alt="turtle">
            </div>
            <div style="display: inline-block" class="text">
                <div>
                    {{ $t('DownloadProtocol.errorOne') }}
                </div>
                <div>
                    {{ $t('DownloadProtocol.errorTwo') }}
                </div>
            </div>
        </div>

        <div v-else>
            <PDFViewer v-if="showPdf" :pdf-src=getProtocol() :overflow="showOverflow"
                       @openDownload="toggleDownload()"></PDFViewer>
        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import DownloadPopUp from "@/main/vue/components/popUps/DownloadPopUp";

import _ from 'lodash';
import {mapGetters} from 'vuex';

export default {
    name: "ProtocolPage",
    components: {
        DownloadPopUp,
        PDFViewer,
        Footer,
        Header
    },
    data() {
        return {
            turtle: require('../assets/turtle.svg'),
            showOverflow: true,
            showDownload: false,
            showPdf: false
        }
    },
    async created() {
        await this.$store.dispatch('document/fetchProtocol', {docId: this.docId})
    },
    async mounted() {
        await this.$store.dispatch('document/fetchProtocol', {docId: this.docId})
        this.showPdf = true
    },
    methods: {
        getProtocol() {
            let chars = atob(this.protocol);
            let array = new Uint8Array(chars.length);
            for (let i = 0; i < chars.length; i++) {
                array[i] = chars.charCodeAt(i)
            }
            return array
        },
        toggleDownload() {
            this.showDownload = !this.showDownload
            this.showOverflow = !this.showOverflow
        }
    }
    ,
    computed: {
        ...mapGetters({
            protocol: 'document/getProtocol',
            getError: 'document/getErrorGetProtocol'
        }),
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        },
        hasError() {
            return !_.isEmpty(this.getError);
        }
    }
}
</script>

<style scoped src="../assets/css/documentPage.css">
</style>

