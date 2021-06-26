<template>
    <div>
      <b-modal id="success" centered title="Success">
        <p> success </p>
      </b-modal>
      <b-modal id="fail" centered
               title="fail"
      >
        <p> fail </p>
      </b-modal>
      <b-card id="login-form" v-bind:header="$t('Login.resetPassword')"
              header-class="headerText" style="background-color: var(--whitesmoke); border-color: var(--dark-grey)">
        <b-alert :show="showDismissibleAlert" variant="danger" dismissible>
          {{ $t('Login.failMail') }}
        </b-alert>
        {{ $t('Login.reset') }}
        <b-form @submit.prevent="onSubmit">
          <b-form-group
              id="input-group-username"
              v-bind:label="$t('Login.email')"
              label-align="left"
              label-size="lg"
          >
            <b-form-input
                id="input-username"
                v-model="reset.username"
                type="email"
                v-bind:placeholder="$t('Login.email')"
                required
                aria-describedby="input-username-live-feedback"
                class="input"
            ></b-form-input>
            <b-form-invalid-feedback
                id="input-username-live-feedback"
            >
              {{ $t('Login.feedback-invalid-email') }}
            </b-form-invalid-feedback>
          </b-form-group>
          <b-button class="dark-btn" type="submit">
            {{ $t('Login.send') }}
          </b-button>
        </b-form>
      </b-card>
    </div>
</template>


<script>
import {validationMixin} from "vuelidate";
import {required, email} from "vuelidate/lib/validators";
import userAPI from "../../api/userAPI";

export default {
    mixins: [validationMixin],
    name: "ForgotPasswordComponent",

    data() {
        return {
            reset: {
                username: null
            },
            showDismissibleAlert: false,
        };
    },

    validations: {
        reset: {
            username: {required, email}
        }
    },


    methods: {
      routeToRegister(){
        this.$router.push('/'+this.$i18n.locale + '/register')
      },
        validateState(name) {
            const {$dirty, $error} = this.$v.reset[name];
            return $dirty ? !$error : null;
        },
        showAlert() {
            this.showDismissibleAlert = true
        }
        ,
        onSubmit() {
            this.$v.reset.$touch();
            if (this.$v.reset.$anyError) {
                return;
            }
            this.resetPassword()
        },
      async resetPassword() {
        await userAPI.resetPassword(this.reset.username)
            .then(response => {
              if (response.status == 200) {
                alert("Check your mails")
              } else {
                this.$refs["modal-success"].show()
              }
            })
            .catch(()=> {
              this.$bvModal.show("fail")

            })
      }
    }

};
</script>


<style scoped>

#login-form {
    width: 100%;
    max-width: 400px;

}

.card-header {
    background-color: var(--header-login);
}

#left-child {
    float: left;
}

input::placeholder {
    color: var(--light-grey);
}

.headerText {
    float: left;
    text-align: left;
    font-size: x-large;
    text-transform: capitalize;
}

</style>
