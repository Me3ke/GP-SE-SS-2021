<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced" @twoFacTrigger="goToDoc"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <DocumentBox @click.native="checkDoc" :document=document :envelopeId="envelopeId"></DocumentBox>
                <div v-if="//documentProgressById(document.id) &&
                     this.document.owner.email === this.$store.state.auth.username">
                    <DocumentProgressBar
                        :state="document.state"
                        :docId="document.id"
                        :getDocumentProgress="documentProgressById(document.id)"
                    ></DocumentProgressBar>
                </div>
            </b-col>
            <b-col cols="1">
                <settingsButton v-if="document.owner.email === user.email" @click.native="$router.push({name: 'settings', params: {envId: envelopeId}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/overviewPage/envSettingsButton";
import DocumentBox from "@/main/vue/components/overviewPage/DocumentBox";
import {mapGetters} from "vuex";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
import DocumentProgressBar from "@/main/vue/components/DocumentProgressBar";
export default {
    name: "DocumentCard",
    components: {DocumentProgressBar, TwoFacAuth, DocumentBox, settingsButton},
    props: {
        document: Object,
        envelopeId: [String, Number],
        showProgress: Boolean
    },
    computed: {
        ...mapGetters({
            documentProgress: 'document/getDocumentProgressArray',
            documentProgressById: 'document/getDocumentProgressArrayById',

            auth: 'twoFakAuth/getAuthMust',
            counter: 'twoFakAuth/getLogoutCounter',
            setUp: 'twoFakAuth/getHasSetUp',
            documentInfo: 'document/getDocumentInfo',
            user: 'getUser'
        }),

    },
    created() {
        this.$store.dispatch('fetchUser')
    },
    data() {
        return {
            showAuth: false,
            advanced: false
        }
    },
    methods: {
        // checks if doc needs advanced signature, if so 2FacAuth has to be done; otherwise go to doc directly
        async checkDoc() {
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
                this.goToDoc()
            }
        },
        // goes to corresponding documentPage
        goToDoc() {
            this.showAuth = false
            this.$router.push({name: 'document', params: {envId: this.envelopeId, docId: this.document.id}})
        },
        // closes 2FacAuth, stays on overview page
        closeAuth() {
            this.showAuth = false
        }
    },

    async mounted() {
        await this.$store.dispatch('document/documentProgress', {
            envId: this.envelopeId,
            docId: this.document.id,
        })

        await this.$store.dispatch('document/fetchDocumentInfo', {
            envId: this.envelopeId,
            docId: this.document.id,
        })

        if (this.documentInfo.signatureType === 'ADVANCED_SIGNATURE') {
            this.advanced = true
        }

        if (this.counter !== -1) {
            this.showAuth = true
        }
    }
}
</script>

<style scoped>

</style>
