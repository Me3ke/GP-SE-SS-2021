<template>
    <div style="width: 100%; height: 100vh; background-color: var(--whitesmoke)">
        <Header></Header>

        <DownloadPopUp v-if="showDownload" :doc-id="docId" :is-protocol="true"
                       @closedDownload="toggleDownload()"></DownloadPopUp>

        <!-- Displays if document cannot get fetched by api -->
        <BaseHeading v-if="!hasError()" name=" " :translate="false" style="position: fixed;"></BaseHeading>
        <div v-if="!hasError()" class="d-flex align-items-center" style="height: 80vh">
            <b-container>
                <b-row cols="2">

                    <b-col cols="4">
                        <img :src="turtle" class="responsive-img" alt="turtle">
                    </b-col>

                    <b-col cols="8">
                        <b-row class="text-center" align-v="center" align-h="center" cols="1">
                            <b-col>
                                <h1>{{ $t('DocumentPage.errors.notFound') }}</h1>
                            </b-col>
                            <b-col>
                                <h3>{{ $t('DocumentPage.errors.refresh') }}</h3>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Displays if document can get fetched by api -->
        <div v-else>
            <BaseHeading name="DownloadProtocol.protocol"
                         style="position: fixed;"></BaseHeading>

            <!-- Displays that preview is possible -->
            <b-container style="width: 100%; margin-top: 0; margin-right: auto; margin-left: auto; padding: 0;">
                <b-row style="width: 100%; margin: auto; padding: 0">
                    <b-col cols="9">
                        <PDFViewer :pdf-src=getProtocol() :overflow="showOverflow"
                                   @openDownload="toggleDownload()"></PDFViewer>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";

import _ from 'lodash';
import {mapGetters} from 'vuex';
import DownloadPopUp from "@/main/vue/components/DownloadPopUp";


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
            pdfKey: 0
        }
    },
    created() {
        this.$store.dispatch('document/fetchProtocol', {docId: this.docId})
    },
    methods: {
        getProtocol() {
            let chars = atob(this.protocol);
            let array = new Uint8Array(chars.length);
            for (let i = 0; i < chars.length; i++) {
                array[i] = chars.charCodeAt(i)
            }
            return array
        }
        ,
        hasError() {
            return _.isEmpty(this.getError);
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
        }, envId() {
            return this.$route.params.envId;
        }
    }
}
</script>

<style scoped src="../assets/css/documentPage.css">
</style>

