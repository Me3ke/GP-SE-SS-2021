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
                <button type="button" class="mt-1 light-btn" @click="resetTest('modal-page1')">
                    <span class="button-txt">
                        {{ $t('UploadDoc.close') }}
                    </span>
                </button>

                <button type="button"
                        class="mt-1 elsa-blue-btn"
                        v-b-modal="'modal-' + docID + 'b'"
                        :disabled="file == null"
                        @click="setActualDoc"
                        >
                    <span class="button-txt">
                        {{ $t('UploadDoc.continue') }}
                    </span>
                </button>
            </div>
        </b-modal>

        <!-- Page 2 -->
        <b-modal
            :id="'modal-' + docID + 'b'"
            ref="modal-page2"
            centered
            :title= "document.title + '  Documentsettings'"
            hide-footer ok-only no-stacking
        >
            <div>
                <label>
                    <input v-model="newTitle" :placeholder="this.document.title">
                    {{newTitle}}
                </label>
            </div>
            <div>
                {{ $t('UploadDoc.configureDoc') }}

                <p>New File: {{ fileString }}</p>

            </div>
            <div class="text-right">
                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetTest('modal-page2')"
                        v-b-modal="'modal-' + docID + 'a'"
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



<!--
                <b-button v-b-modal="'modal-' + docID + 'a'" @click="resetTest">  {{ $t('UploadDoc.back') }}</b-button>
                <b-button class="ml-1" v-b-modal="'modal-' + docID + 'c'">  {{ $t('UploadDoc.continue') }}</b-button>
                -->
            </div>
        </b-modal>

        <!-- Page 3 -->
        <b-modal
            :id="'modal-' + docID + 'c'"
            ref="modal-page3"
            centered
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
                <h5> {{ $t('UploadDoc.UpdateDocument.confirmation', {documentTitle: document.title}) }}</h5>
            </div>
            <p class="my-4"> {{ $t('UploadDoc.UpdateDocument.signatureResetReminder') }}  {{showAlert}} </p>
            <div class="text-right">


                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetTest('modal-page3')"
                        v-b-modal="'modal-' + docID + 'b'"
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
            <b-alert align="center" show>
                ERROR TEST
            </b-alert>

        </b-modal>

    </div>
</template>

<script>
import {convertUploadFileToBase64} from "./fileToBase64Converter";
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "uploadNewVersionButton",
    data() {
        return {
            // filename of uploaded file
            fileString: "",
            file: null,
            showAlert: false,


            // need document settings as variable
            newTitle: '',

            // TODO Actual Doc which is going to be replaced
            actualDoc: {}
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
        }

    },

    methods: {
        // save the selected File in the data
        previewFile(event) {
            this.fileString = event.target.files[0].name
            this.file = event.target.files[0]
        },


        // get all necessary attributes of documents

        /*

        title, type < first modal page @clickAction
        list readersId < second modal page @clickAction
        list signatoriesID, orderRelevant, signatureType, localDateTime endDate < third modal page @clickAction
        data < last modal Page

        document state < - automatically open
        lastModified <

         */
        setActualDoc() {
            this.actualDoc = this.document

            this.actualDoc.orderRelevant = false
            this.actualDoc.lastModified = new Date(this.file.lastModified).toISOString()
            this.actualDoc.signatoriesId = []
            this.actualDoc.readersId = []
            this.actualDoc.endDate = "2021-06-15-21:15:02.933Z"
            this.actualDoc.dataType = this.fileString.split('.').pop()
            this.actualDoc.signed = false
            this.actualDoc.read = false
            this.actualDoc.reader = false
        },

        /// getting emitted value
        async uploadNewFile() {

            this.fileString = ""

            // TODO setting the documentSettings values

            // if input field is empty then it will use the title from the actual document
            if(this.newTitle !== '' || this.newTitle !== "") {
                this.actualDoc.title = this.newTitle
            }
            this.actualDoc.data =  await this.asyncHandleFunction()


            // todo add error (but neet status code on the reponse, for now only getting newDocId and replaced one which is getting an new id too)
            let payload = {newDoc: this.actualDoc, envId: this.envID, docId: this.docID}
            await this.$store.dispatch('document/editDocument', payload)

            // here show error
            if(this.showAlert) {
                this.$refs['modal-page3'].hide()
                this.$bvModal.show('modal-' + this.docID + 'error')
                this.actualDoc = {}
                this.fileString = ''
                this.file = null
            }
            else {
                this.$refs['modal-page3'].hide()
                await this.$store.dispatch('document/fetchDocument', {envId: this.envID, docId: this.newDocumentId})
                let newUrl = 'envelope/' + this.envID + '/document/' + this.newDocumentId
                this.showAlert = !this.showAlert



                // will route the user to the newUploaded document page (with the new ID)
                // for now it is working. But it will show before refreshing the new page an unable preview of the file
                this.$router.push('/' + this.$i18n.locale + '/' + newUrl).then(() => {
                    this.$router.go(0)
                })
            }
        },

        // convert the file into an base64 string
        async asyncHandleFunction() {
            return await convertUploadFileToBase64(this.file)
        },

        //todo
        resetTest(a) {
            if(a === 'modal-page1') {
                this.$refs[a].hide()
                this.fileString =""
                this.actualDoc = {}
            } else if(a === 'modal-page2') {
                this.actualDoc = {}
            }
        }
    }
}










</script>

<style scoped src="../assets/css/signModals.css">


</style>
