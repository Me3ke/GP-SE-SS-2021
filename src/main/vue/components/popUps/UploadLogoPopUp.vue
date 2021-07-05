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
                                                {{ $t('TwoFakAuth.serverErrorTwoAdmin') }}
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
import {convertUploadFileToBase64} from "@/main/vue/scripts/fileToBase64Converter";
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "UploadLogoPopUp",
    props: {
        exp: {
            type: Boolean,
            default: false
        },
        type: {
            type: [Number, String]
        }
    },
    data() {
        return {
            page: 1,

            logo: null,
            showSendingAlert: false
        }
    },
    methods: {
        // uploads given logo-image to server
        async upload() {
            // if now logo has been submitted, do not continue
            if (this.logo === null) {
                return
            }

            // getting type of image
            const type = this.logo.name.split('.')[1];
            // getting content out of image
            const content = await convertUploadFileToBase64(this.logo)

            // checking if logo is for dark mode and if in exp-mode
            if (this.exp && this.type === 1) {
                // sending logo to server
                await this.$store.dispatch('theme/putLogos', {
                    logo: content,
                    logoType: type,
                    dark: true
                })
            } else if (this.exp && this.type === 0) {
                // sending logo to server
                await this.$store.dispatch('theme/putLogos', {
                    logo: content,
                    logoType: type,
                    dark: false
                })
            } else {
                // sending logo to server
                await this.$store.dispatch('theme/putLogos', {
                    logo: content,
                    logoType: type,
                    dark: false
                })
                // sending logo to server
                await this.$store.dispatch('theme/putLogos', {
                    logo: content,
                    logoType: type,
                    dark: true
                })
            }

            // changes page if no error occurred, otherwise shows warning
            if (!this.hasSendingError()) {
                this.page = 2
            } else {
                this.showSendingAlert = true
            }
        },
        // checks if logo got send to server correctly
        hasSendingError() {
            return !_.isEmpty(this.uploadError)
        },
        closeModal() {
            this.$emit('logoTrigger');
            this.page = 1
        }
    },
    computed: {
        ...mapGetters({
            uploadError: 'theme/getErrorPutLogoResponse'
        })
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

