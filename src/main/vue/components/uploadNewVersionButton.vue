<template>
    <div>
        <b-button
            size="sm"
            style="margin-top: 7em; background-color: var(--elsa-blue)"
            v-b-modal.modal-1
        >
            Update Document
        </b-button>

        <b-modal
            id="modal-1"
            ref="my-modal1"
            centered :title="'Replace Document: ' + document.title"
            hide-footer ok-only no-stacking
        >
            <div>
                <h6>Select File to replace your Document</h6>
            </div>
            <div>
                <div class="modal-body">
                    <div>
                        <div class="form-group files">
                            <input
                                type="file"
                                id="fileInput"
                                class="form-control"
                                @change="previewFile"
                                style="height: 15vh"
                            >
                        </div>
                        <p> Selected File: {{fileString}}</p>
                    </div>
                </div>
            </div>
            <div class="text-right">
                <b-button @click="resetTest"> Cancel</b-button>
                <b-button
                    class="ml-1"
                    v-b-modal.modal-2
                    :disabled="fileString===''"
                >
                    Next
                </b-button>
            </div>
        </b-modal>


        <b-modal
            id="modal-2"
            centered

            :title= "document.title + ' Documentsettings'"
            hide-footer ok-only no-stacking
        >
            <div>
                <label>
                    <input v-model="newDoc.title" :placeholder="this.document.title">
                    {{this.newDoc.title}}
                </label>
            </div>
            <div>
                {{ $t('UploadDoc.configureDoc') }}

                <p>New File: {{ fileString }}</p>

            </div>
            <div class="text-right">
                <b-button v-b-modal.modal-1> Back</b-button>
                <b-button class="ml-1" v-b-modal.modal-3> Next</b-button>
            </div>
        </b-modal>

        <b-modal
            id="modal-3"
            ref="my-modal3"
            centered
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
                <h5>Are you Sure to replace the Document: {{document.title}}?</h5>
            </div>
            <p class="my-4">All signed Documents will be reseted after you uploaded an newer version of an Document </p>
            <div class="text-right">
                <b-button
                    @click="uploadNewFile"
                >
                    Save</b-button>
            </div>
        </b-modal>

    </div>
</template>

<script>
export default {
    name: "uploadNewVersionButton",
    data() {
        return {
            // filename of uploaded file
            fileString: "",
            file: File,

            // here need to paste all attributes of documents
            newDoc: {
                byte: [],
                creationDate: '',
                title: '',
                type: '',
                owner: {
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto #2",
                    lastname: "Wehner"
                },
                signatoriesId: [],
                readers: [],
                signatureType: '',
                endDate: '',
                orderRelevant: false,
                state: ''
            }
        }
    },
    props: ['document'],

    methods: {
        // save the selected File in the data
        previewFile(event) {
            console.log(event.target.files[0])
            this.fileString = event.target.files[0].name
            this.file = event.target.files[0]


        },

        // get all necessary attributes of documents
        getToDocumentSettings() {
            this.newDoc.type = this.file.prototype
            if(this.newDoc.title === '' || this.newDoc.title == null) {
                this.newDoc.title = this.document.title + ' #2'
            }
            if(this.newDoc.signatoriesId === '' || this.newDoc.signatoriesId == null) {
                this.newDoc.signatoriesId = this.document.signatoriesId
            }
            if(this.newDoc.readers === '' || this.newDoc.readers == null) {
                this.newDoc.readers = this.document.readers
            }
            if(this.newDoc.signatureType === '' || this.newDoc.signatureType == null) {
                this.newDoc.signatureType = this.document.signatureType
            }
            if(this.newDoc.endDate === '' || this.newDoc.endDate == null) {
                this.newDoc.endDate = this.document.endDate
            }
            if(this.newDoc.state === '' || this.newDoc.state == null) {

                this.newDoc.state = "open"
            }
            this.newDoc.creationDate = "31.06.2021"
            console.log(this.newDoc)

        },

        // emit the newDocument to the parent component for handle the updateDoc method
        async uploadNewFile() {
            //this.$emit('update-document', this.newDocument)
            this.$refs['my-modal3'].hide()
            this.fileString = ""
            this.byte = await this.asyncHandleFunction()
            this.newDoc.byte = await this.asyncHandleFunction()
            this.getToDocumentSettings()

            // emit the new doc to the parent
            this.$emit('update-document', this.newDoc)
        },

        // convert the promise base64 into an byte array
        async asyncHandleFunction() {
            const promise = await convertUploadFileToBase64(this.file)
            const bytes = [];
            for (let i = 0; i < promise.length; i++) {
                bytes.push(promise.charCodeAt(i))
            }
            return bytes
        },
        resetTest(){
            this.$refs['my-modal1'].hide()
            this.fileString = ""
        }
    },

}
// converting the selected file into base64 (as promise)
const convertUploadFileToBase64 = (file) => {
    const reader = new FileReader()
    return new Promise((resolve, reject) => {
        reader.onerror = (error) => {
            reader.abort()
            reject(error)
        }
        reader.onload = () => {
            resolve(reader.result.replace('data:', '').replace(/^.+,/, ''))
        }
        reader.readAsDataURL(file)
    })
}







</script>

<style scoped>


</style>
