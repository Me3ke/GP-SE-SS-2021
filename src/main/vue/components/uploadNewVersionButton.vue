<template>
    <div>
        <!-- Page 1 -->
        <!---
        <b-modal
            modal-class="model-class"
            :id="'modal-' + docID + 'a'"
            ref="modal-page1"
            centered :title="$t('UploadDoc.UpdateDocument.replaceDoc') + document.title"
            style="background-color: var(--whitesmoke); color: var(--dark-grey)"
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
                        :disabled="file == null"
                        @click="setActualDoc"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>
        </b-modal>

        <--- Skip or Add Readers ---
        <b-modal
            modal-class="model-class"
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
            style="margin-top: 2em;"
            modal-class="model-class"
            :id="'modal-' + docID + 'bbb'"
            ref="modal-page2-reader"
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
                        v-b-modal="'modal-' + docID + 'b'"
                        @click="showHideModal('modal-page2-reader', 'modal-page2')"

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
            modal-class="model-class"
            :id="'modal-' + docID + 'bbbb'"
            ref="modal-page2-signatories"
            centered
            :title="document.title"
            style="margin-top: 2em; padding-top: 2em"
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

            <div class="text-right">
                <button type="button"
                        class="mt-1 light-btn"
                        v-b-modal="this.actualDoc.review ? 'modal-' + docID + 'bbb' : 'modal-' + docID + 'bb' "
                        @click="showHideModal('modal-page2-signatories',  'modal-page2' )">

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

        <--- Email Template --
        <b-modal
            modal-class="model-class"
            :id="'modal-'+ docID + 'c'"
            ref="modal-page3"
            class="modal-emailTemplate"
            centered scrollable
            :title= "document.title + '  ' +
            $t('EmailTemplate.emailTemp') "
            style="margin-top: 2em"
            ok-only hide-footer
        >
            <b-container>

                <b-row style="padding-bottom: 1em">
                    <---emit email templates with '@saveEmailTemplate' ---
                    <EmailTemplate @saveEmailTemplate="setEmailTemplate"></EmailTemplate>
                </b-row>
            </b-container>
            <div style="padding-bottom: 1em; text-align: right" class="flex-box-2">

                <b-button
                    class="mt-1 light-btn"
                    @click="showHideModal('modal-page3' , 'modal-page2-signatories')"
                    style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em; width: auto"
                >
                    {{ $t('UploadDoc.back') }}
                </b-button>

                <b-button
                      class="ml-1 elsa-blue-btn"
                      @click="showHideModal('modal-page3', 'modal-page4')"
                      style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em; width: auto;"
                >
                    {{ $t('UploadDoc.continue') }}
                </b-button>
            </div>

        </b-modal>
        <-- Modal Page for save written email template---
        <-- Last PAGE  --
        <-- Last PAGE  --
        <b-modal
            modal-class="model-class"
            :id="'modal-' + docID + 'd'"
            ref="modal-page4"
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
                        v-b-modal="'modal-' + docID + 'c'"
                        @click="showHideModal('modal-page4', 'modal-page3')">

                    <span class="button-txt">
                        {{ $t('UploadDoc.back') }}
                    </span>
                </button>

                <button type="button"
                        class="ml-1 elsa-blue-btn"
                        @click="uploadNewFile"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.upload') }} <--UPLOAD --
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
            modal-class="model-class"
            :id="'modal-' + docID + 'error'"
            centered
            hide-footer hide-header ok-only no-stacking

        >
            <b-alert align="center">
                ERROR Page
            </b-alert>

        </b-modal>

        --->


        <div v-if="clicked && !uploadingDocument">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 v-if="page === 1" class="modal-title">{{ $t('UploadDoc.menuTitle') }}</h5>
                                    <h5 v-if="page === 2" class="modal-title">{{ $t('UploadDoc.menuTitle') }}</h5>
                                    <h5 v-if="page === 3" class="modal-title">{{ $t('UploadDoc.menuTitle') }}</h5>
                                    <h5 v-if="page === 4" class="modal-title">{{ $t('UploadDoc.menuTitle') }}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false; close()">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Page 1 Choose Document -->
                                <div v-if="page === 1">
                                    <div style="padding-top: 1em; text-align: left">
                                        <div v-if="file.title !== null">
                                            <span style="margin-left: 1em">Document Title</span>
                                            <b-input v-if="file" v-model="file.title"
                                                     style="width: 80%; margin-left: 1em"></b-input>
                                        </div>
                                        <FileInput @updateFiles="updateFile"
                                                   @close="show = false; close()"
                                                   @nextPage="page = page + 1"
                                                   :moreFiles="false"
                                        ></FileInput>
                                    </div>
                                </div>

                                <div v-if="page === 2">
                                    <div style="padding-top: 1em; text-align: left">
                                        <div v-if="file.title !== null">
                                            <span style="margin-left: 1em">Document Title</span>
                                            <b-input v-if="file" v-model="newDocumentTitle"
                                                     style="width: 80%; margin-left: 1em"></b-input>
                                        </div>
                                        <div class="modal-footer">
                                            <b-container>
                                                <b-row align-h="end">
                                                    <b-col cols="auto">
                                                        <button type="button" class="light-btn"
                                                                @click="page = page - 1">
                                                            <span>
                                                                {{ $t('UploadDoc.close') }}
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

                                <!-- Page 3 Add signatories/readers-->
                                <div v-if="page === 3">
                                    <UploadSettings :alreadySetSettings="settings"
                                                    @updateSettings="updateSettings"
                                                    @showEmailTemplate="showEmailTemplate"
                                                    @previousPage="page = page - 1"></UploadSettings>
                                </div>


                                <!-- Email Template (Replaced Document Email template) -->
                                <div v-if="page === 5">
                                    <div class="modal-body">
                                        <div>
                                            <div>
                                                <h3>Update document email </h3>
                                                <h5> Due to an Update Request we are sending to all signatories an Update Document Email</h5>
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
                                                <button class="elsa-blue-btn" @click="upload">
                                                    {{ $t('UploadDoc.continue') }}
                                                </button>
                                            </b-col>
                                        </b-row>
                                    </div>
                                </div>

                                <!-- Email Template (for new User) -->
                                <div v-if="page === 4">
                                    <div class="modal-body">
                                        <div>
                                            {{page}}
                                            <EmailTemplate @saveEmailTemplate="setEmailTemplate"></EmailTemplate>
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
                                                <button class="elsa-blue-btn" @click="upload">
                                                    {{ $t('UploadDoc.continue') }}
                                                </button>
                                            </b-col>
                                        </b-row>
                                    </div>
                                </div>


                                <!-- Page 4 Upload -->
                                <div v-if="page === 6">
                                    <div class="modal-body">
                                        <div v-if="!this.uploadingDocument">
                                            <div>
                                                <h3>{{ document.title }}</h3>
                                                <h5> {{
                                                        $t('UploadDoc.UpdateDocument.confirmation', {documentTitle: document.title})
                                                    }}</h5>
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
                                                <button class="elsa-blue-btn" @click="upload">
                                                    {{ $t('UploadDoc.upload') }}
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
import {mapGetters} from "vuex";
import FileInput from "@/main/vue/components/uploadDocuments/FileInput";
import UploadSettings from "@/main/vue/components/uploadDocuments/UploadSettings";
import EmailTemplate from "@/main/vue/components/EmailTemplate";

export default {
    name: "uploadNewVersionButton",
    components: {EmailTemplate, FileInput, UploadSettings},
    data() {
        return {


            show: true,

            showTemplateBoolean: false,


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
            newDocumentTitle: '',

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
            this.file = file[0];
            this.actualDoc = this.document
            this.settings.endDate = this.actualDoc.endDate
            this.settings.signatories = this.actualDoc.signatories
            this.settings.orderRelevant = this.actualDoc.orderRelevant
            this.newDocumentTitle = this.file.title
        },


        updateSettings: function (settings) {
            this.settings = settings;
            console.log(this.settings)
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
        /* NEWWW */
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
            console.log(newUrl)
            console.log(this.actualDoc)

            // will route the user to the newUploaded document page (with the new ID)
            // for now it is working. But it will show before refreshing the new page an unable preview of the file
            await this.$store.dispatch('envelopes/fetchEnvelopes')
            /*this.$router.push('/' + this.$i18n.locale + '/' + newUrl).then(() => {
                this.$router.go(0)
            })*/

            //this.close()
        },

        // temp is the selected template
        setEmailTemplate(temp) {
            // emailTemplates is an array instead of an object (in document)
            this.actualDoc.emailTemplateHtml = temp
        },

        showEmailTemplate(showTemp) {
            console.log(showTemp)
            if(showTemp === true) {
                // new registered signatory is noticed
                this.showTemplateBoolean = true
                this.page = 4
            } else {
                // no new registered signatory is noticed
                this.showTemplateBoolean = true
                this.page = 5
            }
        }
    }
}


</script>

<style scoped src="../assets/css/signModals.css">


</style>
