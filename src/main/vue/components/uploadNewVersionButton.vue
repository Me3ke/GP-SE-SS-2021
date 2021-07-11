<template>
    <div>
        <!-- Page 1 -->
        <!---
        <b-modal
            :id="'modal-' + docID + 'a'"
            ref="modal-page1"
            centered :title="$t('UploadDoc.UpdateDocument.replaceDoc') + document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <h6>{{ $t('UploadDoc.UpdateDocument.selectDocument') }}</h6>
            </div>
            <div>
                <div class="modal-body">
                    <div>
                        <div class="form-group files">
                            <b-form-file
                                v-model="file"
                                :state="Boolean(file)"
                                id="fileInput"
                                v-bind:placeholder="$t('UploadDoc.UpdateDocument.chooseFile')"
                                drop-placeholder="Drop file here..."
                                @change="previewFile"
                            ></b-form-file>

                        </div>
                    </div>
                </div>
            </div>
            <div class="text-right">
                <button type="button" class="mt-1 light-btn" @click="resetSettings('modal-page1')">
                    <span class="button-txt">
                        {{ $t('UploadDoc.close') }}
                    </span>
                </button>

                <button type="button"
                        class="mt-1 elsa-blue-btn"
                        v-b-modal="'modal-' + docID + 'bb'"
                        :disabled="file == null"
                        @click="setActualDoc"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>
        </b-modal>


        <b-modal
            :id="'modal-' + docID + 'bb'"
            ref="modal-page2"
            centered
            :title="document.title + '  Documentsettings'"
            hide-footer ok-only no-stacking
        >
            <div>
                <button type="button"
                        class="light-btn"
                        @click="review = true;"
                        v-b-modal="'modal-' + docID + 'bbb'"
                >
                    <h5>
                        {{ $t('UploadDoc.addReaders') }}
                    </h5>
                </button>
                <button type="button"
                        class="light-btn"
                        @click="review = false;"
                        v-b-modal="'modal-' + docID + 'bbbb'"

                >
                    <h5>
                        {{ $t('UploadDoc.skipReview') }}
                    </h5>
                </button>
            </div>
        </b-modal>

            Readers
        <b-modal
            :id="'modal-' + docID + 'bbb'"
            ref="modal-page2"
            centered
            :title="document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <ReaderMenu :readers="actualDoc.readers"></ReaderMenu>
            </div>

            <div>
                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetSettings('modal-page2')"
                        v-b-modal="'modal-' + docID + 'b'"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.back') }}
                    </span>
                </button>

                <button type="button"
                        class="ml-1 elsa-blue-btn"
                        v-b-modal="'modal-' + docID + 'bbbb'"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>

        </b-modal>


        <---EndDate + Signatories---
        <b-modal
            :id="'modal-' + docID + 'bbbb'"
            ref="modal-page2"
            centered
            :title="document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <div>
                    <label for="endDatePicker">{{ $t('Settings.DocumentSettings.chooseDate') }}</label>
                    <b-form-datepicker id="endDatePicker" v-model="actualDoc.endDate" class="mb-2"></b-form-datepicker>
                </div>
                <SignatoryMenu :signatories="actualDoc.signatories" :orderRelevant="actualDoc.orderRelevant"
                               @update-signatories="updateSignatories"></SignatoryMenu>
            </div>
            <div>
                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetSettings('modal-page2')"
                        v-b-modal="this.actualDoc.review ? 'modal-' + docID + 'bbb' : 'modal-' + docID + 'bb' "
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.back') }}
                    </span>
                </button>

                <button type="button"
                        class="ml-1 elsa-blue-btn"
                        v-b-modal="'modal-' + docID + 'c'"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>

        </b-modal>


        <-- Last PAGE  --
        <b-modal
            :id="'modal-' + docID + 'c'"
            ref="modal-page3"
            centered
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{ document.title }}</h3>
                <h5> {{ $t('UploadDoc.UpdateDocument.confirmation', {documentTitle: document.title}) }}</h5>
            </div>
            <p class="my-4"> {{ $t('UploadDoc.UpdateDocument.signatureResetReminder') }} {{ showAlert }} </p>
            <div class="text-right">


                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetSettings('modal-page3')"
                        v-b-modal="'modal-' + docID + 'bb'"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.back') }}
                    </span>
                </button>

                <button type="button"
                        class="ml-1 elsa-blue-btn"
                        @click="uploadNewFile"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>


                <--<b-button
                    @click="uploadNewFile"
                    >
                    {{ $t('UploadDoc.UpdateDocument.update1') }}
                </b-button>--
            </div>
        </b-modal>


        <-- TODO Error Page  --
        <b-modal
            :id="'modal-' + docID + 'error'"
            centered
            hide-footer hide-header ok-only no-stacking

        >
            <b-alert align="center">
                ERROR TEST
            </b-alert>

        </b-modal>

        --->


        {{clicked}}
        <div v-if="clicked">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 v-if="page === 1" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 2" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 3" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 4" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false; close()">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Page 1 Choose Document -->
                                <div v-if="page === 1">
                                    <FileInput @updateFile="updateFile" @close="show = false; close()" @nextPage="page = page + 1"></FileInput>
                                </div>

                                <!-- Page 3 Add signatories/readers-->
                                <div v-if="page === 2">
                                    <UploadSettings :alreadySetSettings="actualDoc" @updateSettings="updateSettings" @nextPage="page = page + 1" @previousPage="page = page - 1"></UploadSettings>
                                </div>

                                <!-- Page 4 Upload -->
                                <div v-if="page === 4">
                                    <div class="modal-body">
                                        <div v-if="!this.uploadingDocument">
                                            <SettingsPreview :settings="settings" :selectedEnvelope="selectedEnvelope" :file="file"></SettingsPreview>
                                        </div>

                                        <div v-if="this.uploadingDocument">
                                            <b-spinner></b-spinner>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <b-row align-h="end">
                                            <b-col cols="auto">
                                                <button class="light-btn" @click="page = page - 1;">
                                                    {{$t('UploadDoc.back')}}
                                                </button>
                                            </b-col>
                                            <b-col cols="auto">
                                                <button class="elsa-blue-btn" @click="upload()">
                                                    {{$t('UploadDoc.upload')}}
                                                </button>
                                            </b-col>
                                        </b-row>
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
import {convertUploadFileToBase64} from "../scripts/fileToBase64Converter";
import {mapGetters} from "vuex";
import FileInput from "@/main/vue/components/uploadDocuments/FileInput";
import UploadSettings from "@/main/vue/components/uploadDocuments/UploadSettings";
import SettingsPreview from "@/main/vue/components/uploadDocuments/SettingsPreview";

export default {
    name: "uploadNewVersionButton",
    components: { FileInput, UploadSettings, SettingsPreview},
    data() {
        return {


            show: true,


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
            settings2: {
                signatories: [],
                endDate: null,
                orderRelevant: true,
                readers: []
                // TODO: processStart: Boolean
            },
            file: {
                data: null,
                type: null,
                title: null
            },
            uploadingDocument: false,


            // filename of uploaded file
            fileString: "",
            file2: null,
            showAlert: false,
            review: true,


            // need document settings as variable
            newTitle: '',

            // TODO Actual Doc which is going to be replaced
            actualDoc: {},
        }
    },
    props: ['document', 'docID', 'envID', 'clicked'],
    computed: {
        ...mapGetters({
            newDocumentId: 'document/getNewDocumentId',
            newDocumentError: 'document/getErrorEditDocument',
            newDocumentStatus: 'document/getEditDocumentStatus'
        }),

        isoDate(enDate) {
            const [day, month, year] = enDate.split('.')
            return year + '-' + month + '-' + day
        }

    },

    methods: {
        // setting the new file into file variable and also the documents information into the actualDoc variable
        updateFile: function (file) {
            this.file = file;
            this.actualDoc = this.document
            console.log(this.actualDoc)
            this.settings.endDate = this.actualDoc.endDate
            this.settings.signatories = this.actualDoc.signatories
            this.settings.orderRelevant = this.actualDoc.orderRelevant
        },


        updateSettings: function (settings) {
            this.settings = settings;
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








        // save the selected File in the data
        previewFile(event) {
            this.fileString = event.target.files[0].name
            this.file = event.target.files[0]
        },

        updateSignatories(newSignatories) {
            this.actualDoc.signatories = newSignatories
        },

        setActualDoc() {
            this.actualDoc = this.document

            this.actualDoc.orderRelevant = false
            this.actualDoc.lastModified = new Date(this.file.lastModified).toISOString()

            if (this.actualDoc.endDate !== '' || this.actualDoc.endDate != null) {
                const [day, month, year] = this.actualDoc.endDate.split('.')
                this.actualDoc.endDate = year + '-' + month + '-' + day
            }
            this.actualDoc.dataType = this.fileString.split('.').pop()
        },

        /// getting emitted value
        async uploadNewFile() {

            this.fileString = ""
            this.actualDoc.endDate = this.actualDoc.endDate + ' 12:00'

            this.actualDoc.data = await this.asyncHandleFunction()

            // todo add error (but need status code on the response, for now only getting newDocId and replaced one which is getting an new id too)
            let payload = {newDoc: this.actualDoc, envId: this.envID, docId: this.docID}
            await this.$store.dispatch('document/editDocument', payload)

            // here show error
            if (this.showAlert) {
                this.$refs['modal-page3'].hide()
                this.$bvModal.show('modal-' + this.docID + 'error')
                this.actualDoc = {}
                this.fileString = ''
                this.file = null
            } else {
                this.$refs['modal-page3'].hide()
                await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envID, docId: this.newDocumentId})
                let newUrl = 'envelope/' + this.envID + '/document/' + this.newDocumentId
                this.showAlert = !this.showAlert

                // will route the user to the newUploaded document page (with the new ID)
                // for now it is working. But it will show before refreshing the new page an unable preview of the file

                await this.$store.dispatch('envelopes/fetchEnvelopes')
                this.$router.push('/' + this.$i18n.locale + '/' + newUrl).then(() => {
                    this.$router.go(0)
                })
            }
        },

        // convert the file into an base64 string
        async asyncHandleFunction() {
            return await convertUploadFileToBase64(this.file)
        },

        resetSettings(a) {
            if (a === 'modal-page1') {
                this.$refs[a].hide()
                this.fileString = ""
                this.actualDoc = {}
            } else if (a === 'modal-page2') {
                this.actualDoc = {}
            }
        }
    },

    mounted() {
        console.log(this.clicked)
    },

    updated() {
        console.log(this.clicked)
    }
}


</script>

<style scoped src="../assets/css/signModals.css">


</style>
