<template>
    <div>
        <!--

        <b-button

            style="margin-top: 7em; background-color: var(--elsa-blue)"
            v-b-modal="'modal-' + docID + 'a'"
        >
            {{ $t('UploadDoc.UpdateDocument.update2') }}
        </b-button>

        --->
        <b-modal
            :id="'modal-' + docID + 'a'"
            ref="my-modal1"
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
                            <!-- File browse button is only in German available (because of browser language setting) -->

                            <input
                                type="file"
                                id="fileInput"
                                class="form-control"
                                @change="previewFile"
                                style="height: 15vh"
                            >
                        </div>
                        <!-- Not necessary --->
                        <p> Selected File: {{fileString}}</p>
                    </div>
                </div>
            </div>
            <div class="text-right">
                <b-button @click="resetTest">
                    {{ $t('UploadDoc.close') }}
                </b-button>
                <b-button
                    class="ml-1"
                    v-b-modal="'modal-' + docID + 'b'"
                    :disabled="fileString===''"

                    @click="setActualDoc"
                >
                    {{ $t('UploadDoc.continue') }}
                </b-button>
            </div>
        </b-modal>


        <b-modal
            :id="'modal-' + docID + 'b'"
            centered

            :title= "document.title + ' Documentsettings'"
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
                <b-button v-b-modal="'modal-' + docID + 'a'" @click="resetTest">  {{ $t('UploadDoc.back') }}</b-button>
                <b-button class="ml-1" v-b-modal="'modal-' + docID + 'c'">  {{ $t('UploadDoc.continue') }}</b-button>
            </div>
        </b-modal>

        <b-modal
            :id="'modal-' + docID + 'c'"
            ref="my-modal3"
            centered
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
                <h5> {{ $t('UploadDoc.UpdateDocument.confirmation', {documentTitle: document.title}) }}</h5>
            </div>
            <p class="my-4"> {{ $t('UploadDoc.UpdateDocument.signatureResetReminder') }} </p>
            <div class="text-right">
                <b-button
                    @click="uploadNewFile"
                    >
                    {{ $t('UploadDoc.UpdateDocument.update1') }}
                </b-button>
            </div>
        </b-modal>

    </div>
</template>

<script>
import {convertUploadFileToBase64} from "./fileToBase64Converter";

export default {
    name: "uploadNewVersionButton",
    data() {
        return {
            // filename of uploaded file
            fileString: "",
            file: File,


            // need document settings as variable
            newTitle: '',

            // TODO Actual Doc which is going to be replaced
            actualDoc: {}
        }
    },
    props: ['document', 'docID'],

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

            console.log('------------')
            console.log(this.actualDoc)
            console.log('------------')

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




        // emit the newDocument to the parent component for handle the updateDoc method

        /// getting emmited value
        async uploadNewFile() {
            this.$refs['my-modal3'].hide()
            this.fileString = ""

            // TODO setting the documentSettings values

            // if input field is empty then it will use the title from the actual document
            if(this.newTitle !== '' || this.newTitle !== "") {
                this.actualDoc.title = this.newTitle
            }
            this.actualDoc.data =  await this.asyncHandleFunction()

            console.log(this.actualDoc.data)


            // emit the new doc to the parent/other child
            this.$emit('update-document', this.actualDoc)
        },

        // convert the file into an base64 string
        async asyncHandleFunction() {
            return await convertUploadFileToBase64(this.file)
        },
        resetTest() {
            this.$refs['my-modal1'].hide()
            this.fileString = ""
            this.actualDoc = {}
        }
    }
}










</script>

<style scoped>


</style>
