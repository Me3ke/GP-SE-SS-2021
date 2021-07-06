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
                <b-row>
                    <b-col cols="2">
                        <b-row v-for="document in this.envelope(envId).documents" :key="document.id">
                            <button :class="{inactive: !(selectedId === document.id), active: selectedId === document.id}" @click="selectedId = document.id">
                                <h5>
                                    <b-row style="padding: 0.5em 1em 0;">
                                        <b-icon icon="file-earmark-text" style="margin-right: 0.5em; fill:var(--elsa-blue)"></b-icon>
                                        <div class="media-body">
                                            {{document.title}}
                                        </div>
                                    </b-row>
                                </h5>
                            </button>
                        </b-row>
                    </b-col>
                    <b-col cols="10">
                        <div v-if="selectedId === -1">
                            {{$t('Settings.EnvelopeSettings.noDocumentSelected')}}
                        </div>
                        <div v-if="!(selectedId === -1)">
                            <SettingsMenu
                                          :document="getDocument(selectedId)"
                                          :signatories="getSignatories(getSettings(selectedId))"
                                          :readers="getReaders(getSettings(selectedId))"
                                          :endDate="getSettings(selectedId).endDate"
                                          :orderRelevant="getSettings(selectedId).orderRelevant" :envId="envId">
                            </SettingsMenu>
                        </div>
                    </b-col>
                </b-row>

            </div>

           <!-- Global settings -->
            <div v-if="(sameSettings(this.envelopeSettings) && editAll === null) || editAll === true">
                <SettingsMenu style="margin-top:3vh"
                              :document="getDocument(selectedId)"
                              :signatories="getSignatories(getSettings(selectedId))"
                              :readers="getReaders(getSettings(selectedId))"
                              :endDate="getSettings(selectedId).endDate"
                              :orderRelevant="getSettings(selectedId).orderRelevant" :envId="envId">
                </SettingsMenu>
            </div>

            </b-container>

        <div style="height:5vh"></div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import {mapGetters} from "vuex";
import SettingsMenu from "@/main/vue/components/envelopeSettings/SettingsMenu";

export default {
    name: "envSettingsPage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, SettingsMenu},
    data() {
        return {
            editAllInput: null,
            editAll: null,
            selectedId: -1,
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
                this.selectedId = initial.documentID
            }
            return true;
        },
        getReaders(settings) {
            let readers = [];
            let i;
            for(i = 0; i < settings.signatories.length; i++) {
                let signatory = settings.signatories[i]
                if(signatory.signatureType === 0) {
                    readers.push(signatory);
                }
            }
            return readers;
        },
        getSignatories(settings) {
            let signatories = [];
            let i;
            for(i = 0; i < settings.signatories.length; i++) {
                let signatory = settings.signatories[i]
                if(!(signatory.signatureType === 0)) {
                    signatories.push(signatory);
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
        getDocument(docId) {
            let i;
            for(i = 0; i < this.envelope(this.envId).documents.length; i++) {
                if (this.envelope(this.envId).documents[i].id === docId) {
                    return this.envelope(this.envId).documents[i];
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

.active {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.13vw solid var(--dark-grey);
    border-radius: 0.33vw;
    box-shadow: 0 0 0 0.05em var(--elsa-blue);
}

.inactive {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.13vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.inactive:hover {
    background-color: var(--dark-grey);
    color: var(--light-grey);
    transition-duration: 0.4s;
}

</style>
