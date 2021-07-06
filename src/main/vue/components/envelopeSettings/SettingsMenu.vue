<template>
    <div>
        <!-- End Date -->
        <div class="card">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.endDate')}}
            </div>
            <div>
                <b-list-group-item v-if="!this.editDate">{{this.endDate}}</b-list-group-item>
                <b-row align-h="end" v-if="!this.editDate">
                    <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                        <b-icon icon="pencil-fill"></b-icon>
                        {{$t('Settings.DocumentSettings.edit')}}
                    </button>
                </b-row>
                <b-row v-if="this.editDate">
                    <b-col cols="6">
                        <b-form-datepicker class="mb-2" v-model="settingsEdited.endDate"></b-form-datepicker>
                    </b-col>
                    <b-col cols="6">
                        <b-form-timepicker v-model="settingsEdited.endTime" locale="en"></b-form-timepicker>
                    </b-col>
                </b-row>
                <b-row align-h="end" v-if="this.editDate">
                    <button style="width:8em; margin-right:0.5em; margin-bottom: 0.5em" class="light-btn" @click="editDate = false"> {{$t('DownloadDoc.cancel')}} </button>
                    <button style="width:8em; margin-right:1.5em; margin-bottom: 0.5em" class="elsa-blue-btn" @click="saveSettings()"> {{$t('Settings.DocumentSettings.save')}} </button>
                </b-row>
            </div>
        </div>

        <!-- Reader -->
        <div class="card" style="margin-top:3vh">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.reader')}}
            </div>
            <div v-if="!this.editReaders">
                <SignatoryListItem v-for="reader in readers" :key="reader.email" :signatory="reader"></SignatoryListItem>
            </div>

            <b-list-group-item v-if="readers.length === 0 && !this.editReaders">
                {{$t('Settings.DocumentSettings.noReaders')}}
            </b-list-group-item>

            <b-row align-h="end" v-if="!this.editReaders">
                <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editReaders = true; initReaders()">
                    <b-icon icon="pencil-fill"></b-icon>
                    {{$t('Settings.DocumentSettings.edit')}}
                </button>
            </b-row>

            <div style="padding:2em" v-if="this.editReaders">
                <ReaderMenu :readers="settingsEdited.readers"></ReaderMenu>
            </div>

            <b-row align-h="end" v-if="this.editReaders">
                <button style="width:8em; margin:1em" class="light-btn" @click="editReaders = false"> {{$t('DownloadDoc.cancel')}} </button>
                <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveSettings()"> {{$t('Settings.DocumentSettings.save')}} </button>
            </b-row>
        </div>

        <!-- Signatories -->
        <div class="card" style="margin-top:3vh">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.signatory')}}
            </div>

            <div v-if="!this.editSignatories">
                <SignatoryListItem v-for="signatory in signatories" :key="signatory.email" :signatory="signatory"></SignatoryListItem>
            </div>

            <b-list-group-item v-if="signatories.length === 0 && !this.editSignatories">
                {{$t('Settings.DocumentSettings.noSignatories')}}
            </b-list-group-item>

            <b-row align-h="end" v-if="!this.editSignatories">
                <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editSignatories = true; initSignatories()">
                    <b-icon icon="pencil-fill"></b-icon>
                    {{$t('Settings.DocumentSettings.edit')}}
                </button>
            </b-row>

            <div style="padding:2em" v-if="this.editSignatories">
                <SignatoryMenu
                    :inModal="false"
                    :order-relevant="orderRelevant"
                    :signatories="settingsEdited.signatories"
                    :remind="this.settingsEdited.remind"
                    :reminderTiming="this.settingsEdited.reminderTiming"></SignatoryMenu>
            </div>

            <b-row align-h="end" v-if="this.editSignatories">
                <button style="width:8em; margin:1em" class="light-btn" @click="editSignatories = false"> {{$t('DownloadDoc.cancel')}} </button>
                <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveSettings()"> {{$t('Settings.DocumentSettings.save')}} </button>
            </b-row>
        </div>

        <b-row align-h="end" v-if="this.editSignatories || this.editDate || this.editReaders">
            <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveSettings()"> {{$t('Settings.DocumentSettings.saveAll')}} </button>
        </b-row>


    </div>
</template>

<script>
import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";
import SignatoryListItem from "@/main/vue/components/SignatoryListItem";
import {mapGetters} from "vuex";

export default {
    name: "settingsMenu",
    props: {
        envId: Number,
        document: Object,
        readers: Array,
        signatories: Array,
        endDate: String,
        orderRelevant: Boolean,
        editAll: Boolean
    },
    components: {
        ReaderMenu,
        SignatoryMenu,
        SignatoryListItem
    },
    data() {
        return {
            editSignatories: false,
            editReaders: false,
            editDate: false,
            settingsEdited:
            {
                signatories: [],
                readers: [],
                endDate: "",
                orderRelevant: null,
                remind: null,
                reminderTiming: null,
                endTime: ""
            }
        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope'
        })
    },
    methods: {
        initSignatories() {
            this.settingsEdited.signatories = [];
            let i
            for(i = 0; i < this.signatories.length; i++) {
                this.settingsEdited.signatories.push(this.signatories[i])
            }
            this.remind = this.signatories[0].remind
            this.reminderTiming = this.signatories[0].reminderTiming
        },
        initReaders() {
            this.settingsEdited.readers = [];
            let i
            for(i = 0; i < this.readers.length; i++) {
                this.settingsEdited.readers.push(this.readers[i])
            }
        },
        async saveSettings() {
            let newSettings = {signatories: null, orderRelevant: null, endDate: null}
            if (this.editDate) {
                let time = this.settingsEdited.endTime.split(":")
                newSettings.endDate = this.settingsEdited.endDate + ' ' + time[0] + ':' + time[1];
            } else {
                newSettings.endDate = this.endDate + ' 12:00' // TODO
            }
            if (this.editReaders && this.editSignatories) {
                newSettings.orderRelevant = this.settingsEdited.orderRelevant
                let newSignatories;
                newSignatories = this.remindInSignatories(this.settingsEdited.signatories);
                newSettings.signatories = this.makeSignatories(this.settingsEdited.readers, newSignatories)
            } else if (this.editReaders) {
                newSettings.orderRelevant = this.orderRelevant
                newSettings.signatories = this.makeSignatories(this.settingsEdited.readers, this.signatories)
            } else if (this.editSignatories) {
                newSettings.orderRelevant = this.settingsEdited.orderRelevant
                let newSignatories;
                newSignatories = this.remindInSignatories(this.settingsEdited.signatories);
                newSettings.signatories = this.makeSignatories(this.readers, newSignatories)
            } else {
                newSettings.orderRelevant = this.orderRelevant
                newSettings.signatories = this.makeSignatories(this.readers, this.signatories)
            }
            if(this.editAll) {
                let envelope = this.envelope(this.envId)
                let i;
                for(i = 0; i < envelope.documents.length; i++){
                    await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": envelope.documents[i].id, "envId": this.envId, "settings": newSettings})
                }
            } else {
                await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": this.document.id, "envId": this.envId, "settings": newSettings})
            }
            this.editSignatories = false;
            this.editReaders = false;
            this.editDate = false;
        },
        remindInSignatories(signatories, remind, reminderTiming) {
            let newSignatories = []
            let i
            for (i = 0; i < signatories.length; i++) {
                newSignatories.push({email: signatories[i].email, signatureType: signatories[i].signatureType, remind: remind, reminderTiming: reminderTiming})
            }
            return newSignatories
        },
        makeSignatories(readers, signatories) {
            let newSignatories = []
            let i;
            for (i = 0; i < readers.length; i++) {
                let newStatus
                newStatus = this.getStatusReader(readers[i])
                newSignatories.push({email: readers[i].email, signatureType: 0, remind: false, reminderTiming: -1, status: newStatus.status, signedOn: newStatus.signedOn})
            }
            for (i = 0; i < signatories.length; i++) {
                let newStatus
                newStatus = this.getStatusSignatory(signatories[i])
                newSignatories.push({email: signatories[i].email, signatureType: signatories[i].signatureType, remind: signatories[i].remind, reminderTiming: signatories[i].reminderTiming, status: newStatus.status, signedOn: newStatus.signedOn})
            }
            return newSignatories
        },
        getStatusSignatory(signatory) {
            let i;
            for (i = 0; i < this.signatories.length; i++) {
                if(this.signatories[i].email === signatory.email) {
                    if(this.signatories[i].signatureType === signatory.signatureType) {
                        return {status: this.signatories[i].status, signedOn: this.signatories[i].signedOn}
                    } else {
                        return {status: false, signedOn: ""}
                    }
                }
            }
            return {status: false, signedOn: ""}
        },
        getStatusReader(reader) {
            let i;
            for (i = 0; i < this.readers.length; i++) {
                if(this.readers[i].email === reader.email) {
                    return {status: this.readers[i].status, signedOn: this.readers[i].signedOn}
                }
            }
            return {status: false, signedOn: ""}
        }
    }
}
</script>

<style scoped>
.drop-down-box {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.1vw;
    width: 100%
}

.drop-down-box:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}

.iconBlue {
    fill: var(--elsa-blue);
    height: 1.5em;
    width: auto;
}

.list-group-item {
    height: 2.5em;
    padding: 0.5em;
}
</style>
