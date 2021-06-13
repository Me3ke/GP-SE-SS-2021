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
                                        {{ $t('AdminSettings.corporate.logoTitle') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 1 (uploading of logo) -->
                                    <div v-if="page === 1">

                                        <!-- Alert that shows if uploading logo to backend was unsuccessful -->
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
                                            {{ $t('AdminSettings.corporate.logoUploadHere') }}
                                            <b-icon id="tooltip-logo" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-logo" triggers="hover">
                                                {{ $t('AdminSettings.corporate.logoUploadHereExp') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="display: flex; justify-content: center">
                                            <div class="content-div" style="width: 25em;">
                                                <b-form-file
                                                    v-model="logo"
                                                    :state="Boolean(logo)"
                                                    :placeholder=" $t('AdminSettings.corporate.logoPlaceholder')"
                                                    :drop-placeholder=" $t('AdminSettings.corporate.dropPlaceholder')"
                                                    required
                                                    accept="image/*"
                                                ></b-form-file>
                                            </div>
                                        </div>

                                        <!-- Buttons to upload/cancel -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="closeModal()">
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

                                    <!-- Page 2 (uploading success) -->
                                    <div v-if="page === 2">
                                        <!-- Success prompt -->
                                        <div class="step" style="display: flex; justify-content: center">
                                            {{ $t('AdminSettings.corporate.logoSuccess') }}
                                        </div>

                                        <!-- Button to close -->
                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal(logo)">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.close') }}
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
export default {
    name: "UploadLogoPopUp",
    data() {
        return {
            page: 1,

            logo: null,
            showSendingAlert: false
        }
    },
    methods: {
        // uploads given public key to server
        upload() {
            // if now logo has been submitted, do not continue
            if (this.logo === null) {
                return
            }
            // gets key out of file and send it to server
            if (this.logo) {
                const reader = new FileReader()
                reader.readAsText(this.logo)
                reader.onload = (logoInfo) => {
                    this.sendLogo(logoInfo.target.result)
                }
            }

            // changes page if no error occurred
            if (!this.hasSendingError()) {
                this.page = 2
            }
        },
        // sends given logo to server
        async sendLogo(logo) {

            //TODO: add API call
            console.log(logo)

            // if sending did not work correctly sets alert
            if (this.hasSendingError()) {
                this.showSendingAlert = true
            }
        },
        // checks if logo got send to server correctly
        hasSendingError() {
            //TODO: add API call
        },
        closeModal() {
            this.$emit('logoTrigger');
            this.page = 1
        }
    },
    watch: {
        // checks if input file has correct type
        logo(newLogo) {
            if (newLogo && !newLogo.type.startsWith("image/")) {
                this.$nextTick(() => {
                    this.logo = null;
                })
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>

