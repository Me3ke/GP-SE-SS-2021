<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.SecuritySettings.settings') }}
                    </h4>
                </div>
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span>
                                    {{ $t('Settings.SecuritySettings.publicKey') }} {{ this.userData.publicKey }}
                                </span>
                        <b-button class="light-btn" @click="keySetUp">
                            {{ $t('Settings.SecuritySettings.newKeypair') }}
                        </b-button>
                    </b-list-group-item>
                    <KeyPairSetUp v-if="showKey" @keyPairTrigger="keySetUp"></KeyPairSetUp>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.SecuritySettings.twoFacAuthSetUp') }}
                        </span>

                        <b-button class="light-btn" @click="setUp()">
                            {{ $t('Settings.SecuritySettings.setUp') }}
                        </b-button>
                    </b-list-group-item>
                    <TwoFakAuthSetUp v-if="showSetUp" @modalTrigger="setUp"></TwoFakAuthSetUp>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('TwoFakAuth.login.always') }}
                             <b-icon id="tooltip-security" icon="info-circle" class="my-icon"></b-icon>
                             <b-tooltip target="tooltip-security" triggers="hover">
                                {{ $t('TwoFakAuth.login.alwaysExp') }}
                            </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="loginActive" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-end align-items-center">

                        <transition name="saved">
                            <span v-if="showSave" class="content-div">
                                {{ $t('Settings.saved') }}
                            </span>
                        </transition>

                        <b-button class="elsa-blue-btn"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                                  @click="saveTwoFacLogin">
                            {{ $t('Settings.MessageSettings.send') }}
                        </b-button>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import TwoFakAuthSetUp from "@/main/vue/components/popUps/TwoFakAuthSetUp";
import KeyPairSetUp from "@/main/vue/components/popUps/KeyPairSetUp";
import {mapGetters} from "vuex";

export default {
    name: "SecuritySettingsBox",
    components: {
        KeyPairSetUp,
        TwoFakAuthSetUp
    },
    props: {
        user: Object,
        userData: Object
    },
    data() {
        return {
            showKey: false,
            showSetUp: false,
            showSave: false,

            loginActive: false
        }
    },
    async created() {
        await this.$store.dispatch('getTwoFactorLogin')
        this.loginActive = this.twoFacLogin
    },
    async mounted() {
        await this.$store.dispatch('getTwoFactorLogin')
        this.loginActive = this.twoFacLogin
    },
    methods: {
        keySetUp() {
            this.showKey = !this.showKey
            this.$emit('modalTrigger')
        },
        setUp() {
            this.showSetUp = !this.showSetUp
            this.$emit('modalTrigger')
        },
        // saves setting if two-factor authentication should always be shown at login
        async saveTwoFacLogin() {
            // checking if setting even changed
            if (this.loginActive !== this.twoFacLogin) {
                await this.$store.dispatch('putTwoFactorLogin', {setting: this.loginActive})
                await this.$store.dispatch('getTwoFactorLogin')
                this.loginActive = this.twoFacLogin

                // show saved notification
                this.showSave = true
                setTimeout(() => {
                    this.showSave = false
                }, 2000);
            }
        }
    },
    computed: {
        ...mapGetters({
            twoFacLogin: 'getTwoFactorLogin'
        })
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
