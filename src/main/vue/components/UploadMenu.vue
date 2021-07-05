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
                                    <h5 class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false; close()">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Menu -->
                                <div class="modal-body">
                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <b-form-file
                                            v-model="fileInput"
                                            :state="Boolean(fileInput)"
                                            v-bind:placeholder="$t('UploadDoc.chooseFile')"
                                            drop-placeholder="Drop file here..."
                                        ></b-form-file>
                                    </div>

                                    <!-- Page 2 -->
                                    <div v-if="page === 2">
                                        <button type="button" class="light-btn" @click="newEnv = false; page = page +1; selectedEnv.new = null;">
                                            <h5>
                                                {{$t('UploadDoc.selectEnv')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="newEnv = true; page = page +1; selectedEnv.old = null;">
                                            <h5>
                                                {{$t('UploadDoc.newEnv')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 3 -->
                                    <div v-if="page === 3">
                                        <!-- Choose from envelopes -->
                                        <div v-if="newEnv === false">
                                            <div class="form-group">
                                                <label for="selectEnvelope"> {{$t('UploadDoc.selectEnv')}} </label>
                                                <select class="form-control" id="selectEnvelope" v-model="selectedEnv.old">
                                                    <option v-for="envelope in this.envelopes" :key="envelope.id" :value="envelope.id"> {{envelope.name}} </option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- Create new envelope -->
                                        <div v-if="newEnv === true">
                                            <b-form-group
                                                v-bind:label="$t('UploadDoc.newEnv')"
                                                label-align="left"
                                                label-size="lg"
                                            >
                                                <b-form-input
                                                    id="envelope_input"
                                                    v-model="selectedEnv.new"
                                                    v-bind:placeholder="$t('UploadDoc.newEnvPlaceholder')"
                                                >
                                                </b-form-input>
                                            </b-form-group>
                                        </div>
                                    </div>
                                    <!-- Page 4 -->
                                    <div v-if="page === 4">
                                        <button type="button" class="light-btn" @click="review = true; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.addReaders')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="review = false; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.skipReview')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 5 -->
                                    <div v-if="page === 5">
                                        <div v-if="review">
                                            <ReaderMenu :readers="settings.readers"></ReaderMenu>
                                        </div>
                                        <div v-if="!review">
                                            <div>
                                                <label for="endDatePicker">{{$t('Settings.DocumentSettings.chooseDate')}}</label>
                                                <b-form-datepicker id="endDatePicker" v-model="settings.endDate" class="mb-2"></b-form-datepicker>
                                                <p>{{this.settings.endDate}}</p>
                                            </div>
                                            <SignatoryMenu :signatories="settings.signatories" :orderRelevant="settings.orderRelevant"></SignatoryMenu>
                                        </div>
                                    </div>
                                </div>
                                <!-- Buttons -->
                                <div class="modal-footer">
                                    <b-container fluid>
                                        <b-row align-h="between">

                                            <b-col cols="5">
                                                <div class="mt-3"> {{$t('UploadDoc.selectedDoc')}} {{ fileInput ? fileInput.name : '' }}</div>
                                            </b-col>
                                            <b-col cols="7">
                                                <b-row>
                                                    <!-- Page 1 -->
                                                    <div v-if="page === 1">
                                                        <button type="button" class="light-btn" @click="show = false; close()">
                                                            <h5>
                                                                {{$t('UploadDoc.close')}}
                                                            </h5>
                                                        </button>

                                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1">
                                                            <h5>
                                                                {{$t('UploadDoc.continue')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 2 -->
                                                    <div v-if="page === 2">
                                                        <button type="button" class="light-btn" @click="page = page - 1">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                    </div>

                                                    <!-- Page 3 -->
                                                    <div v-if="page === 3">
                                                        <button type="button" class="light-btn" @click="page = page - 1">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1;">
                                                            <h5>
                                                                {{$t('UploadDoc.continue')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 4 -->
                                                    <div v-if="page === 4">
                                                        <button type="button" class="light-btn" @click="page = page - 1">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 5 -->
                                                    <div v-if="page === 5">
                                                        <button type="button" class="light-btn" @click="page = page - 1">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="upload()">
                                                            <h5>
                                                                {{$t('UploadDoc.upload')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                </b-row>
                                            </b-col>
                                        </b-row>
                                    </b-container>
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
import SignatoryMenu from "@/main/vue/components/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/ReaderMenu";
//import {mapActions} from "vuex";
import {convertUploadFileToBase64} from "@/main/vue/scripts/fileToBase64Converter";
import {mapGetters} from "vuex";
export default {
    name: 'UploadButton',
    props: {
        text: String
    },
    components: {SignatoryMenu, ReaderMenu},
    data() {
        return {
            show: false,
            page: 1,
            newEnv: false,
            selectedEnv: {
                new: null,
                old: null
            },
            fileInput: null,
            settings: {
                review: true,
                signatories: [],
                readers: [],
                endDate: '',
                orderRelevant: true
            },
            file: {
                data: null,
                type: null,
                name: null
            }
        };
    },
    methods: {
        close() {
            this.show = false;
            this.page = 1;
            this.fileInput = null;
            this.file.data = null;
            this.file.type = null;
            this.file.name = null;
            this.selectedEnv.old = null;
            this.selectedEnv.new = null;
            this.settings.signatories = [];
            this.settings.readers = [];
            this.settings.endDate = null;
            this.settings.orderRelevant = null;
            this.settings.review = null;
        },
        back() {
            this.selectedEnv.old = null;
            this.selectedEnv.new = null;
        },
        async upload() {
            //TODO: Convert readers into signatories and concat arrays
            //TODO: Error-handling
            this.file.name = this.fileInput.name.split('.')[0];
            this.file.type = this.fileInput.name.split('.')[1];
            this.file.data = await this.asyncHandleFunction(this.fileInput);
            this.settings.endDate = this.settings.endDate + ' 12:00';
            if (!(this.selectedEnv.old === null)) {
                await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.selectedEnv.old, "file":this.file, "settings": this.settings});
                this.close();
            } else if (!(this.selectedEnv.new === null)) {
                await this.$store.dispatch('documentUpload/createEnvelope', {"name": this.selectedEnv.new})
                await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.getCreatedEnvelope.id, "file":this.file, "settings": this.settings})
                this.close();
            } else {
                //TODO: ERROR
            }

            // TODO the env appears before the document is uploaded (reload necessary)
            //await this.$store.dispatch('envelopes/fetchEnvelopes')
        },
        convertReaders() {
            //TODO
        },
        // convert the file into an base64 string
        async asyncHandleFunction(file) {
            return await convertUploadFileToBase64(file)
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
