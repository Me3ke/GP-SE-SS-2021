<template>
    <div class="card">
        <button class="drop-down-box" @click="show = !show">
            <div class="media">
                <b-icon icon="file-earmark-text" class="iconBlue" style="margin:0.5em"></b-icon>
                <div class="media-body">
                    <b-container fluid style="text-align: left; margin: 0.3em">
                        <b-row align-h="between">
                            <b-col cols="11">
                                <h4>
                                    {{this.document.title}}
                                </h4>
                            </b-col>
                            <b-col cols="1">
                                <b-icon v-if="!show" icon="caret-down-fill" class="iconDark" style="margin:0.5em"></b-icon>
                                <b-icon v-if="show" icon="caret-up-fill" class="iconDark" style="margin:0.5em"></b-icon>
                            </b-col>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </button>

        <b-container v-if="show">
            <!-- End Date -->
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.endDate')}}
                </div>
                <div>
                    <b-list-group-item v-if="!this.editDate">{{ this.endDate }}</b-list-group-item>

                    <b-row align-h="end" v-if="!this.editDate">
                        <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                            <b-icon icon="pencil-fill"></b-icon>
                            {{$t('Settings.DocumentSettings.edit')}}
                        </button>
                    </b-row>

                    <b-form-datepicker class="mb-2" v-if="this.editDate" v-model="settingsEdited.endDate"></b-form-datepicker>

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
                    <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editSignatories = true; initSignatories">
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

            <b-row align-h="end" v-if="this.editSignatories">
                <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveSettings()"> {{$t('Settings.DocumentSettings.saveAll')}} </button>
            </b-row>

        </b-container>
    </div>
</template>

<script>
import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";
import SignatoryListItem from "@/main/vue/components/SignatoryListItem";

export default {
    name: "documentDropDown",
    props: {
        envId: Number,
        document: Object,
        readers: Array,
        signatories: Array,
        endDate: String,
        orderRelevant: Boolean,
    },
    components: {
        ReaderMenu,
        SignatoryMenu,
        SignatoryListItem
    },
    data() {
        return {
            show: false,
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
                reminderTiming: null
            }
        }
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
                newSettings.endDate = this.settingsEdited.endDate + ' 12:00'
            } else {
                newSettings.endDate = this.endDate + ' 12:00'
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
            console.log(this.readers)
            console.log(this.signatories)
            console.log(newSettings)
            await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": this.document.id, "envId": this.envId, "settings": newSettings})
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
