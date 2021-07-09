<template>
    <div>
        <Header></Header>

        <DownloadPopUp v-if="showDownload" :doc-id="docId" :env-id="envId" :is-protocol="true"
                       @closedDownload="toggleDownload()"></DownloadPopUp>

        <BaseHeading name="DownloadProtocol.protocol"></BaseHeading>

        <div v-if="dataError" style="margin-top: 25vh;">
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

import documentAPI from "@/main/vue/api/documentAPI";

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
            pdfSrc: null,
            showOverflow: true,
            showDownload: false,
            showPdf: false,
            dataError: false
        }
    },
    async mounted() {
        await documentAPI.getProtocol(this.docId).then(response => {
            this.pdfSrc = response.data
            this.dataError = false
        }).catch(() => {
            this.dataError = true
        })
        this.showPdf = true
    },
    methods: {
        getProtocol() {
            let chars = atob(this.pdfSrc);
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
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        }
    }
}
</script>

<style scoped src="../assets/css/documentPage.css">
</style>

