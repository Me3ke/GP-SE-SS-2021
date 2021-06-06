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
                                    <b-navbar>
                                        <b-nav-text style="color: var(--dark-grey)">
                                            <h4 class="modal-title" id="exampleModalLongTitle">
                                                {{ $t('TwoFakAuth.login.title') }}
                                            </h4>
                                        </b-nav-text>
                                        <b-navbar-nav style="margin-left: 2em">
                                            <LanguageSwitcher></LanguageSwitcher>
                                        </b-navbar-nav>
                                    </b-navbar>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 0 (shows if user has already set up 2 fac auth) -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.login.attention') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page++">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <b-alert :show="showErrorQr || showErrorSetUp || showErrorInput"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>
                                        <div class="step">
                                            {{ $t('TwoFakAuth.login.firstStep') }}
                                        </div>
                                        <div class="content-div" style="text-align: center">
                                            <img id="qrCode" style="margin-bottom: 1em"
                                                 :src="'data:image/png;base64,' + qr.data" class="responsive-img"
                                                 alt="qrCode">
                                        </div>

                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.login.secondStep') }}
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

                                        <div class="content-div" style="margin-bottom: 0">
                                            <b-icon class="my-icon" v-if="!always" icon="square"
                                                    @click="always = !always">
                                            </b-icon>
                                            <b-icon class="my-icon" v-else icon="check-square"
                                                    @click="always = !always">
                                            </b-icon>
                                            <span>
                                             {{ $t('TwoFakAuth.login.always') }}
                                             </span>
                                            <b-icon id="tooltip-target-1" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-target-1" triggers="hover">
                                                {{ $t('TwoFakAuth.login.alwaysExp') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUp()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (successfully set up second factor) -->
                                    <div v-if="page === 2">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.login.didIt') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.go') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 3 (setting up second factor was not successful) -->
                                    <div v-if="page === 3">
                                        <div class="step" style="text-align: center">
                                            {{ $t('TwoFakAuth.login.failedSetUp') }}
                                        </div>
                                        <div class="content-div" style="text-align: center">
                                            {{ $t('TwoFakAuth.login.tryAgain') }}
                                        </div>

                                        <b-alert :show="showErrorInput"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

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
                                                    @click="pageBefore = page; page = 4; showAlert = false">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="light-btn"
                                                    @click="startOver">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.toStart') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUp">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.again') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 4 (shows when trying to abort) -->
                                    <div v-if="page === 4">
                                        <div class="step" style="margin-bottom: 0">
                                            {{ $t('TwoFakAuth.login.cancelWarningOne') }}
                                        </div>
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.login.cancelWarningTwo') }}
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
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import {mapGetters} from 'vuex';
import _ from "lodash";

export default {
    name: "TwoFakAuthSetUp",
    components: {LanguageSwitcher},
    data() {
        return {
            showAlert: false,
            page: 0,
            pageBefore: 0,
            code: '',
            // TODO: connect setting with API
            always: false
        }
    },
    created() {
        this.$store.dispatch('twoFakAuth/fetchHasSetUp')
        this.$store.dispatch('twoFakAuth/fetchQrCode')

        if (!this.hasSetUp) {
            this.page = 1
        }
    },
    methods: {
        async setUp() {
            // check if code is syntactically correct
            if ((this.code.length === 6 && Number.isInteger(Number(this.code)))) {

                // axios call to check if code is semantically correct
                await this.$store.dispatch('twoFakAuth/validateCode', {code: this.code}).then(() => {
                    this.code = ''
                    this.showAlert = false
                    if (this.correctInput) {
                        // TODO: add here always-config call
                        this.page = 2
                    }
                    // code is incorrect
                    else {
                        this.page = 3
                    }
                })
            } else {
                this.showAlert = true
            }
        },
        startOver() {
            this.$store.dispatch('twoFakAuth/fetchQrCode')
            this.page = 1
            this.showAlert = false
            this.code = ''
        },
        closeModal() {
            this.$emit('modalTrigger');
            this.page = 1
        }
    },
    computed: {
        ...mapGetters({
            qr: 'twoFakAuth/getQrCode',
            hasSetUp: 'twoFakAuth/getHasSetUp',
            correctInput: 'twoFakAuth/getCorrectInput',
            errorQr: 'twoFakAuth/getErrorGetQrCode',
            errorSetUp: 'twoFakAuth/getErrorGetHasSetUp',
            errorInput: 'twoFakAuth/getErrorGetCorrectInput'
        }),
        showErrorQr: {
            get() {
                return !_.isEmpty(this.errorQr)
            }
        },
        showErrorSetUp: {
            get() {
                return !_.isEmpty(this.errorSetUp)
            }
        },
        showErrorInput: {
            get() {
                return !_.isEmpty(this.errorInput)
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
