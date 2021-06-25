<template>
    <div class="background" style="background-color: var(--whitesmoke);">
        <Header></Header>
        <BaseHeading :name="this.envelope(envId).name" :translate="false" style="position: fixed"></BaseHeading>

        <b-container fluid="xl">
            <!-- Option to edit all documents at ones -->
            <div style="margin-top:15vh">
                <div class="custom-control custom-switch">
                    <input type="checkbox" class="custom-control-input" id="allEditAllSwitch" v-model="editAllInput" @click="changeEditAll()">
                    <label class="custom-control-label" for="allEditAllSwitch"> {{$t('Settings.DocumentSettings.editAll')}} </label>
                </div>
            </div>

            <!-- Individual Settings -->
            <div v-if="(!sameSettings(this.envelopeSettings) && editAll === null) || editAll === false" style="margin-top:3vh">
                <DocumentDropDown style="margin-top:0.5vh"
                    v-for="document in this.envelope(envId).documents" :key="document.id" :document="document"
                    :signatories="getSignatories(getSettings(document.id))"
                    :readers="getReaders(getSettings(document.id))"
                    :endDate="getSettings(document.id).endDate"
                    :orderRelevant="getSettings(document.id).orderRelevant" :envId="envId">
                </DocumentDropDown>
            </div>

           <!-- Global settings -->
            <div v-if="(sameSettings(this.envelopeSettings) && editAll === null) || editAll === true">
                <!-- End Date -->
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.endDate')}}
                    </div>
                    <div>
                        <b-list-group-item v-if="!this.editDate">{{this.envelopeSettings[this.selectedIndex].endDate}}</b-list-group-item>

                        <b-row align-h="end" v-if="!this.editDate">
                            <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                                <b-icon icon="pencil-fill"></b-icon>
                                {{$t('Settings.DocumentSettings.edit')}}
                            </button>
                        </b-row>

                        <b-form-datepicker class="mb-2" v-if="this.editDate"></b-form-datepicker>

                        <b-row align-h="end" v-if="this.editDate">
                            <button style="width:8em; margin-right:0.5em; margin-bottom: 0.5em" class="light-btn" @click="editDate = false"> {{$t('DownloadDoc.cancel')}} </button>
                            <button style="width:8em; margin-right:1.5em; margin-bottom: 0.5em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                        </b-row>
                    </div>
                </div>

                <!-- Reader -->
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.reader')}}
                    </div>
                    <div v-if="!this.editReaders">
                        <SignatoryListItem v-for="reader in getReaders(this.envelopeSettings[this.selectedIndex])" :key="reader.email" :signatory="reader"></SignatoryListItem>
                    </div>

                    <b-list-group-item v-if="getReaders(this.envelopeSettings[this.selectedIndex]).length === 0 && !this.editReaders">
                        {{$t('Settings.DocumentSettings.noReaders')}}
                    </b-list-group-item>

                    <b-row align-h="end" v-if="!this.editReaders">
                        <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editReaders = true; initReaders()">
                            <b-icon icon="pencil-fill"></b-icon>
                            {{$t('Settings.DocumentSettings.edit')}}
                        </button>
                    </b-row>

                    <div style="padding:2em" v-if="this.editReaders">
                        <ReaderMenu :readers="getReaders(this.envelopeSettings[this.selectedIndex])"></ReaderMenu>
                    </div>

                    <b-row align-h="end" v-if="this.editReaders">
                        <button style="width:8em; margin:1em" class="light-btn" @click="editReaders = false"> {{$t('DownloadDoc.cancel')}} </button>
                        <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                    </b-row>
                </div>

                <!-- Signatories -->
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.signatory')}}
                    </div>

                    <div v-if="!this.editSignatories">
                        <SignatoryListItem v-for="signatory in getSignatories(this.envelopeSettings[this.selectedIndex])" :key="signatory.email" :signatory="signatory"></SignatoryListItem>
                    </div>

                    <b-list-group-item v-if="getSignatories(this.envelopeSettings[this.selectedIndex]).length === 0 && !this.editSignatories">
                        {{$t('Settings.DocumentSettings.noSignatories')}}
                    </b-list-group-item>

                    <b-row align-h="end" v-if="!this.editSignatories">
                        <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editSignatories = true; initSignatories()">
                                <b-icon icon="pencil-fill"></b-icon>
                                {{$t('Settings.DocumentSettings.edit')}}
                        </button>
                    </b-row>


                    <div style="padding:2em" v-if="this.editSignatories">
                        <SignatoryMenu :inModal="false" :order-relevant="this.envelopeSettings[this.selectedIndex].orderRelevant" :signatories="getSignatories(this.envelopeSettings[this.selectedIndex])"></SignatoryMenu>
                    </div>

                    <b-row align-h="end" v-if="this.editSignatories">
                        <button style="width:8em; margin:1em" class="light-btn" @click="editSignatories = false"> {{$t('DownloadDoc.cancel')}} </button>
                        <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                    </b-row>
                </div>
            </div>
            </b-container>

        <div style="height:5vh"></div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
import {mapGetters} from "vuex";
import DocumentDropDown from "@/main/vue/components/envelopeSettings/DocumentDropDown";
import SignatoryListItem from "@/main/vue/components/SignatoryListItem";

export default {
    name: "envSettingsPage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, SignatoryMenu, ReaderMenu, DocumentDropDown, SignatoryListItem},
    data() {
        return {
            editAllInput: null,
            editAll: null,
            selectedIndex: 0,
            editSignatories: false,
            editReaders: false,
            editDate: false,
            settingsEdited:
            {
                signatories: [],
                readers: [],
                endDate: "",
                orderRelevant: null
            }
        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
        this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: this.envId})
    },
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope',
            envelopeSettings: 'documentSettings/getEnvelopeSettings'
        })
    },
    methods: {
        sameSettings(settings) {
            let initial = settings[0];
            let i;
            for(i = 1; i < settings.length; i++) {
                if(!(initial.signatories === settings[i].signatories)) {
                    if(this.editAllInput === null) {
                        this.editAllInput = false
                    }
                    return false;
                }
                if(!(initial.orderRelevant === settings[i].orderRelevant)) {
                    if(this.editAllInput === null) {
                        this.editAllInput = false
                    }
                    return false;
                }
                if(!(initial.endDate === settings[i].endDate)) {
                    if(this.editAllInput === null) {
                        this.editAllInput = false
                    }
                    return false;
                }
            }
            if(this.editAllInput === null) {
                this.editAllInput = true
            }
            return true;
        },
        getReaders(settings) {
            let readers = [];
            let i;
            for(i = 0; i < settings.signatories; i++) {
                if(settings.signatories[i].signatureType === "REVIEW") {
                    readers.push({email: settings.signatories[i].email, signatureType: 0, reminderTiming: settings.signatories[i].reminderTiming});
                } else {
                    return readers;
                }
            }
            return readers;
        },
        getSignatories(settings) {
            let signatories = [];
            let i;
            for(i = 0; i < settings.signatories.length; i++) {
                if(!(settings.signatories[i].signatureType === "REVIEW")) {
                    if(settings.signatories[i].signatureType === "SIMPLE_SIGNATURE") {
                        signatories.push({email: settings.signatories[i].email, signatureType: 1, reminderTiming: settings.signatories[i].reminderTiming});
                    } else {
                        signatories.push({email: settings.signatories[i].email, signatureType: 2, reminderTiming: settings.signatories[i].reminderTiming});
                    }
                }
            }
            return signatories;
        },
        getSettings(docId) {
            let i;
            for(i = 0; i < this.envelopeSettings.length; i++) {
                if (this.envelopeSettings[i].documentID === docId) {
                    return this.envelopeSettings[i];
                }
            }
        },
        changeEditAll() {
            if(this.editAll === null) {
                // TODO: Warning
                this.editAll = !this.sameSettings(this.envelopeSettings)
            } else {
                this.editAll = !this.editAll
            }
        },
        initSignatories() {
            let signatories = this.getSignatories(this.envelopeSettings[this.selectedIndex])
            this.settingsEdited.signatories = [];
            let i
            for(i = 0; i < signatories.length; i++) {
                this.settingsEdited.signatories.push(signatories[i])
            }
            this.remind = signatories[0].remind
            this.reminderTiming = signatories[0].reminderTiming
        },
        initReaders() {
            let readers = this.getReaders(this.envelopeSettings[this.selectedIndex])
            this.settingsEdited.readers = [];
            let i
            for(i = 0; i < readers.length; i++) {
                this.settingsEdited.readers.push(readers[i])
            }
        },
        async saveSettings() {
            let oldSettings = this.envelopeSettings[this.selectedIndex]
            let newSettings = {signatories: null, orderRelevant: null, endDate: null}
            if (this.editDate) {
                newSettings.endDate = this.settingsEdited.endDate + ' 12:00'
            } else {
                newSettings.endDate = oldSettings.endDate + ' 12:00'
            }
            if (this.editReaders && this.editSignatories) {
                newSettings.orderRelevant = this.settingsEdited.orderRelevant
                let newSignatories;
                newSignatories = this.remindInSignatories(this.settingsEdited.signatories);
                newSettings.signatories = this.makeSignatories(this.settingsEdited.readers, newSignatories)
            } else if (this.editReaders) {
                newSettings.orderRelevant = oldSettings.orderRelevant
                newSettings.signatories = this.makeSignatories(this.settingsEdited.readers, oldSettings.signatories)
            } else if (this.editSignatories) {
                newSettings.orderRelevant = this.settingsEdited.orderRelevant
                let newSignatories;
                newSignatories = this.remindInSignatories(this.settingsEdited.signatories);
                newSettings.signatories = this.makeSignatories(oldSettings.readers, newSignatories)
            } else {
                newSettings.orderRelevant = oldSettings.orderRelevant
                newSettings.signatories = this.makeSignatories(oldSettings.readers, oldSettings.signatories)
            }
            let i;
            for(i = 0; i < this.envelope.documents.length; i++) {
                await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": this.envelope.documents.id, "envId": this.envId, "settings": newSettings})
            }
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
                newSignatories.push({email: readers[i], signatureType: 0, remind: false, reminderTiming: -1, status: newStatus.status, signedOn: newStatus.signedOn})
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
                        return {status: true, signedOn: this.signatories[i].signedOn}
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
                    return {status: true, signedOn: this.readers[i].signedOn}
                }
            }
            return {status: false, signedOn: ""}
        }
    }
}
</script>

<style scoped>
.background {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-image: linear-gradient(to bottom, var(--background-fade-one) 0%, var(--background-fade-two) 30%, var(--background-fade-three) 100%), url(../assets/background.png);
    background-repeat: no-repeat;
    background-size: 100% auto;
}

.list-group-item {
    height: 2.5em;
    padding: 0.5em;
}
</style>
