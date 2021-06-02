<template>
    <b-container fluid>
        <b-row no-gutters>
            <b-col cols="11">
                <DocumentBox  @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envelopeId}})"
                              :document = document :envelopeId="envelopeId"></DocumentBox>
            </b-col>
            <b-col cols="1">
                <settingsButton @click.native="$router.push({name: 'settings', params: {envId: envelopeId}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import DocumentBox from "@/main/vue/components/DocumentBox";
export default {
    name: "DocumentCard",
    components: {DocumentBox, settingsButton},
    props: {
        document: Object,
        envelopeId: [String, Number]
    }
}
</script>

<style scoped>

</style>
