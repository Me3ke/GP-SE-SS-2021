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
                            <button :class="{inactive: !(selectedId === document.id), active: selectedId === document.id}" @click="selectedId = document.id" style=" margin-bottom: 0.5em;">
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
                           <h3 >
                               {{$t('Settings.DocumentSettings.noDocumentSelected')}}
                           </h3>
                        </div>
                        <div v-if="!(selectedId === -1)">
                            <SettingsMenu
                                          :document="getDocument(selectedId)"
                                          :signatories="getSignatories(getSettings(selectedId))"
                                          :readers="getReaders(getSettings(selectedId))"
                                          :endDate="getSettings(selectedId).endDate"
                                          :orderRelevant="getSettings(selectedId).orderRelevant" :envId="envId"
                                          :editAll="false">
                            </SettingsMenu>
                        </div>
                    </b-col>
                </b-row>


                <div v-if="showWarning">
                    <transition>
                        <div class="modal-mask">
                            <div class="modal-wrapper">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                            <h5>
                                                {{$t('Settings.DocumentSettings.editAllWarning')}}
                                            </h5>
                                            <select class="form-control" id="selectEnvelope" v-model="selectedIdInput">
                                                <option v-for="document in this.envelope(this.envId).documents" :key="document.id" :value="document.id"> {{document.title}} </option>
                                            </select>
                                        </div>
                                        <div class="modal-footer">
                                            <b-row align-h="end">
                                                <b-col cols="auto">
                                                    <button class="light-btn" @click="selectedIdInput = null; showWarning = false;">
                                                        {{$t('DownloadDoc.cancel')}}
                                                    </button>
                                                </b-col>
                                                <b-col cols="auto">
                                                    <button class="elsa-blue-btn" @click="confirmSelectedID()">
                                                        {{$t('Settings.DocumentSettings.confirm')}}
                                                    </button>
                                                </b-col>
                                            </b-row>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </transition>
                </div>
            </div>
           <!-- Global settings -->
            <div v-if="(sameSettings(this.envelopeSettings) && editAll === null) || editAll === true">
                <SettingsMenu
                              style="margin-top:3vh"
                              :document="getDocument(selectedId)"
                              :signatories="getSignatories(getSettings(selectedId))"
                              :readers="getReaders(getSettings(selectedId))"
                              :endDate="getSettings(selectedId).endDate"
                              :orderRelevant="getSettings(selectedId).orderRelevant" :envId="envId"
                              :editAll="true">
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
            selectedIdInput: null,
            updated: 0,
            showWarning: false
        }
    },
    async mounted() {
        await this.$store.dispatch('envelopes/fetchEnvelopes', {})
        await this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: this.envId})
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
                if(!this.sameSettings(this.envelopeSettings)) {
                    this.showWarning = true;
                } else {
                    this.editAll = !this.sameSettings(this.envelopeSettings);
                }
            } else {
                if(this.editAll === false && !this.sameSettings(this.envelopeSettings)) {
                    this.showWarning = true;
                } else {
                    this.editAll = !this.editAll;
                }
            }
        },
        confirmSelectedID() {
            if(!(this.selectedIdInput === null)) {
                this.selectedId = this.selectedIdInput;
                this.showWarning = false;
                this.editAll = true;
            }
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

</style>
