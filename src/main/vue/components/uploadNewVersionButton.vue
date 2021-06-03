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
                    <input v-model="actualDoc.title" :placeholder="this.document.title">
                    {{this.actualDoc.title}}
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


            // TODO Actual Doc which is going to be replaced
            actualDoc: {}
        }
    },
    props: ['document'],

    methods: {
        // save the selected File in the data
        previewFile(event) {
            this.fileString = event.target.files[0].name
            this.file = event.target.files[0]


        },

        // get all necessary attributes of documents

        /*

        byte data <
        title <
        type <
        list signatoriesID <
        list readersId <
        signatureType <
        localDateTime endDate
        orderRelevant <
        document state <
        lastModified <

         */
        getToDocumentSettings() {
            this.actualDoc = this.document

            console.log('------------')
            console.log(this.actualDoc)
            console.log('------------')



            // todo Document settings input fields
            this.actualDoc.orderRelevant = false
            this.actualDoc.lastModified = new Date(this.file.lastModified).toISOString()
            this.actualDoc.signatoriesId = []
            this.actualDoc.readersId = []
            this.actualDoc.endDate = "2021-06-15-21:15:02.933Z"
            this.actualDoc.byte = []
            this.actualDoc.dataType = "pdf" // todo
            this.actualDoc.signed = false
            this.actualDoc.read = false
            this.actualDoc.reader = false
        },

        // emit the newDocument to the parent component for handle the updateDoc method
        async uploadNewFile() {
            this.$refs['my-modal3'].hide()
            this.fileString = ""

            this.getToDocumentSettings()
            this.actualDoc.byte =  await this.asyncHandleFunction()


            // emit the new doc to the parent
            this.$emit('update-document', this.actualDoc)
        },

        // convert the promise base64 into an byte array
        async asyncHandleFunction() {
            const promise = await convertUploadFileToBase64(this.file)
            let baseAndByte = []
            let bytes =  []
            for (let i = 0; i < promise.length; i++) {
                bytes.push(promise.charCodeAt(i))
            }

            baseAndByte.push(promise)
            baseAndByte.push(bytes)
            return baseAndByte
        },
        resetTest() {
            this.$refs['my-modal1'].hide()
            this.fileString = ""
        },

        base64ToArrayBuffer(base64) {
            const binary_string = window.atob(base64);
            const len = binary_string.length;
            const bytes = new Uint8Array(len);
            for (let i = 0; i < len; i++) {
                bytes[i] = binary_string.charCodeAt(i);
            }
            return bytes.buffer;
        }
    }
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
