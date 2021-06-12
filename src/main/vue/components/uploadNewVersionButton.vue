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
            :title= "document.title + '  Documentsettings'"
            hide-footer ok-only no-stacking
        >
            <div>
                <button type="button"
                        class="light-btn"
                        @click="settings.review = true;"
                        v-b-modal="'modal-' + docID + 'bbb'"
                >
                    <h5>
                        {{$t('UploadDoc.addReaders')}}
                    </h5>
                </button>
                <button type="button"
                        class="light-btn"
                        @click="settings.review = false;"
                        v-b-modal="'modal-' + docID + 'bbbb'"

                >
                    <h5>
                        {{$t('UploadDoc.skipReview')}}
                    </h5>
                </button>
            </div>
        </b-modal>





        <!---Readers --->
        <b-modal
            :id="'modal-' + docID + 'bbb'"
            ref="modal-page2"
            centered
            :title= "document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <ReaderMenu :readers="actualDoc.readers"></ReaderMenu>
            </div>

            <div>
                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetTest('modal-page2')"
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


        <!---EndDate + Signatories--->
        <b-modal
            :id="'modal-' + docID + 'bbbb'"
            ref="modal-page2"
            centered
            :title= "document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <div>
                    <label for="endDatePicker">{{$t('Settings.DocumentSettings.chooseDate')}}</label>
                    <b-form-datepicker id="endDatePicker" v-model="actualDoc.endDate" class="mb-2"></b-form-datepicker>
                    <p>{{actualDoc.endDate}}</p>
                </div>
                <SignatoryMenu :signatories="actualDoc.signatories" :orderRelevant="actualDoc.orderRelevant"></SignatoryMenu>
            </div>
            <div>
                <button type="button"
                        class="mt-1 light-btn"
                        @click="resetTest('modal-page2')"
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











        <!-- Last PAGE  -->
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
                ERROR TEST
            </b-alert>

        </b-modal>

    </div>
</template>

<script>
import {convertUploadFileToBase64} from "./fileToBase64Converter";
import {mapGetters} from "vuex";
import _ from "lodash";
import ReaderMenu from "@/main/vue/components/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/SignatoryMenu";

export default {
    name: "uploadNewVersionButton",
    components: {SignatoryMenu, ReaderMenu},
    data() {
        return {
            // filename of uploaded file
            fileString: "",
            file: null,
            showAlert: false,


            // need document settings as variable
            newTitle: '',

            // TODO Actual Doc which is going to be replaced
            actualDoc: {},

            settings: {
                review: true,
                signatories: [],
                readers: [],
                endDate: '',
                orderRelevant: true
            }
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

        isoDate(enDate){
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
            console.log("BEFORE")
            this.actualDoc = this.document
            console.log((this.actualDoc))


            //this.actualDoc.orderRelevant = false
            this.actualDoc.lastModified = new Date(this.file.lastModified).toISOString()
            //this.actualDoc.signatoriesId = []
            //this.actualDoc.readersId = []

            // todo TimePicker needed ?
            // todo have to change the date into iso type for the datePicker
            if(this.actualDoc.endDate !== '' || this.actualDoc.endDate != null) {
                const [day, month, year] = this.actualDoc.endDate.split('.')
                this.actualDoc.endDate = year + '-' + month + '-' + day
            }
            this.actualDoc.dataType = this.fileString.split('.').pop()
            this.actualDoc.signed = false
            this.actualDoc.read = false
            this.actualDoc.state = 'OPEN'
        },

        /// getting emitted value
        async uploadNewFile() {

            this.fileString = ""
            this.actualDoc.endDate = this.actualDoc.endDate + ' 12:00'
            // TODO setting the documentSettings values

            this.actualDoc.data =  await this.asyncHandleFunction()
            console.log("READY")
            console.log(this.actualDoc)
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
                console.log(newUrl)



                // will route the user to the newUploaded document page (with the new ID)
                // for now it is working. But it will show before refreshing the new page an unable preview of the file
                //this.$router.push('/' + this.$i18n.locale + '/' + newUrl).then(() => {
                 //   this.$router.go(0)
               // })
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
