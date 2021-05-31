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
                                        {{ $t('TwoFakAuth.sign.sign') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 1 (advanced)-->
                                    <div v-if="page === 1 && advanced">
                                        <div class="step" v-if="documents.length === 1">
                                            {{ $t('TwoFakAuth.sign.sureOne') }}
                                        </div>

                                        <div class="step" v-else>
                                            {{ $t('TwoFakAuth.sign.sureMulti') }}
                                        </div>

                                        <div class="content-div">
                                            <ul>
                                                <li v-for="(document, idx) in documents" :key="idx">
                                                    {{ document.title }}
                                                </li>
                                            </ul>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page = 2">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>


                                    <!-- Page 1 (simple) -->
                                    <div v-if="page === 1 && !advanced">
                                        <div class="step" v-if="documents.length === 1">
                                            {{ $t('TwoFakAuth.sign.sureOne') }}
                                        </div>

                                        <div class="step" v-else>
                                            {{ $t('TwoFakAuth.sign.sureMulti') }}
                                        </div>

                                        <div class="content-div">
                                            <ul>
                                                <li v-for="(document, idx) in documents" :key="idx">
                                                    {{ document.title }}
                                                </li>
                                            </ul>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>


                                    <!-- Page 2 (two fac Auth) -->
                                    <div v-if="page === 2">
                                        <b-alert :show="showTries" dismissible
                                                 @dismissed="showTries = false"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.sign.leftTries') }} {{ triesLeft }}
                                        </b-alert>

                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.code') }}
                                        </div>

                                        <div class="content-div">
                                            <b-form-input id="input-code"
                                                          v-model="code"
                                                          placeholder="Code"
                                                          trim
                                                          style="margin-bottom: 1em">
                                            </b-form-input>
                                            <b-alert :show="showAlert" dismissible
                                                     @dismissed="showAlert = false"
                                                     style="margin-bottom: 1em">
                                                {{ $t('TwoFakAuth.login.fail') }}
                                            </b-alert>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
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

                                    <!-- Page 3 (key stuff) -->
                                    <div v-if="page === 3">
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="checkKey()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 4 (success) -->
                                    <div v-if="page === 4">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.success') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 5 (leave?) -->
                                    <div v-if="page === 5">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.sure') }}
                                        </div>

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

                                    <!-- Page 6 (shows if user put in too many wrong codes) -->
                                    <div v-if="page === 6">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.logout') }}
                                        </div>

                                        <div class="step" style="margin-top: 0; text-align: center;">
                                            {{ logoutCounter }}
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
export default {
    name: "SignPopUp",
    props: {
        documents: {
            type: Array,
            required: true
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
            showAlert: false,
            code: '',
            //TODO: add somewhere in store so user cannot just refresh the page
            triesLeft: 3,
            showTries: false,
            //TODO: remove once code correctness gets checked with api
            codeCorrect: false,
            //TODO: add somewhere in store so user cannot just refresh the page
            logoutCounter: 10,
            startCountDown: false
        }
    },
    methods: {
        twoFac() {
            //checking syntax of code
            if (this.code.length === 6 && Number.isInteger(Number(this.code))) {
                this.showAlert = false
                // TODO: check with API if code is correct
                //checking correctness of code
                if (!this.codeCorrect) {
                    this.triesLeft--
                    // user used all his tries -> will get logged out
                    if (this.triesLeft < 1) {
                        this.page = 6
                        this.startCountDown = true
                    }
                    this.showTries = true
                } else {
                    this.page = this.page + 1
                }
            } else {
                this.showAlert = true
            }
        },
        // TODO: check with API if key step was successful
        checkKey() {
            this.page += 1
        },
        closeModal() {
            this.$emit('advancedTrigger');
            this.page = 1
        }
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
                    this.$router.push('/' + this.$i18n.locale + '/login')
                }

            }
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
