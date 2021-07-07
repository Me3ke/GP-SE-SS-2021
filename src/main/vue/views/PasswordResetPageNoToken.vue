<template>
    <section>
        <div class="background" style="height: 100vh; overflow: hidden">
            <LandingPageHeader></LandingPageHeader>
            <Header></Header>
            <div class=img-wrap>
                <img v-if="theme === '' " :src="elsaLight" alt="logo" class="header-image"/>
                <img v-else :src="elsaDark" alt="logo" class="header-image"/>
            </div>
            <div class="login-division">
              <div class="singleItemContainer">
                <b-form-group class="group"
                              v-bind:label="$t('Register.password')"
                              label-align="left"
                              label-size="sm">
                  <b-form-input type="password"
                                :state="validateState('password')"
                                v-model="register.password">

                  </b-form-input>
                </b-form-group>
              </div>
              <div class="singleItemContainer">
                <b-form-group class="group"
                              v-bind:label="$t('Register.passwordRepeat')"
                              label-align="left"
                              label-size="sm">
                  <b-form-input type="password"
                                :state="validateState('passwordRepeat')"
                                v-model="register.passwordRepeat">

                  </b-form-input>
                </b-form-group>
              </div>
              <b-button @click="onSubmit">
                {{$t('ResetPasswordPage.send')}}
              </b-button>
            </div>

        </div>

        <Footer></Footer>
    </section>

</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";

import {mapGetters} from "vuex";
import {validationMixin} from "vuelidate";
import {minLength, required, sameAs} from "vuelidate/lib/validators";
import userAPI from "@/main/vue/api/userAPI";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";

export default {
    mixins: [validationMixin],
    name: "PasswordResetPageNoToken",
    components: {LandingPageHeader, Header, Footer},
    data() {
        return {
          elsaLight: require('../assets/logos/ELSA_big.svg'),
          elsaDark: require('../assets/logos/ELSA_big_darkmode.svg'),
          register: {
            password: "",
            passwordRepeat: ""
          }
        }
    },
    methods: {
        // validate input
      validateState(name) {
        const { $dirty, $error } = this.$v.register[name];
        return $dirty ? !$error : null;
      },
      onSubmit() {
        this.$v.register.$touch();
        if (this.$v.register.$anyError) {
          return;
        }
        this.sendPassword()
      },
      async sendPassword() {
        await userAPI.resetPasswordWithoutToken(this.register.password, this.$store.state.auth.token)
            .then((response) => {
              if (response.data.status === 200) {
                alert(this.$t('ResetPasswordPage.success'))
              }
              else {alert(this.$t('ResetPasswordPage.fail'))}
              this.$router.push('/' + this.$i18n.locale + '/login');
            })
            .catch(function (error) {
              if (error.response) {
                console.log(error.response.data);
                alert("Server fehler:" + error.response.status);
                console.log(error.response.headers);
              }else { alert("else catch")}
              this.$router.push('/' + this.$i18n.locale + '/login');
            })
      }
    },
    validations: {
      register: {
        password: { required, minLength: minLength(8) },
        passwordRepeat: {
         required,
         minLength: minLength(8),
         sameAs: sameAs('password')
        }
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
