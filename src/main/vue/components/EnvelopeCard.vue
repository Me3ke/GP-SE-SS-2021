<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced" @twoFacTrigger="goToEnv"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="checkEnv"></EnvelopeBox>
                <div>
                    <div v-if="this.envelope.owner.email === this.$store.state.auth.username">
                        <EnvelopeProgressBar
                            :envelope="envelopeProgress(envelope.documents)"
                            :env="envelope"
                        ></EnvelopeProgressBar>
                    </div>
                </div>
            </b-col>
            <b-col cols="1">
                <settingsButton
                    @click.native="$router.push({name: 'settings', params: {envId: envelope.id}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import EnvelopeBox from "@/main/vue/components/EnvelopeBox";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
import {mapGetters} from "vuex";
import EnvelopeProgressBar from "@/main/vue/components/EnvelopeProgressBar";

export default {
    name: "EnvelopeCard",
    components: {EnvelopeProgressBar, TwoFacAuth, EnvelopeBox, settingsButton},
    props: {
        envelope: Object
    },
    data() {
        return {
            showAuth: false,
            advanced: false
        }
    },

    computed: {
        ...mapGetters({
            envelopeProgress: 'document/getDocumentProgressArrayByEnvelope',

})
    },

    beforeMount() {
        //this.$store.dispatch('document/resetState')


    },
    mounted() {
        // gives back if advanced signature is needed for at least on document ind envelope (if false -> simple signature is needed)
        for (let i = 0; i < this.envelope.documents.length; i++) {
            if (this.envelope.documents[i].signatureType === 'ADVANCED_SIGNATURE') {
                this.advanced = true
            }
        }

        this.$store.dispatch('document/progressOfAllDocumentsInEnv', {
            envelope: this.envelope
        })
    },

    methods: {

        // checks if env needs advanced signature, if so 2FacAuth has to be done; otherwise go to env directly
        async checkEnv() {
            // checking signatureType and is current user is not owner of document
            if (this.advanced && (this.envelope.owner.email !== this.$store.state.auth.username)) {
                this.showAuth = true
            } else {
                this.goToEnv()
            }
        },
        // goes to corresponding envelopePage
        goToEnv() {
            this.showAuth = false
            this.$router.push({name: 'envelope', params: {envId: this.envelope.id}})
        },
        // closes 2FacAuth, stays on overview page
        closeAuth() {
            this.showAuth = false
        }
    }
}
</script>

<style scoped>

</style>
