<template>
    <div>
        <!-- End Date -->
        <div class="card" style="background-color: var(--whitesmoke); border-color: var(--dark-grey)">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.endDate')}}
            </div>

            <EndDateSettings
                @updateEndDate="updateEndDate"
                :endDate="this.endDate"></EndDateSettings>
        </div>

        <!-- Reader -->
        <div class="card" style="margin-top:3vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.reader')}}
            </div>

            <ReaderSettings
                @updateReader="updateReader"
                :readers="readers"></ReaderSettings>
        </div>

        <!-- Signatories -->
        <div class="card" style="margin-top:3vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.signatory')}}
            </div>

            <SignatorySettings
                @updateSignatories="updateSignatories"
                :orderRelevant="orderRelevant"
                :signatories="signatories"
                ></SignatorySettings>
        </div>

        <!-- History -->
        <div class="card" style="margin-top:3vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('UploadDoc.showHistoryTitle')}}
            </div>

            <HistorySettings @updateHistory="updateHistory" :showHistory="showHistory"></HistorySettings>
        </div>

    </div>
</template>

<script>
import ReaderSettings from "@/main/vue/components/envelopeSettings/ReaderSettings";
import SignatorySettings from "@/main/vue/components/envelopeSettings/SignatorySettings";
import {mapGetters} from "vuex";
import EndDateSettings from "@/main/vue/components/envelopeSettings/EndDateSettings";
import HistorySettings from "@/main/vue/components/envelopeSettings/HistorySettings";

export default {
    name: "settingsMenu",
    props: {
        envId: Number,
        document: Object,
        readers: Array,
        signatories: Array,
        endDate: String,
        orderRelevant: Boolean,
        editAll: Boolean,
        showHistory: Boolean
    },
    components: {
        ReaderSettings,
        SignatorySettings,
        EndDateSettings,
        HistorySettings,
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
        updateReader: function(readers) {
            let newSettings = {signatories: this.makeSignatories(readers, this.signatories), orderRelevant: this.orderRelevant, endDate: this.endDate, showHistory: this.showHistory};
            console.log(newSettings);
            this.saveSettings(newSettings);
        },
        updateSignatories: function(signatories, orderRelevant) {
            let newSettings = {signatories: this.makeSignatories(this.readers, signatories), orderRelevant: orderRelevant, endDate: this.endDate, showHistory: this.showHistory};
            console.log(newSettings);
            this.saveSettings(newSettings);
        },
        updateEndDate: function(endDate) {
            let newSettings = {signatories: this.makeSignatories(this.readers, this.signatories), orderRelevant: this.orderRelevant, endDate: endDate, showHistory: this.showHistory};
            this.saveSettings(newSettings);
        },
        updateHistory: function(showHistory) {
            let newSettings = {signatories: this.makeSignatories(this.readers, this.signatories), orderRelevant: this.orderRelevant, endDate: this.endDate, showHistory: showHistory};
            this.saveSettings(newSettings);
        },
        makeSignatories(readers, signatories) {
            let newSignatories = [];
            let i;
            for (i = 0; i < readers.length; i++) {
                newSignatories.push(readers[i]);
            }
            for (i = 0; i < signatories.length; i++) {
                newSignatories.push(signatories[i]);
            }
            return newSignatories;
        },
        async saveSettings(settings) {
            if(this.editAll) {
                let i;
                for(i = 0; i < this.envelope(this.envId).documents.length; i++) {
                    let documentID = this.envelope(this.envId).documents[i].id;
                    await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": documentID, "envId": this.envId, "settings": settings});
                }
            } else {
                await this.$store.dispatch('documentSettings/changeDocumentSettings', {"docId": this.document.id, "envId": this.envId, "settings": settings});
            }
        }
    }
}
</script>

<style scoped>
.iconBlue {
    fill: var(--elsa-blue);
    height: 1.5em;
    width: auto;
}

.list-group-item {
    height: 2.5em;
    padding: 0.5em;
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.form-control:focus {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}
</style>
