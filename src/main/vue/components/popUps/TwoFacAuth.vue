<template>
    <div>
        <transition>
            <div class="modal-mask">
                <div class="modal-wrapper">
                    <div class="modal-fade" role="dialog" id="exampleModalLong" tabindex="-1"
                         aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title" id="exampleModalLongTitle">
                                        {{ $t('TwoFakAuth.verify') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 0 -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.notDone') }}
                                        </div>

                                        <ul>
                                            <li v-if="!hasSetUp">
                                                <div class="content-div"
                                                     style="justify-content: left; text-align: left">
                                                    {{ $t('TwoFakAuth.sign.tfa') }}
                                                </div>
                                            </li>
                                        </ul>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="goToSettings">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.sign.toSettings') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 1 (two fac Auth) -->
                                    <div v-if="page === 1">

                                        <!-- Error Messages -->
                                        <b-alert :show="showTries" dismissible
                                                 @dismissed="showTries = false"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.sign.leftTries') }} {{ triesLeft }}
                                        </b-alert>

                                        <!-- Auth Prompt -->
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.code') }}
                                        </div>

                                        <div class="content-div">
                                            <b-form-input id="input-code"
                                                          v-model="code"
                                                          placeholder="Code"
                                                          trim>
                                            </b-form-input>
                                        </div>

                                        <b-alert :show="showAlertCode" dismissible
                                                 @dismissed="showAlertCode = false"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.login.fail') }}
                                        </b-alert>

                                        <!-- Buttons to switch pages -->
                                        <div style="text-align: right">
                                            <button v-if="beforeDoc" type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 3">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="twoFac()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (shows if user put in too many wrong codes) -->
                                    <div v-if="page === 2">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.logout') }}
                                        </div>

                                        <div class="step" style="margin-top: 0; text-align: center;">
                                            {{ logoutCounter }}
                                        </div>
                                    </div>

                                    <!-- Page 3 (leave?) -->
                                    <div v-if="page === 3">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.sure') }}
                                        </div>

                                        <!-- Buttons to switch pages/ close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page = pageBefore">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.back') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
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
import {mapGetters} from "vuex";

export default {
    name: "TwoFacAuth",
    props: {
        beforeDoc: {
            type: Boolean,
            default: true
        },
        advanced: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            page: 1,
            pageBefore: 0,
            code: '',
            showAlertCode: false,
            //TODO: add somewhere in store so user cannot just refresh the page
            triesLeft: 3,
            showTries: false,
            //TODO: add somewhere in store so user cannot just refresh the page
            logoutCounter: 10,
            startCountDown: false
        }
    },
    async created() {
        await this.$store.dispatch('twoFakAuth/fetchHasSetUp')
        // checks if signature is advanced (if prompt appears in font of doc or envelope containing doc with advanced signature),
        // if so checks if user has set up 2FakAuth
        if (this.advanced && this.beforeDoc) {
            if (!this.hasSetUp) {
                this.page = 0
            }
        }

        // if auth is not in front of doc and user has no set-up -> close modal
        if (!this.beforeDoc && !this.hasSetUp) {
            this.$emit('twoFacTrigger')
        }
    },
    methods: {
        // makes 2FakAuth verification
        async twoFac() {
            //checking syntax of code
            if (this.code.length === 6 && Number.isInteger(Number(this.code))) {
                this.showAlertCode = false

                await this.$store.dispatch('twoFakAuth/validateCode', {code: this.code})
                //checking correctness of code
                if (!this.correctInput) {
                    this.triesLeft--
                    // user used all his tries -> will get logged out
                    if (this.triesLeft < 1) {
                        this.page = 2
                        this.startCountDown = true
                    }
                    this.showTries = true
                } else {
                    // emits event to close pop-up
                    this.showTries = false
                    this.showAlertCode = false
                    this.$emit('twoFacTrigger')
                }
            } else {
                this.showAlertCode = true
            }
        },
        // send user to settings page
        goToSettings() {
            this.$emit('closeTrigger');
            this.page = 1
            this.$router.push('/' + this.$i18n.locale + '/user')
        },
        // closes the pop up
        closeModal() {
            this.$emit('closeTrigger');
            this.page = 1
            this.showAlertSign = false
        }
    },
    computed: {
        ...mapGetters({
            correctInput: 'twoFakAuth/getCorrectInput',
            hasSetUp: 'twoFakAuth/getHasSetUp',

        }),
    },
    // watch methods taken from: https://stackoverflow.com/questions/55773602/how-do-i-create-a-simple-10-seconds-countdown-in-vue-js
    watch: {
        startCountDown(value) {
            if (value) {
                setTimeout(() => {
                    this.logoutCounter--;
                }, 1000);
            }
        },
        logoutCounter: {
            handler(value) {
                if (value > 0 && this.startCountDown) {
                    setTimeout(() => {
                        this.logoutCounter--;
                    }, 1000);
                }

                if (value === 0) {
                    localStorage.removeItem('store')
                    localStorage.clear()
                    this.$router.push('/' + this.$i18n.locale + '/landing')
                }

            }
        },
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
