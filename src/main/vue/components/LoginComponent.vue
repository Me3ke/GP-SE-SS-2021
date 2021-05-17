<template>
    <b-card id="login-form" v-bind:header="$t('Login.login')">
        <b-alert :show="showDismissibleAlert" variant="danger" dismissible>
            {{$t('Login.fail')}}
        </b-alert>
        <b-form @submit.prevent="onSubmit">
            <b-form-group
                id="input-group-username"
                v-bind:label="$t('Login.email')"
            >
                <b-form-input
                    id="input-username"
                    v-model="auth.username"
                    type="email"
                    v-bind:placeholder="$t('Login.email')"
                    required
                    :state="validateState('username')"
                    aria-describedby="input-username-live-feedback"
                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-username-live-feedback"
                >
                    {{$t('Login.feedback-invalid-email')}}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group
                id="input-group-password"
                v-bind:label="$t('Login.password')"
            >
                <b-form-input
                    id="input-password"
                    v-model="auth.password"
                    type="password"
                    v-bind:placeholder="$t('Login.password')"
                    required
                    :state="validateState('password')"
                    aria-describedby="input-password-live-feedback"

                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-password-live-feedback"
                >
                    {{$t('Login.feedback-invalid-password')}}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-button type="submit">
                {{$t('Login.sign-in')}}
            </b-button>
        </b-form>
    </b-card>
</template>




<script>
import { validationMixin } from "vuelidate";
import { required, email, minLength } from "vuelidate/lib/validators";
import {mapActions, mapGetters} from "vuex";

export default {
    mixins: [validationMixin],
    name: "LoginComponent",

    data() {
        return {
            auth: {
                username: null,
                password: null
            },
            showDismissibleAlert: false,
        };
    },

    validations: {
        auth: {
            username: {required, email},
            password: {required, min: minLength(4)}
        }
    },

    computed: {
        ...mapGetters({
            authenticated: 'isAuthenticated'
        })
    },

    methods: {
        validateState(name) {
            const { $dirty, $error } = this.$v.auth[name];
            return $dirty ? !$error : null;
        },
        showAlert() {
            this.showDismissibleAlert = true
        }
        ,
        onSubmit() {
            this.$v.auth.$touch();
            if (this.$v.auth.$anyError) {
                return;
            }
            this.login()
        },
        ...mapActions(['requestToken']),
        login() {
            this.requestToken(this.auth).then(() => this.$router.push('/'+this.$i18n.locale + '/overview'))
            this.showAlert()
        }
    }

};
</script>




<style scoped>

#login-form{
    width: 100%;
    max-width: 400px;
}

</style>
