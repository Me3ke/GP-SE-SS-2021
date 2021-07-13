<template>
    <div>
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
            <!-- No error, can be shown -->
            <div v-if="document.dataType === 'pdf'">
                <BaseHeading :name="document.title" :translate="false"></BaseHeading>

                <transition name="fade" mode="out-in">
                    <div style="display: flex; width: 100%" v-if="showDetails" key="1">
                        <PDFViewer v-if="showPdf" :pdf-src=getPDF() :overflow="showOverflow"
                                   @openDownload="toggleDownload()"></PDFViewer>

                        <!-- Meta data -->
                        <div class="meta-container has-doc overflow-auto">
                            <div class="meta-heading first-heading">
                                <span> {{ $t('DocumentPage.meta.meta') }} </span>
                            </div>

                            <hr style="border-color: var(--dark-grey); margin-top: 0">

                            <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.title') }}: <span
                            class="meta-inner"> {{ document.title }} </span> </span>
                            </div>

                            <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.docType') }}: <span
                            class="meta-inner"> {{ document.dataType }} </span> </span>
                            </div>

                            <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.state') }}: <span
                            class="meta-inner"> {{ $t('DocumentPage.meta.' + getDocState()) }} </span> </span>
                            </div>

                            <div class="meta-heading">
                        <span> {{ $t('DocumentPage.meta.owner') }}: <span
                            class="meta-inner"> {{ document.owner.firstname }} {{
                                document.owner.lastname
                            }} </span> </span>
                            </div>

                            <div class="meta-heading">
                        <span>  {{ $t('DocumentPage.meta.created') }}: <span
                            class="meta-inner"> {{ document.creationDate }} </span></span>
                            </div>

                            <div class="meta-heading">
                        <span>  {{ $t('DocumentPage.meta.end') }}: <span
                            class="meta-inner"> {{ document.endDate }} </span></span>
                            </div>

                            <div class="meta-heading">
                                <span> {{ $t('DocumentPage.meta.readers') }}</span>

                                <div v-if="documentProgress[0]">
                                    <ul class="text" v-if="documentProgress[0].data.readers.length === 0">
                                        <li> {{ $t('DocumentPage.meta.noReaders') }}</li>
                                    </ul>
                                    <ul class="text" v-else>
                                        <li v-for="(reader, index) in documentProgress[0].data.readers" :key="index">
                                            {{ reader.email }}:
                                            {{ $t('DocumentPage.meta.' + getRead(reader.email)) }}
                                        </li>
                                    </ul>
                                </div>

                            </div>

                            <div class="meta-heading">
                                <span>  {{ $t('DocumentPage.meta.signatories') }}</span>
                                <div v-if="documentProgress[0]">
                                    <ul class="text" v-if="documentProgress[0].data.signatories.length === 0">
                                        <li> {{ $t('DocumentPage.meta.noSignatories') }}</li>
                                    </ul>
                                    <ul class="text" v-else>
                                        <li v-for="(signatory, index) in documentProgress[0].data.signatories"
                                            :key="index">
                                            {{ signatory.email }}: {{
                                                $t('DocumentPage.meta.' + getSignatureType(index))
                                            }},
                                            {{ $t('DocumentPage.meta.' + getSigned(index)) }}
                                        </li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div v-else key="2">
                        <PDFViewer v-if="showPdf" :pdf-src=getPDF() :overflow="showOverflow"
                                   @openDownload="toggleDownload()"></PDFViewer>
                    </div>

                </transition>
                <Sidebar @triggerOverflow="toggleOverflow" @detailTrigger="showDetails = !showDetails"></Sidebar>
            </div>

            <!-- No error, cannot be shown -->
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

                <!-- Meta data -->
                <transition name="fade">
                    <div v-if="showDetails" class="meta-container">
                        <div class="meta-heading first-heading">
                            <span> {{ $t('DocumentPage.meta.meta') }} </span>
                        </div>

                        <hr style="border-color: var(--dark-grey); margin-top: 0">

                        <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.title') }}: <span
                            class="meta-inner"> {{ document.title }} </span> </span>
                        </div>

                        <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.docType') }}: <span
                            class="meta-inner"> {{ document.dataType }} </span> </span>
                        </div>

                        <div class="meta-heading">
                        <span>{{ $t('DocumentPage.meta.state') }}: <span
                            class="meta-inner"> {{ $t('DocumentPage.meta.' + getDocState()) }} </span> </span>
                        </div>

                        <div class="meta-heading">
                        <span> {{ $t('DocumentPage.meta.owner') }}: <span
                            class="meta-inner"> {{ document.owner.firstname }} {{
                                document.owner.lastname
                            }} </span> </span>
                        </div>

                        <div class="meta-heading">
                        <span>  {{ $t('DocumentPage.meta.created') }}: <span
                            class="meta-inner"> {{ document.creationDate }} </span></span>
                        </div>

                        <div class="meta-heading">
                        <span>  {{ $t('DocumentPage.meta.end') }}: <span
                            class="meta-inner"> {{ document.endDate }} </span></span>
                        </div>

                        <div class="meta-heading">
                            <span> {{ $t('DocumentPage.meta.readers') }}</span>

                            <div v-if="documentProgress[0]">
                                <ul class="text" v-if="documentProgress[0].data.readers.length === 0">
                                    <li> {{ $t('DocumentPage.meta.noReaders') }}</li>
                                </ul>
                                <ul class="text" v-else>
                                    <li v-for="(reader, index) in documentProgress[0].data.readers" :key="index">
                                        {{ reader.email }}:
                                        {{ $t('DocumentPage.meta.' + getRead(reader.email)) }}
                                    </li>
                                </ul>
                            </div>

                        </div>

                        <div class="meta-heading">
                            <span>  {{ $t('DocumentPage.meta.signatories') }}</span>
                            <div v-if="documentProgress[0]">
                                <ul class="text" v-if="documentProgress[0].data.signatories.length === 0">
                                    <li> {{ $t('DocumentPage.meta.noSignatories') }}</li>
                                </ul>
                                <ul class="text" v-else>
                                    <li v-for="(signatory, index) in documentProgress[0].data.signatories" :key="index">
                                        {{ signatory.email }}: {{ $t('DocumentPage.meta.' + getSignatureType(index)) }},
                                        {{ $t('DocumentPage.meta.' + getSigned(index)) }}
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                </transition>

                <Sidebar @triggerOverflow="toggleOverflow" @detailTrigger="showDetails = !showDetails"></Sidebar>
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

import documentAPI from "@/main/vue/api/documentAPI";

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
            pdfSrc: null,
            dataError: false,

            showOverflow: true,
            showDownload: false,
            showUploadNewVersion: false,
            showPdf: false,
            showDetails: false
        }
    },
    async mounted() {
        await this.$store.dispatch('document/resetState')
        await this.$store.dispatch('document/documentProgress', {envId: this.envId, docId: this.docId})
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
        this.$store.dispatch('document/resetState')
    },
    methods: {
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
        // returns state of document as string
        getDocState() {
            if (this.document.state === 'REVIEW') {
                return 'review'
            } else if (this.document.state === 'SIGN') {
                return 'sign'
            } else if (this.document.state === 'ARCHIVED') {
                return 'archived'
            } else {
                return 'error'
            }
        },
        getRead(mail) {
            const haveRead = this.documentProgress[0].data.alreadyRead

            for (var i = 0; i < haveRead; i++) {
                if (haveRead[i].email === mail) {
                    return "didRead"
                }
            }

            return "noRead"
        },
        // returns signed as string
        getSigned(index) {
            if (this.documentProgress[0].data.signatories[index].status) {
                return 'signed'
            } else {
                return 'noSigned'
            }
        },
        // returns signature type as string
        getSignatureType(index) {
            if (this.documentProgress[0].data.signatories[index].signatureType === 'SIMPLE_SIGNATURE') {
                return 'simple'
            } else if (this.documentProgress[0].data.signatories[index].signatureType === 'ADVANCED_SIGNATURE') {
                return 'advanced'
            }
        }
    },
    computed: {
        ...mapGetters({
            newDocumentId: 'document/getNewDocumentId',
            document: 'document/getDocumentInfo',
            getError: 'document/getErrorGetDocumentInfo',
            documentProgress: 'document/getDocumentProgressArray'
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
