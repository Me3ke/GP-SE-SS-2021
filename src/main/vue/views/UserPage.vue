<template>
    <div>
        <div>
            <Header></Header>
        </div>

        <BaseHeading name="Settings.content"></BaseHeading>

        <b-container fluid="xl" style="overflow: hidden">
            <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 85vh">
                <UserInfoBox :user="user" :userData="userData"></UserInfoBox>
                <SecuritySettingsBox :user="user" :userData="userData"
                                     @modalTrigger="toggleOverflow"></SecuritySettingsBox>
                <SignatureSettingsBox @uploadTrigger="toggleOverflow"></SignatureSettingsBox>
                <MessageSettingsBox></MessageSettingsBox>
                <EmailTemplate></EmailTemplate>
            </div>
        </b-container>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import UserInfoBox from "@/main/vue/components/settingsPage/UserInfoBox";
import {mapGetters} from 'vuex';
import MessageSettingsBox from "@/main/vue/components/settingsPage/MessageSettingsBox";
import SecuritySettingsBox from "@/main/vue/components/settingsPage/SecuritySettingsBox";
import SignatureSettingsBox from "@/main/vue/components/settingsPage/SignatureSettingsBox";
import EmailTemplate from "@/main/vue/components/EmailTemplate";

export default {
    name: "UserPage",
    components: {EmailTemplate, SignatureSettingsBox, SecuritySettingsBox, MessageSettingsBox, Header, UserInfoBox},
    data() {
        return {
            showOverflow: true
        }
    },
    methods: {
        toggleOverflow() {
            this.showOverflow = !this.showOverflow
        }
    },
    computed: {
        ...mapGetters({
            user: 'getUser',
            userData: 'getUserData'
        })
    },
    created() {
        this.$store.dispatch('fetchUserData')
        this.$store.dispatch('fetchUser')
    },
}
</script>

<style scoped>
</style>
