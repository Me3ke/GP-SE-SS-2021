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
                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.login.firstStep') }}
                                        </div>
                                        <div class="content-div" style="text-align: center">
                                            <img style="margin-bottom: 1em" :src="qr" class="responsive-img"
                                                 alt="turtle">
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
                                            <button type="button" class="elsa-blue-btn">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.go') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- TODO: connect page when api says code was wrong -->
                                    <!-- Page 3 (setting up second factor was not successful) -->
                                    <div v-if="page === 3">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.login.failedSetUp') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="code = ''; page = 1">
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
                                                    {{ $t('TwoFakAuth.continue') }}
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

export default {
    name: "TwoFakAuthSetUp",
    components: {LanguageSwitcher},
    data() {
        return {
            showAlert: false,
            page: 1,
            pageBefore: 0,
            qr: require('../../assets/frame2.png'),
            code: '',
            always: false
        }
    },
    methods: {
        /* TODO; check if correct code, not only syntax, via axios*/
        setUp() {
            if (this.code.length === 6 && Number.isInteger(Number(this.code))) {
                this.page = this.page + 1
                this.showAlert = false
            } else {
                this.showAlert = true
            }
        },
        closeModal() {
            this.$emit('modalTrigger');
            this.page = 1
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
