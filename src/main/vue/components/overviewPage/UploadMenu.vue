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
                                    <!-- Page 1 Choose Document -->
                                    <div v-if="page === 1">
                                        <b-alert :show="this.errors.noDocument">
                                            {{$t('UploadDoc.error.noDocument')}}
                                        </b-alert>

                                        <b-form-file
                                            v-model="fileInput"
                                            :state="Boolean(fileInput)"
                                            v-bind:placeholder="$t('UploadDoc.chooseFile')"
                                            drop-placeholder="Drop file here..."
                                        ></b-form-file>
                                    </div>

                                    <!-- Page 2 New or old envelope? -->
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
                                    <!-- Page 3 select envelope/create envelope-->
                                    <div v-if="page === 3">
                                        <!-- Choose from envelopes -->
                                        <b-alert :show="this.errors.noEnvelope">
                                            {{$t('UploadDoc.error.noEnvelope')}}
                                        </b-alert>

                                        <div v-if="newEnv === false">
                                            <div class="form-group">
                                                <label for="selectEnvelope"> {{$t('UploadDoc.selectEnv')}} </label>
                                                <select class="form-control" id="selectEnvelope" v-model="selectedEnv.old">
                                                    <option v-for="envelope in this.envelopes" :key="envelope.id" :value="envelope.id"> {{envelope.name}} </option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- Create new envelope -->
                                        <b-alert :show="this.errors.noNewName">
                                            {{$t('UploadDoc.error.noNewName')}}
                                        </b-alert>

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
                                    <!-- Page 4 Review process?-->
                                    <div v-if="page === 4">
                                        <button type="button" class="light-btn" @click="review = true; settings.signatories = []; settings.endDate = null; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.addReaders')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="review = false; readers = []; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.skipReview')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 5 Add signatories/readers-->
                                    <div v-if="page === 5">
                                        <!-- Pick Deadline -->
                                        <b-alert :show="this.errors.noEndDate">
                                            {{$t('UploadDoc.error.noEndDate')}}
                                        </b-alert>
                                        <div>
                                            <label for="endDate">{{$t('Settings.DocumentSettings.chooseDate')}}</label>
                                            <b-form-datepicker id="endDate" v-model="settings.endDate" class="mb-2"></b-form-datepicker>
                                        </div>

                                        <!-- Add readers -->
                                        <div v-if="review" style="margin-bottom: 1em">
                                            <b-alert :show="this.errors.noReaders">
                                                {{$t('UploadDoc.error.noReaders')}}
                                            </b-alert>
                                            <h6>{{$t('Settings.DocumentSettings.addReader')}}</h6>
                                            <ReaderMenu :readers="readers"></ReaderMenu>

                                            <b-row align-h="center" v-if="!reviewAddSignatory">
                                                <button class="light-btn" @click="reviewAddSignatory = true">
                                                    <h5>
                                                        <b-icon icon="plus-circle"></b-icon>
                                                        {{$t('Settings.DocumentSettings.addSignatory')}}
                                                    </h5>
                                                </button>
                                            </b-row>
                                        </div>

                                        <!-- Add signatories -->
                                        <div v-if="!review || reviewAddSignatory">
                                            <b-alert :show="this.errors.noSignatories">
                                                {{$t('UploadDoc.error.noSignatories')}}
                                            </b-alert>
                                            <b-alert :show="this.errors.noSignatureType">
                                                {{$t('UploadDoc.error.noSignatureType')}}
                                            </b-alert>

                                            <h6>{{$t('Settings.DocumentSettings.addSignatory')}}</h6>
                                            <SignatoryMenu :inModal="true" :signatories="signatories" :orderRelevant="settings.orderRelevant"></SignatoryMenu>
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

                                                        <button type="button" class="elsa-blue-btn" @click="validate()">
                                                            <h5>
                                                                {{$t('UploadDoc.continue')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 2 -->
                                                    <div v-if="page === 2">
                                                        <button type="button" class="light-btn" @click="back()">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                    </div>

                                                    <!-- Page 3 -->
                                                    <div v-if="page === 3">
                                                        <button type="button" class="light-btn" @click="back()">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="validate()">
                                                            <h5>
                                                                {{$t('UploadDoc.continue')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 4 -->
                                                    <div v-if="page === 4">
                                                        <button type="button" class="light-btn" @click="back()">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                    </div>
                                                    <!-- Page 5 -->
                                                    <div v-if="page === 5">
                                                        <button type="button" class="light-btn" @click="back(); reviewAddSignatory = false">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="validate()">
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
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
//import {mapActions} from "vuex";
import {convertUploadFileToBase64} from "@/main/vue/api/fileToBase64Converter";
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
            review: true,
            reviewAddSignatory: false,
            signatories: [],
            readers: [],
            selectedEnv: {
                new: null,
                old: null
            },
            fileInput: null,
            settings: {
                signatories: [],
                endDate: null,
                orderRelevant: true,
                remind: false,
                reminderTiming: null //TODO
            },
            file: {
                data: null,
                type: null,
                title: null
            },
            errors: {
                noDocument: false,
                noEnvelope: false,
                noNewName: false,
                noReaders: false,
                noSignatories: false,
                noEndDate: false,
                noSignatureType: false
            }
        };
    },
    methods: {
        close() {
            this.show = false;
            this.page = 1;
            this.review = null;
            this.fileInput = null;
            this.reviewAddSignatory = false;
            this.signatories = [];
            this.readers = [];
            this.file = {data: null, type: null, title: null}
            this.selectedEnv = {old: null, new: null}
            this.settings = {signatories: [], endDate: null, orderRelevant: null}
            this.errors = {noEnvelope: null, noNewName: null, noDocument: null};
        },
        back() {
            this.errors = {noEnvelope: null, noNewName: null, noDocument: null};
            this.page = this.page-1;
        },
        validate() {
            // Check if Document was chosen
            if (this.page === 1) {
               if (this.fileInput === null) {
                   this.errors.noDocument = true;
               } else {
                   this.errors.noDocument = false;
                   this.page = this.page+1;
               }
            } else if (this.page === 3) {
                // Check if name for new envelope was chosen
                if (this.newEnv) {
                    if (this.selectedEnv.new === null || this.selectedEnv.new === "") {
                        this.errors.noNewName = true;
                    } else {
                        this.errors.noNewName = false;
                        this.page = this.page+1;
                    }
                    // Check if old envelope was chosen
                } else {
                    if (this.selectedEnv.old === null) {
                        this.errors.noEnvelope = true;
                    } else {
                        this.errors.noEnvelope = false;
                        this.page = this.page+1;
                    }
                }
            } else if (this.page === 5) {
                // checks endDate
                this.errors.noEndDate = this.settings.endDate === null;
                if (this.review) {
                    // checks for readers
                    this.errors.noReaders = this.readers.length === 0;
                    // check fpr signatories
                    if (this.signatories.length > 0) {
                        let i;
                        for(i = 0; i < this.signatories.length; i++) {
                            if (this.signatories[i].signatureType === "") {
                                this.errors.noSignatureType= true;
                            }
                        }
                    }
                    if (!this.errors.noReaders && !this.errors.noEndDate && !this.errors.noSignatureType) {
                        this.upload();
                    }
                } else {
                    // checks for signatories
                    this.errors.noSignatories = this.signatories.length === 0;
                    // checks signatories for signature types
                    this.errors.noSignatureType = false;
                    let i;
                    for(i = 0; i < this.signatories.length; i++) {
                        if (this.signatories[i].signatureType === "") {
                            this.errors.noSignatureType= true;
                        }
                    }
                    if (!this.errors.noSignatureType && !this.errors.noSignatories && !this.errors.noEndDate ) {
                        this.upload();
                    }
                }
            }
        },
        async upload() {
            //TODO: Convert readers into signatories and concat arrays
            //TODO: Error-handling
            await this.fillFile()
            this.fillSettings()
            if (!(this.selectedEnv.old === null)) {
                await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.selectedEnv.old, "file":this.file, "settings": this.settings});
                this.close();
            } else {
                await this.$store.dispatch('documentUpload/createEnvelope', {"name": this.selectedEnv.new})
                await this.$store.dispatch('documentUpload/uploadDocument', {"envID": this.getCreatedEnvelope.id, "file":this.file, "settings": this.settings})
                this.close();
            }
        },
        fillSettings() {
            this.settings.endDate = this.settings.endDate + ' 12:00';
            let i;
            for (i = 0; i < this.readers.length; i++) {
                this.settings.signatories.push({email: this.readers[i], type: 0})
            }
            for (i = 0; i < this.signatories.length; i++) {
                this.settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].signatureType})
            }

        },
        async fillFile() {
            this.file.title = this.fileInput.name.split('.')[0];
            this.file.type = this.fileInput.name.split('.')[1];
            this.file.data = await this.asyncHandleFunction(this.fileInput);
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

.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}
</style>
