<template>
  <div class="registerContainer">
    <div class="registerFormBox">
      <h2> {{ $t('Register.register') }}</h2>
      <div class="halfSplitContainer">
        <b-form-group class="group"
                      v-bind:label="$t('Register.firstname')"
                      label-align="left"
                      label-class="inputLabel"
                      label-size="sm">
          <b-form-input :state="validateState('firstname')"
                        v-model="register.firstname">

          </b-form-input>
        </b-form-group>
        <b-form-group class="group"
                      v-bind:label="$t('Register.surname')"
                      label-align="left"
                      label-size="sm">
          <b-form-input :state="validateState('surname')"
                        v-model="register.surname">

          </b-form-input>
        </b-form-group>
      </div>
      <div class="singleItemContainer">
        <b-form-group class="group"
                      v-bind:label="$t('Register.email')"
                      label-align="left"
                      label-size="sm">
          <b-form-input :state="validateState('username')"
                        v-model="register.username">

          </b-form-input>
        </b-form-group>
      </div>
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
      <div class="halfSplitContainer">
        <b-form-group class="bigGroup"
                      v-bind:label="$t('Register.street')"
                      label-align="left"
                      label-class="inputLabel"
                      label-size="sm">
          <b-form-input class="notRequired" v-model="register.street">

          </b-form-input>
        </b-form-group>
        <b-form-group class="smallGroup"
                      v-bind:label="$t('Register.streetNumber')"
                      label-align="left"
                      label-size="sm">
          <b-form-input class="notRequired" v-model="register.streetNumber">

          </b-form-input>
        </b-form-group>
      </div>
      <div class="halfSplitContainer">
        <b-form-group class="group"
                      v-bind:label="$t('Register.postcode')"
                      label-align="left"
                      label-class="inputLabel"
                      label-size="sm">
          <b-form-input class="notRequired" v-model="register.postCode">

          </b-form-input>
        </b-form-group>
        <b-form-group class="group"
                      v-bind:label="$t('Register.city')"
                      label-align="left"
                      label-size="sm">
          <b-form-input class="notRequired" v-model="register.city">

          </b-form-input>
        </b-form-group>
      </div>
      <div class="halfSplitContainer">
        <b-form-group class="group"
                      v-bind:label="$t('Register.birthdate')"
                      label-align="left"
                      label-class="inputLabel"
                      label-size="sm">
          <b-form-datepicker class="notRequired" v-model="register.birthdate">

          </b-form-datepicker>
        </b-form-group>
        <b-form-group class="group"
                      v-bind:label="$t('Register.phoneNumber')"
                      label-align="left"
                      label-size="sm">
          <b-form-input class="notRequired" v-model="register.phoneNumber">

          </b-form-input>
        </b-form-group>
      </div>
      <div class="halfSplitContainer">
        <b-button variant="outline-primary" class="group" @click="routeToLogin">{{$t('Register.login')}}</b-button>
        <b-button class="group" @click="onSubmit"> {{$t('Register.sign-up')}}</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, minLength, sameAs, email } from "vuelidate/lib/validators"
import userAPI from "@/main/vue/api/userAPI";

export default {
  mixins: [validationMixin],
  name: "RegisterForm",
  methods:{
    routeToLogin(){
      this.$router.push('/'+this.$i18n.locale + '/login')
    },
    validateState(name) {
      const { $dirty, $error } = this.$v.register[name];
      return $dirty ? !$error : null;
    },
    onSubmit() {
      this.$v.register.$touch();
      if (this.$v.register.$anyError) {
        return;
      }
      this.registerNewUser()
    },
    async registerNewUser() {
      await userAPI.registerUser(this.register)
          .then(() => {
            alert("Erfolgreich registriert. Bitte prüfen Sie Ihre E-Mails.")
            this.$router.push('/' + this.$i18n.locale + '/overview')
          })
          .catch(function (error) {
            if (error.response) {
              console.log(error.response.data);
              alert("Server fehler:" + error.response.status);
              console.log(error.response.headers);
            }else { alert("else catch")}
          })
    }
  },
  validations: {
    register: {
      firstname: { required, minLength: minLength(1) },
      surname: { required, minLength: minLength(1) },
      username: { required, email },
      password: { required, minLength: minLength(8) },
      passwordRepeat: {
        required,
        minLength: minLength(8),
        sameAs: sameAs('password')
      },
    }
  },
  data() {
    return {
      register: {
        firstname: "",
        surname: "",
        username: "",
        password: "",
        passwordRepeat: "",
        street: "",
        streetNumber: "",
        postCode: "",
        city: "",
        birthdate: "2021-01-01",
        phoneNumber: "",
        reCaptcha: ""
      }
    };
  }
}
</script>

<style scoped>

h2 {
  margin-top: 1rem;
  margin-left: 1rem;
  text-align: left;
  text-transform: capitalize;
  font-size: x-large;
}

.registerContainer {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin-bottom: 1rem;
  margin-top: 1rem;
  padding: 1rem;
}

.registerFormBox {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  min-height: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  width: 100%;
  max-width: 50rem;
  border-color: var(--dark-grey);
  border-radius: 5px 5px 5px 5px;
  border-width: 2px;
  border-style: solid;
  background-color: var(--light-grey);
}

.halfSplitContainer {
  display: flex;
  flex-flow: row wrap;
  padding: 0;
  margin: 0;
  width: 100%;
  justify-content: space-between;

}

.group {
  flex: 1 auto;
  margin: 0.5rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
.bigGroup {
  flex: 3 auto;
  margin: 0.5rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
.smallGroup {
  flex: 1 1rem;
  margin: 0.5rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
.notRequired {
  background-color: var(--shadow-grey);
}


</style>