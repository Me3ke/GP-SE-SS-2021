<template>
    <div class="background" style="background-color: var(--whitesmoke);">
        <Header></Header>
        <BaseHeading :name="this.getEnv(envId).name" :translate="false" style="position: fixed"></BaseHeading>

        <b-container fluid="xl">
            <div style="margin-top:15vh">
                <div class="custom-control custom-switch">
                    <input type="checkbox" class="custom-control-input" id="allIndividualSwitch" v-model="individual">
                    <label class="custom-control-label" for="allIndividualSwitch"> {{$t('Settings.DocumentSettings.editAll')}} </label>
                </div>
            </div>

            <div v-if="individual" style="margin-top:3vh">
                <DocumentDropDown style="margin-top:0.5vh"
                    v-for="document in this.getEnv(envId).documents" :key="document.id"
                    :document="document"
                    :signatories="getSignatories(getSettings(document.id))"
                    :readers="getReaders(getSettings(document.id))"
                    :endDate="getSettings(document.id).endDate"
                    :orderRelevant="getSettings(document.id).orderRelevant">
                </DocumentDropDown>
            </div>
            <div v-if="!individual">
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.chooseDate')}}
                    </div>
                    <div>
                        <b-form-datepicker class="mb-2"></b-form-datepicker>
                        <!-- <p>{{this.settings.endDate}}</p> -->
                    </div>
                </div>
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.reader')}}
                    </div>
                    <div style="padding:2em">
                        <ReaderMenu :readers="getReaders(this.settings[0])"></ReaderMenu>
                    </div>
                </div>
                <div class="card" style="margin-top:3vh">
                    <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                        {{$t('Settings.DocumentSettings.signatory')}}
                    </div>
                    <div style="padding:2em">
                        <SignatoryMenu :inModal="false" :signatories="getSignatories(this.settings[0])"></SignatoryMenu>
                    </div>
                </div>
                <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
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

export default {
    name: "envSettingsPage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, SignatoryMenu, ReaderMenu, DocumentDropDown},
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
        this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: this.envId})
    },
    computed: {
        ...mapGetters({
            getEnv: 'envelopes/getEnvelope',
            getEnvSettings: 'documentSettings/getEnvelopeSettings'
        })
    },
    data() {
        return{
            individual: this.sameSettings(this.getEnvSettings),
            settings: this.getEnvSettings.data
        }
    },
    methods: {
        sameSettings(settings) {
            let initial = settings[0];
            let i;
            for(i = 1; i < settings.length; i++) {
                if(!(initial.signatories === settings[i].signatories)) {
                    return false;
                }
                if(!(initial.orderRelevant === settings[i].orderRelevant)) {
                    return false;
                }
                if(!(initial.endDate === settings[i].endDate)) {
                    return false;
                }
            }
            return true;
        },
        getReaders(settings) {
            let readers = [];
            let i;
            for(i = 0; i < settings.signatories; i++) {
                if(settings.signatories[i].type === "REVIEW") {
                    readers.push(settings.signatories);
                } else {
                    return readers;
                }
            }
            return readers;
        },
        getSignatories(settings) {
            let signatories = [];
            let i;
            for(i = 0; i < settings.signatories; i++) {
                if(!(settings.signatories[i].type === "REVIEW")) {
                    signatories.push(settings.signatories);
                }
            }
            return signatories;
        },
        getSettings(docId) {
            let i;
            for(i = 0; i < this.getEnvSettings.length; i++) {
                if (this.getEnvSettings[i].documentID === docId) {
                    return this.getEnvSettings[i];
                }
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
</style>
