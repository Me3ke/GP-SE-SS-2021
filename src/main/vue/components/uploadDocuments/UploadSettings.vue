<template>
<div>
    <div class="modal-body">
        <!-- Pick Deadline -->
        <b-alert :show="this.error.noEndDate">
            {{$t('UploadDoc.error.noEndDate')}}
        </b-alert>
        <div>
            <label for="endDate">{{$t('Settings.DocumentSettings.chooseDate')}}</label>
            <b-row>
                <b-col cols="6">
                    <b-form-datepicker class="mb-2" id="endDate" v-model="endDate"></b-form-datepicker>
                </b-col>
                <b-col cols="6">
                    <b-form-timepicker v-model="endTime" :locale="this.$i18n.locale"></b-form-timepicker>
                </b-col>
            </b-row>
        </div>

        <!-- Add Readers and Signatories -->
        <b-alert :show="this.error.noSignatories">
            {{$t('UploadDoc.error.noReadSig')}}
        </b-alert>

        <!-- Add readers -->
        <h6>{{$t('Settings.DocumentSettings.addReader')}}</h6>
        <ReaderMenu :readers="readers"></ReaderMenu>

        <!-- Add signatories -->
        <b-alert :show="this.error.noSignatureType">
            {{$t('UploadDoc.error.noSignatureType')}}
        </b-alert>

        <h6>{{$t('Settings.DocumentSettings.addSignatory')}}</h6>
        <SignatoryMenu :inModal="true" :signatories="signatories" :orderRelevant="settings.orderRelevant"></SignatoryMenu>

    </div>
    <div class="modal-footer">
        <b-container fluid>
            <b-row align-h="end">
                <b-col cols="auto">
                    <button type="button" class="light-btn" @click="back(); reviewAddSignatory = false">
                        <h5>
                            {{$t('UploadDoc.back')}}
                        </h5>
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button type="button" class="elsa-blue-btn" @click="noProcess()">
                        <h5>
                            {{$t('UploadDoc.startProcessLater')}}
                        </h5>
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button type="button" class="elsa-blue-btn" @click="startProcess()">
                        <h5>
                            {{$t('UploadDoc.startProcess')}}
                        </h5>
                    </button>
                </b-col>
            </b-row>
        </b-container>
    </div>
</div>
</template>

<script>
import SignatoryMenu from "@/main/vue/components/uploadDocuments/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/uploadDocuments/ReaderMenu";

export default {
    name: "UploadSettings",
    props: {
        settings: Object
    },
    components: {
        SignatoryMenu,
        ReaderMenu
    },
    data() {
        return {
            endDate: null,
            endTime: null,
            readers: [],
            signatories: [],
            orderRelevant: false,
            error: {
                noSignatories: false,
                noSignatureType: false,
                noEndDate: false,
            }
        }
    },
    methods: {
        back() {
            this.endDate = null;
            this.endTime = null;
            this.readers = [];
            this.signatories = [];
            this.orderRelevant = false;
            this.error = {
                noSignatories: false,
                noSignatureType: false,
                noEndDate: false,
            }
            this.$emit('previousPage')
        },
        noProcess() {
            // set end date
            if(!(this.endTime) && !(this.endDate === null)) {
                let time = this.endTime.split(":")
                this.settings.endDate = this.settings.endDate + ' ' + time[0] + ':' + time[1];
            }
            // set signatories
            let i;
            for (i = 0; i < this.readers.length; i++) {
                this.settings.signatories.push({email: this.readers[i].email, type: 0})
            }
            for (i = 0; i < this.signatories.length; i++) {
                this.settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].signatureType})
            }

            //set order relevant
            this.settings.orderRelevant = this.orderRelevant;

            this.$emit('nextPage')
        },
        startProcess() {
            if(this.validate()) {
                // set end date
                let time = this.endTime.split(":")
                this.settings.endDate = this.settings.endDate + ' ' + time[0] + ':' + time[1];

                // set signatories
                let i;
                for (i = 0; i < this.readers.length; i++) {
                    this.settings.signatories.push({email: this.readers[i].email, type: 0})
                }
                for (i = 0; i < this.signatories.length; i++) {
                    this.settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].signatureType})
                }

                //set order relevant
                this.settings.orderRelevant = this.orderRelevant;

                this.$emit('nextPage')
            }
        },
        validate() {
            this.error.noEndDate = this.endDate === null || this.endTime === null;
            this.error.noSignatories = this.readers.length === 0 && this.signatories.length === 0;
            this.error.noSignatureType = false;
            let i;
            for(i = 0; i < this.signatories.length; i++) {
                if(!(this.signatories.type === 1 || this.signatories.type === 2)) {
                    this.error.noSignatureType = true;
                }
            }
            return !this.error.noEndDate && !this.error.noSignatureType && !this.error.noSignatories;
        }
    }
}
</script>

<style scoped>

</style>
