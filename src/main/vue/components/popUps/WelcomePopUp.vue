<template>
    <div>

        <TwoFakAuthSetUp v-if="show2Fak" @modalTrigger="setUpSecond"></TwoFakAuthSetUp>
        <KeyPairSetUp v-if="showKey" @keyPairTrigger="setUpKey"></KeyPairSetUp>
        <SignatureUploadPopUp v-if="showSignature" :has-signature="false"
                              @uploadTrigger="setUpSignature"></SignatureUploadPopUp>

        <transition v-if="showWelcome">
            <div class="modal-mask" v-if="showWelcome">
                <div class="modal-wrapper">
                    <div class="modal-fade" role="dialog" id="exampleModalLong" tabindex="-1"
                         aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable" role="document">
                            <div class="modal-content">
                                <div class="modal-header" style="justify-content: space-between">
                                    <b-img v-if="theme === '' " :src="logoLightMode" class="logo"
                                           :alt="$t('Header.logo')"></b-img>
                                    <b-img v-else :src="logoDarkMode" class="logo" :alt="$t('Header.logo')"></b-img>
                                    <!-- Progress -->
                                    <b-progress max="100" class="bar" show-progress show-value>
                                        <b-progress-bar
                                            :value="progress"
                                            :label="progress + '%'"
                                            variant="elsaBlue"
                                        ></b-progress-bar>
                                    </b-progress>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 1 (welcome) -->
                                    <div v-if="page === 1">

                                        <!-- welcome prompt -->
                                        <h4 class="big-heading">
                                            {{ $t('wizard.welcome.welcome') }}
                                        </h4>

                                        <div class="step">
                                            {{ $t('wizard.welcome.toDo') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 6">
                                                <span class="button-txt">
                                                   {{ $t('wizard.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page = 2">
                                                <span class="button-txt">
                                                 {{ $t('wizard.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (2FakAuth) -->
                                    <div v-if="page === 2">
                                        <!-- 2FakAuth set up prompt -->
                                        <h4 style="text-align: center">
                                            {{ $t('wizard.welcome.2FakAuth') }}
                                        </h4>

                                        <div class="step">
                                            {{ $t('wizard.welcome.2FakAuthExp') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 6">
                                                <span class="button-txt">
                                                   {{ $t('wizard.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUpSecond">
                                                <span class="button-txt">
                                                 {{ $t('wizard.welcome.setUp') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 3 (keypair) -->
                                    <div v-if="page === 3">
                                        <!-- 2FakAuth set up prompt -->
                                        <h4 style="text-align: center">
                                            {{ $t('wizard.welcome.keypair') }}
                                        </h4>

                                        <div class="step">
                                            {{ $t('wizard.welcome.keypairExp') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 6">
                                                <span class="button-txt">
                                                   {{ $t('wizard.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUpKey">
                                                <span class="button-txt">
                                                 {{ $t('wizard.welcome.setUp') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 4 (picture) -->
                                    <div v-if="page === 4">
                                        <!-- Signature image set up prompt -->
                                        <h4 style="text-align: center">
                                            {{ $t('wizard.welcome.signature') }}
                                        </h4>

                                        <div class="step">
                                            {{ $t('wizard.welcome.signatureExp') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 6">
                                                <span class="button-txt">
                                                   {{ $t('wizard.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5; progress = 100;">
                                                <span class="button-txt">
                                                   {{ $t('wizard.welcome.skip') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUpSignature">
                                                <span class="button-txt">
                                                 {{ $t('wizard.welcome.setUp') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 5 (finished) -->
                                    <div v-if="page === 5">
                                        <!-- 2FakAuth set up prompt -->
                                        <h4 class="big-heading">
                                            {{ $t('wizard.welcome.done') }}
                                        </h4>

                                        <div class="step">
                                            {{ $t('wizard.welcome.fun') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal">
                                                <span class="button-txt">
                                                 {{ $t('wizard.welcome.go') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 6 (abort warning) -->
                                    <div v-if="page === 6">

                                        <!-- Cancel Warning prompt -->
                                        <div class="step" style="margin-bottom: 0">
                                            {{ $t('wizard.welcome.cancelWarningOne') }}
                                        </div>
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('wizard.welcome.cancelWarningTwo') }}
                                        </div>

                                        <!-- Buttons to close/ go back -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page = pageBefore">
                                                <span class="button-txt">
                                                    {{ $t('wizard.back') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('wizard.cancel') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
import TwoFakAuthSetUp from "@/main/vue/components/popUps/TwoFakAuthSetUp";
import KeyPairSetUp from "@/main/vue/components/popUps/KeyPairSetUp";
import {mapGetters} from "vuex";
import SignatureUploadPopUp from "@/main/vue/components/popUps/SignatureUploadPopUp";

export default {
    name: "WelcomePopUp",
    components: {SignatureUploadPopUp, KeyPairSetUp, TwoFakAuthSetUp},
    data() {
        return {
            logoLightMode: require('../../assets/logos/ELSA_small.svg'),
            logoDarkMode: require('../../assets/logos/ELSA_small_darkmode.svg'),

            page: 1,
            pageBefore: 0,

            showWelcome: true,
            show2Fak: false,
            showKey: false,
            showSignature: false,

            progress: 0
        }
    },
    methods: {
        // shows 2FakAuth modal, toggles current modal
        setUpSecond() {
            // toggle everything
            this.show2Fak = !this.show2Fak
            this.showWelcome = !this.showWelcome
            // go to next step (keypair)
            this.page = 3
            this.progress = 33
        },
        // shows keypair modal, toggles current modal
        setUpKey() {
            // toggle everything
            this.showKey = !this.showKey
            this.showWelcome = !this.showWelcome
            // go to next step (signature image)
            this.page = 4
            this.progress = 66
        },
        // shows signature upload modal, toggles current modal
        setUpSignature() {
            // toggle everything
            this.showSignature = !this.showSignature
            this.showWelcome = !this.showWelcome
            // go to next step (done)
            this.page = 5
            this.progress = 100
        },
        closeModal() {
            // resetting everything
            this.showSignature = false
            this.showKey = false
            this.show2Fak = false
            this.showWelcome = true
            this.page = 1
            // telling parent container to toggle modal
            this.$emit('welcomeTrigger')
        }
    },
    computed: {
        ...mapGetters({
            theme: 'theme/getTheme'
        })
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
