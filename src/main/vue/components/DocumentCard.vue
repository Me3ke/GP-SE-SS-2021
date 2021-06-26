<template>
    <b-container fluid style="padding: 0">

        <TwoFacAuth v-if="showAuth" :advanced="advanced()" @twoFacTrigger="goToDoc"
                    @closeTrigger="closeAuth"></TwoFacAuth>

        <b-row no-gutters>
            <b-col cols="11">
                <DocumentBox @click.native="checkDoc" :document=document :envelopeId="envelopeId"></DocumentBox>
                    <div  v-if="//documentProgressById(document.id) &&
                     this.document.owner.email === this.$store.state.auth.username">
                        <ProgressBar
                            :state="document.state"
                            :docId="document.id"
                            :getDocumentProgress="documentProgressById(document.id)"
                        ></ProgressBar>
                    </div>
            </b-col>
            <b-col cols="1">
                <settingsButton
                    @click.native="$router.push({name: 'settings', params: {envId: envelopeId}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import DocumentBox from "@/main/vue/components/DocumentBox";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
import ProgressBar from "@/main/vue/components/ProgressBar";
import {mapGetters} from "vuex";

export default {
    name: "DocumentCard",
    components: {ProgressBar, TwoFacAuth, DocumentBox, settingsButton},
    props: {
        document: Object,
        envelopeId: [String, Number],
        showProgress: Boolean
    },
    computed: {
        ...mapGetters({
        documentProgress: 'document/getDocumentProgress',
        documentProgressById: 'document/getDocumentProgressArrayById'
    }),

    },
    data() {
        return {
            showAuth: false
        }
    },
    methods: {
        // gives back if advanced signature is needed (if false -> simple signature is needed)
        advanced() {
            return this.document.signatureType === 'ADVANCED_SIGNATURE'
        },
        // checks if doc needs advanced signature, if so 2FacAuth has to be done; otherwise go to doc directly
        checkDoc() {
            // checking signatureType and is current user is not owner of document
            if (this.advanced && (this.document.owner.email !== this.$store.state.auth.username)) {
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

    mounted() {
        this.$store.dispatch('document/documentProgress', {
            envId: this.envelopeId,
            docId: this.document.id,
        })
    }
}
</script>

<style scoped>

</style>
