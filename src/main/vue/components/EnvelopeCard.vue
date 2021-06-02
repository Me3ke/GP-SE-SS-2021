<template>
    <b-container fluid>
        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"></EnvelopeBox>
            </b-col>
            <b-col cols="1">
                <settingsButton @click.native="$router.push({name: 'settings', params: {envId: envelope.id}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import EnvelopeBox from "@/main/vue/components/EnvelopeBox";
export default {
    name: "EnvelopeCard",
    components: {EnvelopeBox, settingsButton},
    props: {
        envelope: Object
    }
}
</script>

<style scoped>

</style>
