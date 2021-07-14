<template>
    <div>
        <button class="elsa-blue-btn" @click="show = true">
            <h4>
                <b-icon icon="plus-circle"></b-icon>
                {{$t('UploadDoc.upload')}}
            </h4>
        </button>
        <div v-if="show">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 v-if="page === 1" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 2" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 3" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5 v-if="page === 4" class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false; close()">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Page 1 Choose Document -->
                                <div v-if="page === 1">
                                    <FileInput @updateFiles="updateFiles" @close="show = false" @nextPage="page = page + 1"></FileInput>
                                </div>

                                <!-- Page 2 New or old envelope? -->
                                <div v-if="page === 2">
                                    <SelectEnvelope @updateEnvelope="updateEnvelope" @nextPage="page = page + 1" @previousPage="page = page -1"></SelectEnvelope>
                                </div>

                                <!-- Page 3 Add signatories/readers-->
                                <div v-if="page === 3">
                                    <UploadSettings @updateSettings="updateSettings" @nextPage="page = page + 1" @previousPage="page = page - 1"></UploadSettings>
                                </div>

                                <!-- Page 4 Upload -->
                                <div v-if="page === 4">
                                    <div class="modal-body">
                                        <div v-if="!this.uploadingDocument">
                                            <SettingsPreview :settings="settings" :selectedEnvelope="selectedEnvelope" :files="files"></SettingsPreview>
                                        </div>

                                        <div v-if="this.uploadingDocument">
                                            <b-spinner></b-spinner>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <b-row align-h="end">
                                            <b-col cols="auto">
                                                <button class="light-btn" @click="page = page - 1;">
                                                    {{$t('UploadDoc.back')}}
                                                </button>
                                            </b-col>
                                            <b-col cols="auto">
                                                <button class="elsa-blue-btn" @click="upload()">
                                                    {{$t('UploadDoc.upload')}}
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
import SelectEnvelope from "@/main/vue/components/uploadDocuments/SelectEnvelope";
import FileInput from "@/main/vue/components/uploadDocuments/FileInput";
import UploadSettings from "@/main/vue/components/uploadDocuments/UploadSettings";
import SettingsPreview from "@/main/vue/components/uploadDocuments/SettingsPreview";
export default {
    name: 'UploadButton',
    props: {
        text: String
    },
    components: {SelectEnvelope, FileInput, UploadSettings, SettingsPreview},
    data() {
        return {
            show: false,
            page: 1,
            selectedEnvelope: {
                id: null,
                name: null
            },
            settings: {
                signatories: [],
                endDate: null,
                orderRelevant: true,
                showHistory: true,
                // TODO: processStart: Boolean
            },
            files: [],
            uploadingDocument: false
        };
    },
    methods: {
        updateFiles: function (files) {
            this.files = files;
        },
        updateEnvelope: function (envelope) {
            this.selectedEnvelope = envelope;
        },
        updateSettings: function (settings) {
          this.settings = settings;
        },
        close() {
            this.page = 1;
            this.files = [];
            this.selectedEnv = {old: null, new: null};
            this.settings = {signatories: [], endDate: null, orderRelevant: null};
            this.show = false;
            this.uploadingDocument = false;
        },
        async upload() {
            if (!(this.selectedEnvelope.id === null)) {
                this.uploadingDocument = true;
                let i;
                for(i = 0; i < this.files.length; i++) {
                    await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.selectedEnvelope.id, "file":this.files[i], "settings": this.settings});
                }
                this.$emit("refreshOverview")
                this.close();
            } else {
                this.uploadingDocument = true;
                await this.$store.dispatch('documentUpload/createEnvelope', {"name": this.selectedEnvelope.name})
                let i;
                for(i = 0; i < this.files.length; i++) {
                await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.getCreatedEnvelope.id, "file":this.files[i], "settings": this.settings});
                }
                this.$emit("refreshOverview")
                this.close();
            }
        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelopes: 'envelopes/getEnvelopes',
            getCreatedEnvelope: "documentUpload/getCreatedEnvelope"
        })
    }
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 10000;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-content {
    max-height: 50em;
    overflow-y: scroll;
    background-color: var(--whitesmoke);
}

.bi-x-square {
    fill: var(--dark-grey);
}

.bi-x-square:hover {
    fill: var(--light-grey);
    transition: 0.4s;
}

.elsa-blue-btn, .light-btn {
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}

.grey-btn {
    background-color: var(--light-grey);
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
    color: var(--dark-grey);
    border-radius: 0.33vw;
}


</style>
