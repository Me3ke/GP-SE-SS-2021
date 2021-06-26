<template>
    <section>
        <div class="background" style="height: 100vh; overflow: hidden">

            <TwoFacAuth v-if="showTwoFactorLogin" :before-doc="false" @twoFacTrigger="closeTwoFacAuth"></TwoFacAuth>

            <LandingPageHeader></LandingPageHeader>
            <div class=img-wrap>
                <img v-if="theme === '' " :src="elsaLight" alt="logo" class="header-image"/>
                <img v-else :src="elsaDark" alt="logo" class="header-image"/>
            </div>
            <div class="login-division">
                <login-component v-show="showLogin" @loginComponentTrigger="twoFacAuthCheck"></login-component>
                <b-link v-show="showLogin" v-on:click="showLogin=false">Passwort vergessen?</b-link>
                <forgot-password-component v-show="!showLogin"></forgot-password-component>
                <b-link v-show="!showLogin" v-on:click="showLogin=true">Zurück zum Login</b-link>
            </div>
        </div>

        <Footer></Footer>
    </section>

</template>

<script>
import Footer from "@/main/vue/components/Footer";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";
import LoginComponent from "@/main/vue/components/LoginComponent";
import ForgotPasswordComponent from "../components/settingsPage/ForgotPasswordComponent";
import TwoFacAuth from "@/main/vue/components/popUps/TwoFacAuth";
import {mapGetters} from "vuex";

export default {
    name: "LoginPage",
    components: {TwoFacAuth, ForgotPasswordComponent, LoginComponent, LandingPageHeader, Footer},
    data() {
        return {
            elsaLight: require('../assets/logos/ELSA_big.svg'),
            elsaDark: require('../assets/logos/ELSA_big_darkmode.svg'),
            showLogin: true,
            showTwoFactorLogin: false
        }
    },
    methods: {
        // checks if twoFacAuth should be should, if not continues
        async twoFacAuthCheck() {
            //  loads twoFacAuthLogin info into local storage
            await this.$store.dispatch('getTwoFactorLogin')
            if (this.twoFactorLogin) {
                this.showTwoFactorLogin = true
            } else {
                await this.$router.push('/' + this.$i18n.locale + '/overview')
            }
        },
        // closes TwoFakAuth pop up, continues to overview page
        closeTwoFacAuth() {
            this.showTwoFactorLogin = false
            this.$router.push('/' + this.$i18n.locale + '/overview')
        }
    },
    computed: {
        ...mapGetters({
            twoFactorLogin: 'getTwoFactorLogin',
            theme: 'theme/getTheme'
        })
    }
}
</script>

<style scoped>


.background {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-image: linear-gradient(to bottom, var(--background-fade-one) 0%, var(--background-fade-two) 30%, var(--background-fade-three) 100%), url(../assets/background.png);
    background-repeat: no-repeat;
    background-size: 100% auto;
}

.login-division {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 5vh;
    width: 100vw;
    min-height: 100vh;
    border-top-width: 1px;
    border-top-style: solid;
    border-top-color: var(--shadow-grey);
    background-color: var(--headerFadeOne);
    background-size: 100% auto;
}


.header-image {
    padding-top: 2vw;
    padding-bottom: 2vw;
    width: 25%;
    height: auto;
}
</style>
