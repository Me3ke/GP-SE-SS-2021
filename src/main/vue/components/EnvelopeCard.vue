<template>
    <b-container fluid style="padding: 0">
        <b-row no-gutters>
            <b-col cols="11">
                <EnvelopeBox :envelope="envelope" @click.native="$router.push({name: 'envelope', params: {envId: envelope.id}})"></EnvelopeBox>
            </b-col>
            <b-col cols="1">
                <settingsButton v-if="envelope.owner.email === user.user.email" @click.native="$router.push({name: 'settings', params: {envId: envelope.id}})"></settingsButton>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";
import EnvelopeBox from "@/main/vue/components/EnvelopeBox";
import {mapGetters} from "vuex";

export default {
    name: "EnvelopeCard",
    components: {EnvelopeBox, settingsButton},
    props: {
        envelope: Object
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
