<template>
<div>
    <div>
        <h5>{{$t('UploadDoc.areYouSure')}}</h5>
    </div>

    <div style="margin:1em">
        <h6 style="font-weight:bold;">{{$t('UploadDoc.document')}}</h6>
        <div class="card">
            {{this.file.title}}.{{this.file.type}}
        </div>
    </div>

    <div style="margin:1em">
        <h6 style="font-weight:bold;">{{$t('UploadDoc.envelope')}}</h6>
        <div class="card">
            <div v-if="selectedEnvelope.id === null">
                {{this.selectedEnvelope.name}}
            </div>
            <div v-else>
                {{this.envelope(this.selectedEnvelope.id).name}}
            </div>
        </div>
    </div>

    <div style="margin:1em">
        <h6 style="font-weight:bold;">{{$t('Settings.DocumentSettings.endDate')}}</h6>
        <div class="card">
            {{this.settings.endDate}}
        </div>
    </div>

    <div style="margin:1em">
        <h6 style="font-weight:bold;">{{$t('Settings.DocumentSettings.reader')}}</h6>
        <div class="card">
            <div v-for="reader in getReaders()" :key="reader">
                {{reader}}
            </div>
        </div>
    </div>

    <div style="margin:1em">
        <h6 style="font-weight:bold;">{{$t('Settings.DocumentSettings.signatory')}}</h6>
        <div class="card">
            <div v-for="signatory in getSignatories()" :key="signatory">
                {{signatory}}
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "SettingsPreview",
    props: {
        settings: Object,
        selectedEnvelope: Object,
        file: Object
    },
    methods: {
        getReaders() {
            let readers = [];
            let i;
            for(i = 0; i < this.settings.signatories.length; i++) {
                if(this.settings.signatories[i].type === 0) {
                    readers.push(this.settings.signatories[i].email);
                }
            }
            return readers;
        },
        getSignatories() {
            let signatories = [];
            let i;
            for(i = 0; i < this.settings.signatories.length; i++) {
                if(this.settings.signatories[i].type === 1 || this.settings.signatories[i].type === 2) {
                    signatories.push(this.settings.signatories[i].email);
                }
            }
            return signatories;
        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope',
        })
    }
}
</script>

<style scoped>
.card{
    border-top-color: var(--dark-grey);
    border-bottom-color: var(--whitesmoke);
    border-left-color: var(--whitesmoke);
    border-right-color: var(--whitesmoke);
    border-radius: 0;
    background-color: var(--whitesmoke);
}
</style>
