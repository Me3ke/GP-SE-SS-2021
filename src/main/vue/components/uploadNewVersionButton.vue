<template>
    <div>
        <b-button
            size="sm"
            style="margin-top: 7em; background-color: var(--elsa-blue)"
            v-b-modal.modal-5
        >
            Neue Version Hochladen
        </b-button>

        <b-modal
            id="modal-5"
            ref="my-modal2"
            centered :title="document.title"
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
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
                    v-b-modal.modal-6
                    :disabled="fileString===''"
                >
                    Next
                </b-button>
            </div>
        </b-modal>


        <b-modal
            id="modal-6"
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
                <b-button v-b-modal.modal-5> Back</b-button>
                <b-button class="ml-1" v-b-modal.modal-7> Next</b-button>
            </div>
        </b-modal>

        <b-modal
            id="modal-7"
            ref="my-modal"
            centered
            hide-footer hide-header ok-only no-stacking
        >
            <div>
                <h3>{{document.title}}</h3>
                <h5>Are you Sure to replace the Document: {{document.title}}?</h5>
            </div>
            <p class="my-4">All signed Documents will be reseted after you uploaded an newer version of an Document </p>
            <div class="text-right">
                <b-button @click="uploadNewFile">Save</b-button>
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
        }
    },
    props: ['document', 'newDocument'],

    methods: {
        // save the selected File in the data
        previewFile(event) {
            this.fileString = event.target.files[0].name
        },

        // emit the newDocument to the parent component for handle the updateDoc method
        uploadNewFile() {
            this.$emit('update-document', this.newDocument)
            this.$refs['my-modal'].hide()
            this.fileString = ""
        },
        resetTest(){
            this.$refs['my-modal2'].hide()
            this.fileString = ""
        }
    },

}
</script>

<style scoped>


</style>
