<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced" @twoFacTrigger="goToEnv"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="checkEnv"></EnvelopeBox>
                <div>
                    <div v-if="this.envelope.owner.username === this.$store.state.auth.username && showProgress">
                        <EnvelopeProgressBar
                            :envelope="envelopeProgress(envelope.documents)"
                            :env="envelope"
                        ></EnvelopeProgressBar>
                    </div>
                </div>
            </b-col>
            <b-col cols="1">
                <settingsButton v-if="envelope.owner.username === user.username"
                                @click.native="settings()"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/overviewPage/envSettingsButton";
import EnvelopeBox from "@/main/vue/components/overviewPage/EnvelopeBox";
import {mapGetters} from "vuex";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
import EnvelopeProgressBar from "@/main/vue/components/EnvelopeProgressBar";

export default {
    name: "EnvelopeCard",
    components: {EnvelopeProgressBar, TwoFacAuth, EnvelopeBox, settingsButton},
    props: {
        envelope: Object
    },
    created() {
        this.$store.dispatch('fetchUser')
    },
    data() {
        return {
            showAuth: false,
            advanced: false,
            showProgress: false
        }
    },

    computed: {
        ...mapGetters({
            envelopeProgress: 'document/getDocumentProgressArrayByEnvelope',
            documentInfo: 'document/getDocumentInfo',

            auth: 'twoFakAuth/getAuthMust',
            counter: 'twoFakAuth/getLogoutCounter',
            setUp: 'twoFakAuth/getHasSetUp',
            user: 'getUser'
        })
    },

    beforeMount() {
        //this.$store.dispatch('document/resetState')


    },
    async mounted() {
        // gives back if advanced signature is needed for at least on document ind envelope (if false -> simple signature is needed)
        for (let i = 0; i < this.envelope.documents.length; i++) {
            await this.$store.dispatch('document/fetchDocumentInfo', {
                envId: this.envelope.id,
                docId: this.envelope.documents[i].id,
            })
            if (this.documentInfo.signatureType === 'ADVANCED_SIGNATURE') {
                this.advanced = true
            }
            if (this.documentInfo.state !== 'ARCHIVED' && !this.documentInfo.draft) {
                this.showProgress = true
            }
        }

        await this.$store.dispatch('document/progressOfAllDocumentsInEnv', {
            envelope: this.envelope
        })


        if (this.counter !== -1) {
            this.showAuth = true
        }
    },

    methods: {

        // checks if env needs advanced signature, if so 2FacAuth has to be done; otherwise go to env directly
        async checkEnv() {
            // checking if set-up is there
            await this.$store.dispatch('twoFakAuth/fetchHasSetUp')
            let auth
            if (this.setUp) {
                auth = this.auth
            } else {
                auth = true
            }
            // checking signatureType and if auth is necessary at the moment
            if (this.advanced && auth) {
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
        },
        settings() {
            let envelopeId = this.envelope.id;
            this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: envelopeId}).then(() => this.$router.push({
                name: 'settings',
                params: {envId: this.envelope.id}
            }));
        }
    }
}
</script>

<style scoped>

</style>
