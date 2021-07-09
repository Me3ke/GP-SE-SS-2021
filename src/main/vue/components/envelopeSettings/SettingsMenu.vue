<template>
    <div>
        <!-- End Date -->
        <div class="card">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.endDate')}}
            </div>

            <EndDateSettings
                @updateEndDate="updateEndDate"
                :endDate="this.endDate"></EndDateSettings>
        </div>

        <!-- Reader -->
        <div class="card" style="margin-top:3vh">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.reader')}}
            </div>

            <div>
                <ReaderSettings
                    @updateReader="updateReader"
                    :readers="readers"></ReaderSettings>
            </div>
        </div>

        <!-- Signatories -->
        <div class="card" style="margin-top:3vh">
            <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                {{$t('Settings.DocumentSettings.signatory')}}
            </div>

            <div>
                <SignatorySettings
                    @updateSignatories="updateSignatories"
                    :orderRelevant="orderRelevant"
                    :signatories="signatories"
                    ></SignatorySettings>
            </div>
        </div>

    </div>
</template>

<script>
import ReaderSettings from "@/main/vue/components/envelopeSettings/ReaderSettings";
import SignatorySettings from "@/main/vue/components/envelopeSettings/SignatorySettings";
import {mapGetters} from "vuex";
import EndDateSettings from "@/main/vue/components/envelopeSettings/EndDateSettings";

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
        ReaderSettings,
        SignatorySettings,
        EndDateSettings
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
            let newSettings = {signatories: this.makeSignatories(readers, this.signatories), orderRelevant: this.orderRelevant, endDate: this.endDate};
            console.log(newSettings);
            this.saveSettings(newSettings);
        },
        updateSignatories: function(signatories, orderRelevant) {
            let newSettings = {signatories: this.makeSignatories(this.readers, signatories), orderRelevant: orderRelevant, endDate: this.endDate};
            console.log(newSettings);
            this.saveSettings(newSettings);
        },
        updateEndDate: function(endDate) {
            let newSettings = {signatories: this.makeSignatories(this.readers, this.signatories), orderRelevant: this.orderRelevant, endDate: endDate};
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
</style>
