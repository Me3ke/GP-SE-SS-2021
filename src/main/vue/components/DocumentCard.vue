<template>
    <b-container fluid style="padding: 0">
        <b-row no-gutters>
            <b-col cols="11">
                <DocumentBox  @click.native="$router.push({name: 'document', params: {envId: envelopeId, docId: document.id}})"
                              :document = document :envelopeId="envelopeId"></DocumentBox>
            </b-col>
            <b-col cols="1">
                <settingsButton v-if="document.owner.email === this.user.user.email" @click.native="$router.push({name: 'settings', params: {envId: envelopeId}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import DocumentBox from "@/main/vue/components/DocumentBox";
import {mapGetters} from "vuex";
export default {
    name: "DocumentCard",
    components: {DocumentBox, settingsButton},
    props: {
        document: Object,
        envelopeId: [String, Number]
    },
    created: {
        ...mapGetters({
            user: 'getUser',
        })
    }
}
</script>

<style scoped>

</style>
