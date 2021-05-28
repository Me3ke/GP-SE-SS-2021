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
                                            <button type="button" class="light-btn" @click="page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setUp()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.continue') }}
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
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.login.continue') }}
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

<style scoped>

.button-txt {
    float: left;
}

.my-icon {
    fill: var(--elsa-blue);
    height: 1em;
    width: auto;
}

.my-icon:hover {
    cursor: pointer;
}

.responsive-img {
    height: 20em;
    width: auto;
}

.content-div, .step {
    text-align: left;
    margin-top: 1vh;
    margin-bottom: 1vh;
}

.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}

/* Modal */
.modal-mask {
    position: fixed;
    z-index: 1000000;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: var(--modalBackground);
    display: table;
    color: var(--dark-grey);
}

.modal-body {
    height: 100%;
    max-height: 100%;
    margin-top: 0;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-content {
    background-color: var(--whitesmoke);
    width: fit-content;
}

.modal-dialog {
    max-width: fit-content;
}

.modal-title {
    align-items: center;
    text-align: left;
}

.modal-header {
    border-color: var(--dark-grey);
    height: fit-content;
    justify-content: center;
    align-items: center;
}

/* Form */
.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border-color: var(--light-grey);
}

.form-control:focus {
    color: var(--dark-grey);
    background-color: var(--whitesmoke);
    border-color: var(--elsa-blue);
    outline: 0;
    box-shadow: 0 0 0 0.2rem var(--elsa-blue-transparent);
}

.form-control::placeholder {
    color: var(--shadow-grey);
    opacity: 1; /* Firefox */
}

.form-control:-ms-input-placeholder {
    color: var(--shadow-grey);
}

.form-control::-ms-input-placeholder {
    color: var(--shadow-grey);
}

/* Buttons */
.elsa-blue-btn, .light-btn {
    padding: 0.25em 1em 0;
    border: 0.03em solid var(--dark-grey);
    margin: 0.15em 0.15em;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}


/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h4 {
        font-size: 1.12em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: .92em;
    }

    .content-div {
        font-size: .62em;
    }

    .responsive-img {
        font-size: 0.7em;
    }

    .nav-item {
        font-size: 0.5em;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    h4 {
        font-size: 1.12em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: .92em;
    }

    .content-div {
        font-size: .62em;
    }

    .responsive-img {
        font-size: 0.6em;
    }

    .nav-item {
        font-size: 0.41em;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h4 {
        font-size: 1.12em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: .92em;
    }

    .content-div {
        font-size: .62em;
    }

    .responsive-img {
        font-size: 0.9em;
    }

    .nav-item {
        font-size: 0.41em;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h4 {
        font-size: 1.45em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: 1.25em;
    }

    .content-div {
        font-size: .95em;
    }

    .responsive-img {
        font-size: 0.9em;
    }

    .nav-item {
        font-size: 0.75em;
    }
}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    h4 {
        font-size: 1.4em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: 1.2em;
    }

    .content-div {
        font-size: .9em;
    }

    .responsive-img {
        font-size: 1em;
    }

    .nav-item {
        font-size: 0.75em;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    h4 {
        font-size: 1.5em;
        text-overflow-mode: ellipse;
    }

    .step, .button-txt {
        font-size: 1.3em;
    }

    .content-div {
        font-size: 1em;
    }


    .responsive-img {
        font-size: 1em;
    }

    .nav-item {
        font-size: 0.75em;
    }
}
</style>
