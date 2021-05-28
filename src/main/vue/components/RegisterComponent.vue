<template>
    <b-card id="register-form" v-bind:header="$t('Register.register')"
            header-class="headerText" style="background-color: var(--whitesmoke); border-color: var(--dark-grey)">
        <b-form @submit.prevent="onSubmit">
            <b-form-group
                id="input-group-firstname"
                v-bind:label="$t('Register.firstname')"
                label-align="left"
                label-size="lg"
            >
                <b-form-input
                    id="input-firstname"
                    v-model="register.firstname"
                    type="name"
                    v-bind:placeholder="$t('Register.firstname')"
                    required
                    aria-describedby="input-firstname-live-feedback"
                    class="input"
                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-firstname-live-feedback"
                >
                    {{ $t('Register.feedback-invalid-name') }}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group
                id="input-group-surname"
                v-bind:label="$t('Register.surname')"
                label-align="left"
                label-size="lg"
            >
                <b-form-input
                    id="input-surname"
                    v-model="register.surname"
                    type="name"
                    v-bind:placeholder="$t('Register.surname')"
                    required
                    aria-describedby="input-surname-live-feedback"
                    class="input"
                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-surname-live-feedback"
                >
                    {{ $t('Register.feedback-invalid-name') }}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group
                id="input-group-username"
                v-bind:label="$t('Register.email')"
                label-align="left"
                label-size="lg"
            >
                <b-form-input
                    id="input-username"
                    v-model="register.username"
                    type="email"
                    v-bind:placeholder="$t('Register.email')"
                    required
                    aria-describedby="input-username-live-feedback"
                    class="input"
                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-username-live-feedback"
                >
                    {{ $t('Register.feedback-invalid-email') }}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group
                id="input-group-password"
                v-bind:label="$t('Register.password')"
                label-align="left"
                label-size="lg"
            >
                <b-form-input
                    id="input-password"
                    v-model="register.password"
                    type="password"
                    v-bind:placeholder="$t('Register.password')"
                    required
                    aria-describedby="input-password-live-feedback"

                ></b-form-input>
                <b-form-invalid-feedback
                    id="input-password-live-feedback"
                >
                    {{ $t('Register.feedback-invalid-password') }}
                </b-form-invalid-feedback>
            </b-form-group>
            <b-button class="dark-btn" type="submit">
                {{ $t('Register.sign-up') }}
            </b-button>
        </b-form>
    </b-card>
</template>


<script>
import {validationMixin} from "vuelidate";
import {required, email, minLength} from "vuelidate/lib/validators";
import {mapActions, mapGetters} from "vuex";

export default {
    mixins: [validationMixin],
    name: "RegisterComponent",

    data() {
        return {
            register: {
                firstname: null,
                surname: null,
                username: null,
                password: null
            }
        };
    },

    validations: {
        firstname: {required, minLength: minLength(2)},
        surname: {required, minLength: minLength(2)},
        username: {required, email},
        password: {required, minLength: minLength(4)}
    },

    computed: {
        ...mapGetters({
            authenticated: 'isAuthenticated'
        })
    },

    methods: {
        validateState(name) {
            const {$dirty, $error} = this.$v.auth[name];
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
        async login() {
            await this.requestToken(this.auth)
                .then(() => {
                    this.$router.push('/' + this.$i18n.locale + '/overview')
                })
                .catch(() => {
                    this.showAlert()
                })
        }
    }

};
</script>


<style scoped>

#register-form {
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
