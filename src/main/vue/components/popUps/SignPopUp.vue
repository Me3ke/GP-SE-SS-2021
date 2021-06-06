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

                                    <!-- Page 0 -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.notDone') }}
                                        </div>

                                        <ul>
                                            <li>
                                                <div class="content-div" v-if="!hasSetUp">
                                                    {{ $t('TwoFakAuth.sign.tfa') }}
                                                </div>
                                            </li>
                                            <li>
                                                <!-- TODO: change to hasKey -->
                                                <div class="content-div" v-if="!hasSetUp">
                                                    {{ $t('TwoFakAuth.sign.keyPair') }}
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

                                        <!-- Error Messages -->
                                        <b-alert :show="showAlertSign"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.fail') }} {{ statusCodeSimple }}
                                        </b-alert>


                                        <b-alert :show="showErrorSimple"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Sign Prompt -->
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

                                        <!-- Buttons to switch pages -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="signSimple">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>


                                    <!-- Page 2 (two fac Auth) -->
                                    <div v-if="page === 2">

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
                                                          trim
                                                          style="margin-bottom: 1em">
                                            </b-form-input>
                                            <b-alert :show="showAlertCode" dismissible
                                                     @dismissed="showAlertCode = false"
                                                     style="margin-bottom: 1em">
                                                {{ $t('TwoFakAuth.login.fail') }}
                                            </b-alert>
                                        </div>

                                        <!-- Buttons to switch pages -->
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
                                        <!-- Error Messages -->
                                        <b-alert :show="showAlertSign"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.fail') }} {{ statusCodeAdvanced }}
                                        </b-alert>


                                        <b-alert :show="showErrorSimple"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Input Key Prompt -->
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.uploadExp') }}
                                            <b-icon id="tooltip-upl" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-upl" triggers="hover">
                                                {{ $t('TwoFakAuth.sign.accepted') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="display: flex; justify-content: center">
                                            <div class="content-div" style="width: 25em;">
                                                <b-form-file
                                                    v-model="key"
                                                    :state="Boolean(key)"
                                                    :placeholder="$t('TwoFakAuth.sign.placeholder')"
                                                    :drop-placeholder="$t('TwoFakAuth.sign.dropPlaceholder')"
                                                    accept="text/*"
                                                ></b-form-file>
                                            </div>
                                        </div>

                                        <!-- Buttons to switch pages -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="signAdvanced">
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

                                        <!-- Button to close -->
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
// TODO: rethink way pop up gets information about documents once it is possible to sign multiple files at at time
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "SignPopUp",
    props: {
        documents: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            page: 1,
            pageBefore: 0,

            code: '',
            key: null,

            showAlertCode: false,
            showAlertSign: false,

            //TODO: add somewhere in store so user cannot just refresh the page
            triesLeft: 3,
            showTries: false,

            //TODO: add somewhere in store so user cannot just refresh the page
            logoutCounter: 10,
            startCountDown: false
        }
    },
    async created() {
        // checks if signature is advanced, if so checks if user has set up 2FakAuth and key
        // TODO: connect to api to check for key
        if (this.advanced) {
            await this.$store.dispatch('twoFakAuth/fetchHasSetUp')
            if (!this.hasSetUp) {
                this.page = 0
            }
        }
    },
    methods: {
        // makes simple signature api call
        async signSimple() {
            await this.$store.dispatch('document/simpleSignDocument', {docId: this.docId})

            // everything went fine
            if (this.statusCodeSimple === 200) {
                // reloading document in store, so information is coherent with server information
                await this.$store.dispatch('document/fetchDocument', {envId: this.envId, docId: this.docId})
                // goes to success page and toggles alert
                this.page = 4
                this.showAlertSign = false
            } else {
                // if api call got to server, but server did not response wit 'ok' shows errorCode to user
                // if api call did not go to server, shows that there are Server Network problems
                if (!this.showErrorSimple) {
                    this.showAlertSign = true
                }
            }
        },
        // makes 2FakAuth verification needed for advanced signature
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
                        this.page = 6
                        this.startCountDown = true
                    }
                    this.showTries = true
                } else {
                    // goes to key page
                    this.page = 3
                    this.showTries = false
                    this.showAlertCode = false
                }
            } else {
                this.showAlertCode = true
            }
        },
        // checks of user owns correct private-public-keypair
        async signAdvanced() {

            // gets data out of user file that contains key
            const reader = new FileReader()
            reader.readAsText(this.key)

            reader.onload = async (keyData) => {
                await this.$store.dispatch('document/advancedSignDocument', {
                    docId: this.docId,
                    signature: keyData.target.result
                })
            }

            // everything went fine
            if (this.statusCodeAdvanced === 200) {
                // reloading document in store, so information is coherent with server information
                await this.$store.dispatch('document/fetchDocument', {envId: this.envId, docId: this.docId})
                // goes to success page and toggles alert
                this.page = 4
                this.showAlertSign = false
            } else {
                // if api call got to server, but server did not response wit 'ok' shows errorCode to user
                // if api call did not go to server, shows that there are Server Network problems
                if (!this.showErrorAdvanced) {
                    this.showAlertSign = true
                }
            }
        },
        // send user to settings page
        goToSettings() {
            this.$emit('signTrigger');
            this.page = 1
            this.$router.push('/' + this.$i18n.locale + '/user')
        },
        // closes the pop up
        closeModal() {
            this.$emit('signTrigger');
            this.page = 1
        }
    },
    computed: {
        ...mapGetters({
            statusCodeSimple: 'document/getSimpleSignStatus',
            errorSimple: 'document/getErrorSimpleSignDocument',
            statusCodeAdvanced: 'document/getAdvancedSignStatus',
            errorAdvanced: 'document/getErrorAdvancedSignDocument',

            correctInput: 'twoFakAuth/getCorrectInput',
            hasSetUp: 'twoFakAuth/getHasSetUp'
        }),
        // TODO
        // gives back if advanced signature is needed (if false -> simple signature is needed)
        advanced() {
            //  return this.documents[0].signatureType === ''
            return true
        },
        showErrorSimple: {
            get() {
                return !_.isEmpty(this.errorSimple)
            }
        },
        showErrorAdvanced: {
            get() {
                return !_.isEmpty(this.errorAdvanced)
            }
        },
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
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
        },
        // checks if input file has correct type
        key(newKey) {
            if (newKey && !newKey.type.startsWith("text/")) {
                this.$nextTick(() => {
                    this.key = null;
                })
            }
        }
    }

}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
