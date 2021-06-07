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
                                    <h4 style="color: var(--dark-grey)" class="modal-title" id="exampleModalLongTitle">
                                        {{ $t('KeypairAlert.title') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 0 (shows if user has already set up keypair) -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            {{ $t('KeypairAlert.warningText') }}
                                        </div>

                                        <!-- Buttons to switch pages/ close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page++">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.continue') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.cancel') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 1 -->
                                    <div v-if="page === 1">

                                        <!-- Choose option prompt -->
                                        <div class="step">
                                            {{ $t('KeypairAlert.choose') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page = 2">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.upload') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="generate">
                                                <span class="button-txt">
                                                  {{ $t('KeypairAlert.generate') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (uploading of keypair) -->
                                    <div v-if="page === 2">

                                        <!-- Alert that shows if sending public key to backend was unsuccessful -->
                                        <b-alert :show="showSendingAlert"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Upload prompt -->
                                        <div class="step">
                                            {{ $t('KeypairAlert.uploadHere') }}
                                            <b-icon id="tooltip-pk" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-pk" triggers="hover">
                                                {{ $t('KeypairAlert.uploadHereExp') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="display: flex; justify-content: center">
                                            <div class="content-div" style="width: 25em;">
                                                <b-form-file
                                                    v-model="key"
                                                    :state="Boolean(key)"
                                                    :placeholder="$t('KeypairAlert.placeholder')"
                                                    :drop-placeholder="$t('KeypairAlert.dropPlaceholder')"
                                                    accept="text/*"
                                                ></b-form-file>
                                            </div>
                                        </div>

                                        <!-- Buttons to upload/cancel -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="upload">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.upload') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 3 (uploading keypair was successful) -->
                                    <div v-if="page === 3">
                                        <!-- Success prompt -->
                                        <div class="step" style="display: flex; justify-content: center">
                                            {{ $t('KeypairAlert.success') }}
                                        </div>
                                        <div class="content-div" style="display: flex; justify-content: center">
                                            {{ $t('KeypairAlert.successWarning') }}
                                        </div>

                                        <!-- Button to close -->
                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 4 (generating keypair) -->
                                    <div v-if="page === 4">

                                        <!-- Alert that shows if sending public key to backend was unsuccessful -->
                                        <b-alert :show="showSendingAlert"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Private Key prompt -->
                                        <div class="step" style="display: flex; justify-content: center">
                                            {{ $t('KeypairAlert.privateKey') }}
                                        </div>
                                        <div class="content-div" style="display: flex; justify-content: center">
                                            {{ $t('KeypairAlert.noLose') }}
                                        </div>
                                        <div v-if="generating" class="step"
                                             style="display: flex; justify-content: center">
                                            {{ $t('KeypairAlert.generatingText') }}
                                            <b-spinner></b-spinner>
                                        </div>
                                        <div v-else class="content-div" style="display: flex; justify-content: center">
                                            {{ privateKey }}
                                        </div>

                                        <!-- Button to close -->
                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 5 (shows when trying to abort) -->
                                    <div v-if="page === 5">

                                        <!-- Cancel Warning prompt -->
                                        <div class="step" style="margin-bottom: 0">
                                            {{ $t('KeypairAlert.cancelWarningOne') }}
                                        </div>
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('KeypairAlert.cancelWarningTwo') }}
                                        </div>

                                        <!-- Buttons to close/ go back -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page = pageBefore">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.back') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.cancel') }}
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
import _ from "lodash";

export default {
    name: "KeyPairSetUp",
    data() {
        return {
            page: 0,
            pageBefore: 0,

            key: null,
            showSendingAlert: false,
            generating: true
        }
    },
    async created() {
        // checking if user has a keypair already
        await this.$store.dispatch('fetchHasKey')

        // if he does not have a keypair -> do not show warning
        if (!this.hasKey) {
            this.page = 1
        }
    },
    methods: {
        // uploads given public key to server
        upload() {
            // gets key out of file and send it to server
            if (this.key) {
                const reader = new FileReader()
                reader.readAsText(this.key)
                reader.onload = (keyInfo) => {
                    this.sendKey(keyInfo.target.result)
                }
            }

            // changes page if no error occurred
            if (!this.hasSendingError()) {
                this.page = 3
            }
        },
        // generates keypair, saves public key in backend, shows private key to user
        generate() {
            // go to generating page
            this.page = 4
            // waits one second before executing function so page switches and loading message is displayed
            setTimeout(() => {
                // call to generate keypair
                this.$store.dispatch('callToGenerate')
                this.generating = false
                this.sendKey(this.publicKey)
            }, 1000);
        },
        // sends given public key to server
        async sendKey(key) {
            await this.$store.dispatch('sendPublicKey', {publicKey: key})
            // if sending did not work correctly sets alert
            if (this.hasSendingError()) {
                this.showSendingAlert = true
            }
        },
        // checks if public key got send to server correctly
        hasSendingError() {
            return !_.isEmpty(this.sendingSuccess);
        },
        closeModal() {
            this.$emit('keyPairTrigger');
            this.page = 0
        }
    },
    computed: {
        ...mapGetters({
            hasKey: 'getHasKey',
            privateKey: 'getPrivateKey',
            publicKey: 'getPublicKey',
            sendingSuccess: 'getSendingSuccess'
        })
    },
    watch: {
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
