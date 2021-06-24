<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced" @twoFacTrigger="goToEnv"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="checkEnv"></EnvelopeBox>
                <div>
                    <div v-if="this.envelope.owner.email === this.$store.state.auth.username">
                        <ProgressBar
                            :env="documentProgressArray"
                            :envelope="envelope"
                        ></ProgressBar>
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
import ProgressBar from "@/main/vue/components/ProgressBar";
import {mapGetters} from "vuex";

export default {
    name: "EnvelopeCard",
    components: {ProgressBar, TwoFacAuth, EnvelopeBox, settingsButton},
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
          documentProgressArray: 'document/getDocumentProgressArray',
          document: 'document/getDocument'

      })
    },

   async beforeCreate() {
        await this.$store.dispatch('document/resetState', [])

        for(let i = 0; i < this.envelope.documents.length; i++) {
            await this.$store.dispatch('document/getDocumentProgress', {
                envId: this.envelope.id,
                docId: this.envelope.documents[i].id
            })
        }
    },

    mounted() {
        // gives back if advanced signature is needed for at least on document ind envelope (if false -> simple signature is needed)
        for (let i = 0; i < this.envelope.documents.length; i++) {
            if (this.envelope.documents[i].signatureType === 'ADVANCED_SIGNATURE') {
                this.advanced = true
            }
        }
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
