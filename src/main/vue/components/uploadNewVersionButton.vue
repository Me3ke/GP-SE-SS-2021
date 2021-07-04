<template>
    <div>
        <!-- Page 1 -->
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
                        :disabled="file == null"
                        @click="setActualDoc"
                >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>
        </b-modal>

        <!--- Skip or Add Readers --->
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


        <!---Readers --->
        <b-modal
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


        <!---EndDate + Signatories--->
        <b-modal
            :id="'modal-' + docID + 'bbbb'"
            ref="modal-page2-signatories"
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

        <!--- Email Template --->
        <b-modal
        :id="'modal-'+ docID + 'c'"
        ref="modal-page3"
        class="modal-emailTemplate"
        centered
        :title= "document.title + ' Email Template' "

        ok-only hide-footer
        >
            <b-container>

                <b-row style="padding-bottom: 1em">
                    <EmailTemplate @saveEmailTemplate="setEmailTemplate"></EmailTemplate>
                </b-row>
            </b-container>


            <div class="flex-box-2">
                <button type="button"
                        class="mt-1 light-btn"
                        @click="showHideModal('modal-page3' , 'modal-page2-signatories')">
                     <span class="button-txt">
                         {{ $t('UploadDoc.back') }}
                     </span>
                </button>
                <button type="button"
                        class="ml-1 elsa-blue-btn"
                        @click="showHideModal('modal-page3', 'modal-page4')"
                >
                     <span class="button-txt">
                         {{ $t('UploadDoc.continue') }}
                     </span>
                </button>
            </div>

        </b-modal>
        <!-- Modal Page for save written email template--->
        <!-- Last PAGE  -->
        <b-modal
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
                        {{ $t('UploadDoc.continue') }} <!--UPLOAD -->
                    </span>
                </button>
                <!--<b-button
                    @click="uploadNewFile"
                    >
                    {{ $t('UploadDoc.UpdateDocument.update1') }}
                </b-button>-->

            </div>
        </b-modal>


        <!-- TODO Error Page  -->
        <b-modal
            :id="'modal-' + docID + 'error'"
            centered
            hide-footer hide-header ok-only no-stacking

        >
            <b-alert align="center">
                ERROR Page
            </b-alert>

        </b-modal>

    </div>
</template>

<script>
import {convertUploadFileToBase64} from "../scripts/fileToBase64Converter";
import {mapGetters} from "vuex";
import _ from "lodash";
import ReaderMenu from "@/main/vue/components/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/SignatoryMenu";
import EmailTemplate from "@/main/vue/components/EmailTemplate";

export default {
    name: "uploadNewVersionButton",
    components: {EmailTemplate, SignatoryMenu, ReaderMenu},
    data() {
        return {
            // filename of uploaded file
            fileString: "",
            file: null,
            showAlert: false,
            review: true,


            // need document settings as variable
            newTitle: '',

            // TODO Actual Doc which is going to be replaced
            actualDoc: {},
        }
    },
    props: ['document', 'docID', 'envID'],
    computed: {
        ...mapGetters({
            newDocumentId: 'document/getNewDocumentId',
            newDocumentError: 'document/getErrorEditDocument',
            newDocumentStatus: 'document/getEditDocumentStatus'
        }),
        showErrorReview: {
            get() {
                return !_.isEmpty(this.newDocumentError)
            }
        },

        isoDate(enDate) {
            const [day, month, year] = enDate.split('.')
            return year + '-' + month + '-' + day
        }

    },

    methods: {
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

            this.showHideModal('modal-page1', 'modal-page2')
        },


        // temp is the selected template
        setEmailTemplate(temp){
            // emailTemplates is an array instead of an object (in document)
            this.actualDoc.emailTemplateHtml = temp
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
            }
            else {
                this.$refs['modal-page4'].hide()

                await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envID, docId: this.newDocumentId})
                let newUrl = 'envelope/' + this.envID + '/document/' + this.newDocumentId
                this.showAlert = !this.showAlert

                // will route the user to the newUploaded document page (with the new ID)
                // for now it is working. But it will show before refreshing the new page an unable preview of the file
                this.file = null

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
            }
        },

        showHideModal(currentModalId, goToModalId) {
            this.$refs[goToModalId].show()
            this.$refs[currentModalId].hide()
            this.resetSettings(goToModalId)

        }
    }
}


</script>

<style scoped src="../assets/css/signModals.css">

</style>
