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
                                            v-model="file"
                                            :state="Boolean(file)"
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
                                                    <option v-for="envelope in this.envelopes({state: null})" :key="envelope.id" :value="envelope.id"> {{envelope.name}} </option>
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
                                        {{$t('UploadDoc.configureDoc')}}
                                    </div>
                                </div>
                                <!-- Buttons -->
                                <div class="modal-footer">
                                    <b-container fluid>
                                        <b-row align-h="between">
                                            <b-col>
                                                <div class="mt-3"> {{$t('UploadDoc.selectedDoc')}} {{ file ? file.name : '' }}</div>
                                            </b-col>
                                            <b-col>
                                                <b-row>
                                                    <!-- Page 1 -->
                                                    <div v-if="page === 1">
                                                        <button type="button" class="light-btn" @click="show = false; close()">
                                                            <h5>
                                                                {{$t('UploadDoc.close')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="grey-btn" v-if="file===null">
                                                            <h5>
                                                                {{$t('UploadDoc.continue')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1;" v-if="!(file===null)">
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
                                                        <button type="button" class="light-btn" @click="page = page - 1;">
                                                            <h5>
                                                                {{$t('UploadDoc.back')}}
                                                            </h5>
                                                        </button>
                                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1; validateEnvelope();">
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
import {mapActions, mapGetters} from "vuex";

export default {
    name: 'UploadButton',
    props: {
        text: String
    },
    data() {
        return {
            show: false,
            page: 1,
            newEnv: false,
            selectedEnv: {
                new: null,
                old: null
            },
            file: null,
            settings : {
                title: null,
                type: null,
                signatories: null,
                readers: null,
                signatureType: null,
                endDate: null,
                orderRelevant: null,
                state: null
            }
        };
    },
    methods: {
        close() {
            this.page = 1;
            this.file = null;
            this.selectedEnv.old = null;
            this.selectedEnv.new = null;
            this.settings.title = null;
            this.settings.type = null;
            this.settings.signatories = null;
            this.settings.readers = null;
            this.settings.signatureType = null;
            this.settings.endDate = null;
            this.settings.orderRelevant = null;
            this.settings.state= null;
        },
        back() {
            this.selectedEnv.old = null;
            this.selectedEnv.new = null;
        },
        validateEnvelope() {
            if(this.selectedEnv.old === null && this.selectedEnv.new === null) {
                //TODO: ERROR
            }
        },
        upload() {
            if(!(this.selectedEnv.old === null)) {
                //TODO: Put document
            } else if(!(this.selectedEnv.new === null)) {
                //TODO: Post new Envelope then put document
            } else {
                //TODO: ERROR
            }
            close();
        },
        ...mapActions({uploadDocument: 'documentUpload/uploadDocument'})
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelopes: 'envelopes/getEnvelopes'
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
