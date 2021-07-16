<template>
    <div>
        <div v-if="clicked && !uploadingDocument">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog h-100 d-flex flex-column justify-content-center my-0">
                            <div class="modal-content">
                                <div class="modal-header" style="justify-content: space-between">
                                    <h5 v-if="page === 1" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 2" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 3" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 4" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 5" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 6" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.update2') }}</h5>
                                    <h5 v-if="page === 7" class="modal-title">
                                        {{ $t('UploadDoc.UpdateDocument.errorUpload') }}</h5>

                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false; close()">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Page 1: Choose Document -->
                                <div v-if="page === 1">
                                    <div style="padding-top: 1em; text-align: left">
                                        <FileInput @updateFiles="updateFile"
                                                   @close="show = false; close()"
                                                   @nextPage="page = page + 1"
                                                   :moreFiles="false"
                                        ></FileInput>
                                    </div>
                                </div>

                                <div v-if="page === 2">
                                    <div style="padding-top: 1em; text-align: left">
                                        <div v-if="file.title !== null" style="padding: 1rem;">
                                            <span style="margin-left: 1em">Document Title</span>
                                            <b-input v-if="file" v-model="newDocumentTitle"
                                                     style="width: 90%; margin-left: 1em"></b-input>
                                        </div>
                                        <div class="modal-footer">
                                            <b-container>
                                                <b-row align-h="end">
                                                    <b-col cols="auto">
                                                        <button type="button" class="light-btn"
                                                                @click="page = page - 1">
                                                                <span>
                                                                    {{ $t('UploadDoc.back') }}
                                                                </span>
                                                        </button>
                                                    </b-col>
                                                    <b-col cols="auto">
                                                        <button type="button" class="elsa-blue-btn"
                                                                @click="page = page + 1">
                                                                <span>
                                                                    {{ $t('UploadDoc.continue') }}
                                                                </span>
                                                        </button>
                                                    </b-col>
                                                </b-row>
                                            </b-container>
                                        </div>
                                    </div>
                                </div>

                                <!-- Page 3: Add signatories/readers-->
                                <div v-if="page === 3">
                                    <UploadSettings :alreadySetSettings="settings"
                                                    @updateSettings="updateSettings"
                                                    @showEmailTemplate="showEmailTemplate"
                                                    @previousPage="page = page - 1"></UploadSettings>
                                </div>


                                <!-- Page 4: Email Template (for new User) -->
                                <div v-if="page === 4">
                                    <div class="modal-body">
                                        <div>
                                            <EmailTemplate
                                                @saveEmailTemplate="setEmailTemplate"
                                                @pages="checkInnerPages"
                                                :showText="true"
                                            ></EmailTemplate>
                                        </div>
                                    </div>
                                    <div class="modal-footer" v-if="showFooter">
                                        <b-row align-h="end">
                                            <b-col cols="auto">
                                                <button class="light-btn" @click="page = page - 1;">
                                                    {{ $t('UploadDoc.back') }}
                                                </button>
                                            </b-col>
                                            <b-col cols="auto">
                                                <button class="elsa-blue-btn" @click="page = page + 2">
                                                    {{ $t('UploadDoc.upload') }}
                                                </button>
                                            </b-col>
                                        </b-row>
                                    </div>
                                </div>


                                <!-- Page 5: Email Template (Replaced Document Email template) -->
                                <div v-if="page === 5">
                                    <div>
                                        <div class="modal-body">
                                            <div>
                                                <div>
                                                    <h5> {{ $t('EmailTemplate.updateTemplate') }} </h5>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <b-row align-h="end">
                                                <b-col cols="auto">
                                                    <button class="light-btn" @click="page = page - 2;">
                                                        {{ $t('UploadDoc.back') }}
                                                    </button>
                                                </b-col>
                                                <b-col cols="auto">
                                                    <button class="elsa-blue-btn" @click="setDefaultTemplate">
                                                        {{ $t('UploadDoc.continue') }}
                                                    </button>
                                                </b-col>
                                            </b-row>
                                        </div>
                                    </div>
                                </div>


                                <!-- Page 6: Upload -->
                                <div v-if="page === 6">
                                    <div>
                                        <div class="modal-body">
                                            <div v-if="!this.uploadingDocument">
                                                <div>
                                                    <p>
                                                        {{
                                                            $t('UploadDoc.UpdateDocument.confirmation', {documentTitle: document.title})
                                                        }}</p>
                                                    <p>{{ $t('UploadDoc.UpdateDocument.signatureResetReminder') }}</p>
                                                </div>
                                            </div>

                                            <div v-if="this.uploadingDocument">
                                                <b-spinner></b-spinner>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <b-row align-h="end">
                                                <b-col cols="auto">
                                                    <button class="light-btn" @click="page = page - 1;">
                                                        {{ $t('UploadDoc.back') }}
                                                    </button>
                                                </b-col>
                                                <b-col cols="auto">

                                                    <b-button class="elsa-blue-btn" @click="upload">
                                                        {{ $t('UploadDoc.upload') }}
                                                    </b-button>
                                                </b-col>
                                            </b-row>
                                        </div>
                                    </div>
                                </div>


                                <!-- Page 7 Error -->
                                <div v-if="page === 7">
                                    <div>
                                        <div class="modal-body">
                                            <div>
                                                <div>
                                                    {{ $t('UploadDoc.UpdateDocument.errorWhileUpload') }}
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <b-row align-h="end">
                                                <b-col cols="auto">
                                                    <button class="light-btn" @click="close">
                                                        {{ $t('UploadDoc.close') }}
                                                    </button>
                                                </b-col>
                                            </b-row>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </transition>
        </div>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import FileInput from "@/main/vue/components/uploadDocuments/FileInput";
import UploadSettings from "@/main/vue/components/uploadDocuments/UploadSettings";
import EmailTemplate from "@/main/vue/components/EmailTemplate";

export default {
    name: "uploadNewVersionButton",
    components: {EmailTemplate, FileInput, UploadSettings},
    data() {
        return {
            innerPage: null,
            show: true,
            showTemplateBoolean: false,
            showFooter: true,
            page: 1,
            selectedEnvelope: {
                id: null,
                name: null
            },
            settings: {
                signatories: [],
                endDate: null,
                orderRelevant: true
                // TODO: processStart: Boolean
            },
            file: {
                data: null,
                type: null,
                title: null
            },
            uploadingDocument: false,
            newDocumentTitle: '',

            // filename of uploaded file
            fileString: "",
            // Object which contains the selected document and getting updated with the new informations
            actualDoc: {},
        }
    },
    props: ['document', 'docID', 'envID', 'clicked'],
    computed: {
        ...mapGetters({
            newDocumentId: 'document/getNewDocumentId',
            newDocumentError: 'document/getErrorEditDocument',
            newDocumentStatus: 'document/getEditDocumentStatus'
        })
    },

    methods: {
        // setting the new file into file variable and also the documents information into the actualDoc variable
        updateFile: function (file) {
            this.file = file[0];
            this.actualDoc = this.document
            this.settings.endDate = this.actualDoc.endDate
            this.settings.signatories = this.actualDoc.signatories
            this.settings.orderRelevant = this.actualDoc.orderRelevant
            this.newDocumentTitle = this.file.title
        },


        updateSettings: function (settings) {
            this.settings = settings;
        },
        updateSignatories(newSignatories) {
            this.actualDoc.signatories = newSignatories
        },

        close() {
            this.page = 1;
            this.file = {data: null, type: null, title: null};
            this.selectedEnv = {old: null, new: null};
            this.settings = {signatories: [], endDate: null, orderRelevant: null};
            this.show = false;
            this.uploadingDocument = false;

            this.$emit('closePopUp', false)
        },

        async upload() {
            this.uploadingDocument = true

            this.actualDoc.endDate = this.settings.endDate
            this.actualDoc.signatories = this.settings.signatories
            this.actualDoc.orderRelevant = this.settings.orderRelevant
            this.actualDoc.data = this.file.data
            this.actualDoc.dataType = this.file.type
            this.actualDoc.title = this.newDocumentTitle

            let payload = {newDoc: this.actualDoc, envId: this.envID, docId: this.docID}
            await this.$store.dispatch('document/editDocument', payload)

            // here show error


            await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envID, docId: this.newDocumentId})
            let newUrl = 'envelope/' + this.envID + '/document/' + this.newDocumentId

            console.log('Update success')
            // will route the user to the newUploaded document page (with the new ID)
            // for now it is working. But it will show before refreshing the new page an unable preview of the file
            await this.$store.dispatch('envelopes/fetchEnvelopes')
            await this.$emit("refreshDocument")
            this.$router.push('/' + this.$i18n.locale + '/' + newUrl).then(() => {
                this.$router.go(0)
            })
            this.close()

        },

        // temp is the selected template
        setEmailTemplate(temp) {
            // emailTemplates is an array instead of an object (in document)
            this.actualDoc.emailTemplateHtml = temp
        },

        setDefaultTemplate() {
            this.actualDoc.emailTemplateHtml = this.actualDoc.owner.emailTemplates[0]
            this.page = this.page + 1

        },

        showEmailTemplate(showTemp) {
            if (showTemp === true) {
                // new registered signatory is noticed
                this.showTemplateBoolean = true
                this.page = 4
            } else {
                // no new registered signatory is noticed
                this.showTemplateBoolean = true
                this.page = 5
            }
        },
        checkInnerPages(innerPage) {
            this.showFooter = innerPage === 0;
            this.innerPage = innerPage
        }
    }
}


</script>

<style scoped src="../assets/css/signModals.css">


</style>
