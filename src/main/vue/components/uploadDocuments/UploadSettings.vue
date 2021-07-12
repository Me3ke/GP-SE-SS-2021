<template>
<div>
    <div class="modal-body">
        <!-- Pick Deadline -->
        <b-alert :show="this.error.noEndDate">
            {{$t('UploadDoc.error.noEndDate')}}
        </b-alert>
        <div>
            <h6>{{$t('Settings.DocumentSettings.chooseDate')}}</h6>
            <b-row style="margin-bottom: 0.5em">
                <b-col cols="6">
                    {{endDate}}
                    <b-form-datepicker class="mb-2" v-model="endDate"></b-form-datepicker>
                </b-col>
                <b-col cols="6">
                    {{endTime}}
                    <b-form-timepicker v-model="endTime" :locale="this.$i18n.locale"></b-form-timepicker>
                </b-col>
            </b-row>
        </div>

        <!-- Add Readers and Signatories -->
        <b-alert :show="this.error.noSignatories">
            {{$t('UploadDoc.error.noReadSig')}}
        </b-alert>

        <!-- Add readers -->
        <h6>{{$t('Settings.DocumentSettings.reader')}}</h6>
        <ReaderMenu :readers="readers" @updateReaders="updateReaders"></ReaderMenu>

        <!-- Add signatories -->
        <b-alert :show="this.error.noSignatureType">
            {{$t('UploadDoc.error.noSignatureType')}}
        </b-alert>

        <h6>{{$t('Settings.DocumentSettings.signatory')}}</h6>
        <SignatoryMenu :signatories="signatories" @updateSignatories="updateSignatories" @updateOrderRelevant="updateOrderRelevant"></SignatoryMenu>

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
    components: {
        SignatoryMenu,
        ReaderMenu
    },
    props: ['alreadySetSettings'],
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
            },

            settingsCopy: {}

        }
    },
    methods: {
        updateReaders(readers) {
            this.readers = readers;
        },
        updateSignatories(signatories) {
            this.signatories = signatories;
        },
        updateOrderRelevant(orderRelevant) {
            this.orderRelevant = orderRelevant;
        },
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
            let settings = {endDate: "", orderRelevant: false, signatories: []};

            // set end date
            if(!(this.endTime) && !(this.endDate === null)) {
                let time = this.endTime.split(":")
                settings.endDate = this.settings.endDate + ' ' + time[0] + ':' + time[1];
            }
            // set signatories
            let i;
            for (i = 0; i < this.readers.length; i++) {
                settings.signatories.push({email: this.readers[i].email, type: 0})
            }
            for (i = 0; i < this.signatories.length; i++) {
                settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].signatureType})
            }

            //set order relevant
            settings.orderRelevant = this.orderRelevant;

            this.$emit('updateSettings', settings)
            this.$emit('nextPage')
        },
        startProcess() {
            if(this.validate()) {
                let settings = {endDate: "", orderRelevant: false, signatories: []};

                // set end date
                let time = this.endTime.split(":")
                settings.endDate = this.endDate + ' ' + time[0] + ':' + time[1];

                // set signatories
                let i;
                for (i = 0; i < this.readers.length; i++) {
                    settings.signatories.push({email: this.readers[i].email, type: 0})
                }
                for (i = 0; i < this.signatories.length; i++) {
                    settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].type})
                }

                //set order relevant
                settings.orderRelevant = this.orderRelevant;

                this.$emit('updateSettings', settings)
                this.$emit('nextPage')
            }
        },
        validate() {
            this.error.noEndDate = this.endDate === null || this.endTime === null;
            this.error.noSignatories = this.readers.length === 0 && this.signatories.length === 0;
            this.error.noSignatureType = false;
            let i;
            for(i = 0; i < this.signatories.length; i++) {
                if(!(this.signatories[i].type === 1 || this.signatories[i].type === 2)) {
                    this.error.noSignatureType = true;
                }
            }
            return !this.error.noEndDate && !this.error.noSignatureType && !this.error.noSignatories;
        }
    },

    created() {
        if(this.alreadySetSettings !== undefined) {
            this.settingsCopy = Object.assign({}, this.alreadySetSettings)
        }

        if(this.alreadySetSettings !== undefined) {
            this.endDate = this.settingsCopy.endDate
            this.orderRelevant = this.settingsCopy.orderRelevant
            this.signatories = this.settingsCopy.signatories


            if(this.endDate.includes(':')) {
                const [date, time] = this.endDate.split(' ')

                if(date.includes('-')) {
                    this.endDate = date
                } else {
                    const [year, month, day] = date.split('.')
                    this.endDate = year + '-' + month + '-' + day
                }

                const [hours, seconds] = time.split(':')
                this.endTime = hours + ':' + seconds

            } else {
                if(!this.endDate.includes('.')) {
                    const [day, month, year] = this.endDate.split('.')
                    this.endDate = year + '-' + month + '-' + day
                }
            }

            if(this.endDate.includes('.')) {
                const [day, month, year] = this.endDate.split('.')
                this.endDate = year + '-' + month + '-' + day
            }

            const [day, month, year] = this.endDate.split('-')
            if(day.length === 4 && year.length > 3) {
                this.endDate = day + '-' + month + '-' + year
            } else if(year === 4 && day.length > 3) {
                this.endDate = year + '-' + month + '-' + day
            }
        }
    },


}
</script>

<style scoped>
.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}

</style>
