<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced" @twoFacTrigger="goToEnv"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="checkEnv"></EnvelopeBox>
            </b-col>
            <b-col cols="1">
                <settingsButton v-if="envelope.owner.email === user.email" @click.native="$router.push({name: 'settings', params: {envId: envelope.id}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/overviewPage/envSettingsButton";
import EnvelopeBox from "@/main/vue/components/overviewPage/EnvelopeBox";
import {mapGetters} from "vuex";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
export default {
    name: "EnvelopeCard",
    components: {TwoFacAuth, EnvelopeBox, settingsButton},
    props: {
        envelope: Object
    },
    created() {
        this.$store.dispatch('fetchUser')
    },
    computed: {
    ...
        mapGetters({
            user: 'getUser'
        })
    },
    data() {
        return {
            showAuth: false,
            advanced: false
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
