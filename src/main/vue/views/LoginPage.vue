<template>
    <section>
        <div class="background" style="height: 100vh; overflow: hidden">

            <LandingPageHeader></LandingPageHeader>
            <div class=img-wrap>
                <img :src="image" alt="logo" class="header-image"/>
            </div>
            <div class="login-division">
                <login-component v-show="showLogin"></login-component>
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
import image from "../assets/logos/ELSA_big.svg";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";
import LoginComponent from "@/main/vue/components/LoginComponent";
import ForgotPasswordComponent from "../components/settingsPage/ForgotPasswordComponent";

export default {
    data: function () {
        return {
            image: image,
            showLogin: true,
        }
    },
    name: "LoginPage",
    components: {ForgotPasswordComponent, LoginComponent, LandingPageHeader, Footer},
    methods: {}
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
