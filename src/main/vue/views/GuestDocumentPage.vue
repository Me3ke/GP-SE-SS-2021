<template>
    <div>
      <!-- Comment
        <Header></Header>

        <DownloadPopUp v-if="showDownload" :doc-id="docId" :env-id="envId"
                       @closedDownload="toggleDownload()"></DownloadPopUp>

        <div v-if="hasError || dataError" style="margin-top: 35vh;">
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
            <div v-if="document.dataType === 'pdf'">
                <BaseHeading :name="document.title" :translate="false"></BaseHeading>
                <PDFViewer v-if="showPdf" :pdf-src=getPDF() :overflow="showOverflow"
                           @openDownload="toggleDownload()"></PDFViewer>
                <Sidebar @triggerOverflow="toggleOverflow"></Sidebar>
            </div>

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
        -->

        <!-- !TEST OUPUT! -->
      <h1> Hello World </h1>
      <h1> AUTHENTICATED </h1>
      <h1> {{ this.$store.state.guestAuth.guestAuthenticated.toString() }}</h1>
      <h1> USERNAME </h1>
      <h1> {{ this.$store.state.guestAuth.guestUsername.toString() }}</h1>
      <h1> TOKEN </h1>
      <h1> {{ this.$store.state.guestAuth.guestToken.toString() }}</h1>


    </div>
</template>

<script>
/*
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import Sidebar from "@/main/vue/components/Sidebar";
import DownloadPopUp from "@/main/vue/components/popUps/DownloadPopUp";

import documentAPI from "@/main/vue/api/documentAPI";

import _ from 'lodash';
import {mapGetters} from 'vuex';
*/

export default {
    name: "GuestDocumentPage",
    components: {
      /*
        DownloadPopUp,
        Sidebar,
        PDFViewer,
        Footer,
        Header

       */
    },
    data() {
        return {

          /*
            turtle: require('../assets/turtle.svg'),
            pdfSrc: null,
            dataError: false,
            showOverflow: true,
            showDownload: false,
            showUploadNewVersion: false,
            showPdf: false

           */
        }
    },
    /*
    async mounted() {
        await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envId, docId: this.docId})
        await documentAPI.getDocument(this.envId, this.docId).then(response => {
            this.pdfSrc = response.data.data
            this.dataError = false
        }).catch(() => {
            this.dataError = true
        })
        this.showPdf = true
    },
    beforeDestroy() {

    },
    */
    methods: {
      /*
        getPDF() {
            let chars = atob(this.pdfSrc);
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
      */
    }
    ,
    computed: {

      /*
        ...mapGetters({
            newDocumentId: 'document/getNewDocumentId',
            document: 'document/getDocumentInfo',
            getError: 'document/getErrorGetDocumentInfo'
        }),
       */
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        },
        tokenId() {
        return this.$route.params.tokenId;
        },
      /*
        hasError() {
            return !_.isEmpty(this.getError);
        }*/

    },
    created() {
      //pushing a logged in user to normal Document Page
      if (this.$store.state.auth.authenticated === true) {
        this.$router.push('/' + this.$i18n.locale + '/envelope/' + this.envId + '/document/' + this.docId )
      } else {
        localStorage.clear()
        this.$store.dispatch('requestGuestInfo', { envId:this.envId, docId:this.docId, guestToken:this.tokenId })
      }
    }
}
</script>

<style scoped src="../assets/css/documentPage.css">
</style>