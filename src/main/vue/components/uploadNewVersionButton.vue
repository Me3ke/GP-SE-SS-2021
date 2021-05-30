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
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
                <h6>Select File to replace your Document</h6>
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
            // Todo need to add the new file as an ByteArray
            fileString: "",
            file: File,
            dataURL: ''
        }
    },
    props: ['document', 'newDocument'],

    methods: {
        // save the selected File in the data
        previewFile(event) {
            this.fileString = event.target.files[0].name
            this.file = event.target.files[0]
        },

        // emit the newDocument to the parent component for handle the updateDoc method
        uploadNewFile() {
            this.$emit('update-document', this.newDocument)
            this.$refs['my-modal3'].hide()
            this.fileString = ""
            this.asyncHandleFunction()

        },
        async asyncHandleFunction() {
            const promise = await convertUploadFileToBase64(this.file)
            let newString = promise.replace('data:', '').replace(/^.+,/, '');
            console.log(newString)
            this.dataURL = newString
        },
        resetTest(){
            this.$refs['my-modal1'].hide()
            this.fileString = ""
        }

    },

}

const convertUploadFileToBase64 = (file) => {
    const reader = new FileReader()
    return new Promise((resolve, reject) => {
        reader.onerror = (error) => {
            reader.abort()
            reject(error)
        }

        reader.onload = () => {
            resolve(reader.result)
        }
        reader.readAsDataURL(file)
    })
}







</script>

<style scoped>


</style>
