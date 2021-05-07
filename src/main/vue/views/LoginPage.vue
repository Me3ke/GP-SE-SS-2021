<template>
    <section>
        <div class="background">
            <div class = img-wrap>
                <img :src = "image"  class="header-image"/>
            </div>
            <div class="login-division">

                <div v-if="showlogin" class = login-box>
                    <h3>Anmeldung</h3>
                    <b-form>
                        <b-form-group
                            id="input-group-email"
                        >
                            <b-form-input
                                id="input-email"
                                v-model="form.email"
                                type="email"
                                placeholder="E-Mail"
                                required
                            ></b-form-input>
                        </b-form-group>
                        <b-form-group id="input-group-password">
                            <b-form-input
                                id="input-paswword"
                                v-model="form.name"
                                type="password"
                                placeholder="Password"
                                required
                            ></b-form-input>
                        </b-form-group>
                        <b-link v-on:click="showlogin = false">
                            <h5> Passwort vergessen? </h5>
                        </b-link>
                        <b-button>Anmelden</b-button>
                        <h5> Noch kein Konto? <b-link>Registrieren</b-link></h5>

                    </b-form>
                </div>
                <div v-if="!showlogin" class = login-box>
                    <h3>Passwort zurücksetzen</h3>
                    <h5>Sie erhalten eine E-Mail zum zurücksetzen Ihres Passwortes</h5>
                    <b-form>
                        <b-form-group
                            id="input-group-email"
                        >
                            <b-form-input
                                id="input-email"
                                v-model="form.email"
                                type="email"
                                placeholder="E-Mail"
                                required
                            ></b-form-input>
                        </b-form-group>
                        <b-button>Senden</b-button>
                        <b-link v-on:click="showlogin = true">
                            <h5> Zurück zur Anmeldung</h5>
                        </b-link>
                        <h5> Noch kein Konto? <b-link>Registrieren</b-link></h5>

                    </b-form>
                </div>
            </div>
        </div>
        <Footer></Footer>
    </section>

</template>

<script>
import Footer from "@/main/vue/components/Footer";
import image from "../assets/ELSA_big.png";
export default {
    data: function () {
        return {
            image: image,
            show: true,
            form: {
                email: ''
            },
            showlogin: true
        }
    },
    name: "LoginPage",
    components: {Footer},
    methods: {
        historyThere() {
            return window.history.length > 2
        }
    }
}
</script>

<style scoped>



.background {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-image: linear-gradient(to bottom, rgba(255,153,153,0) 0%,rgba(255,255,255,1) 30%, rgb(255, 255, 255) 100%), url(../assets/background.png);
    background-repeat: no-repeat;
    background-size: 100% auto;
}
.login-division{
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    border-top-width: 1px;
    border-top-style: solid;
    border-top-color: rgba(0,0,0,0.3);
    background-color: rgba(0,0,0,0.2);
    background-size: 100% auto;
}
.login-box{
    color: white;
    display: inline-block;
    padding: 20px;
    margin: 10px;
    border-radius: 6px 6px 6px 6px;
    background-color: #49535F;
}

.email-label, .password-label {
    color: white;
    font-size: x-large;
}


.header-image{
    padding-top: 2vw;
    padding-bottom: 2vw;
    width: 25%;
    height: auto;
}
</style>
