<template>
    <div>
        <b-button size="sm" style="margin-top: 7em; background-color: var(--elsa-blue)" v-b-modal.modal-5>{{$t('UploadDoc.configureDoc')}}
        </b-button>

        <b-modal id="modal-5" centered :title="documentTitle" hide-footer ok-only no-stacking>
            <div>
                <div class="modal-body">
                    <div>
                        <div class="form-group files">
                            <input type="file" id="fileInput" class="form-control" @change="previewFile" style="height: 15vh">
                        </div>
                    </div>
                    <p>New File: {{newFile}}</p>
                </div>
            </div>
            <div class="text-right">
                <b-button v-b-modal.modal-6 :disabled="newFile===''"> Next</b-button>
            </div>
        </b-modal>


        <b-modal id="modal-6" centered title="BootstrapVue" hide-footer ok-only no-stacking>
            <div>
                {{$t('UploadDoc.configureDoc')}}
                <p>New File: {{newFile}}</p>

            </div>
            <div class="text-right">
                <b-button v-b-modal.modal-5> Back</b-button>
                <b-button class="ml-1" v-b-modal.modal-7> Next</b-button>
            </div>
        </b-modal>

        <b-modal id="modal-7" centered title="BootstrapVue" hide-footer ok-only no-stacking>
            <p class="my-4">Hello from modal #3!</p>
            <div class="text-right">
                <b-button @click="$bvModal.hide('modal-7')">Finish</b-button>
            </div>
        </b-modal>

    </div>
</template>

<script>

export default {
    name: "uploadNewVersionButton",
    components: {},
    data() {
        return {
            newFile: "",
        }
    },
    props: {
        envId: [Number, String],
        docId: {
            type: [Number, String]
        },
        documentTitle: String,

    },

    methods: {
        previewFile(event) {
            console.log(event.target.files[0])
            this.newFile = event.target.files[0].name
        }
    },

}
</script>

<style scoped>

</style>
