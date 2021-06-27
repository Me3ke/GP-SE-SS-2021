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
                                        {{
                                            drawSignature ? $t('Settings.SignatureSettings.upload.popUp.heading2') : $t('Settings.SignatureSettings.upload.popUp.heading')
                                        }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 0 (shows if user has already set up signature) -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            <div>
                                                {{ $t('Settings.SignatureSettings.upload.popUp.attentionOne') }}
                                            </div>
                                            <div>
                                                {{ $t('Settings.SignatureSettings.upload.popUp.attentionTwo') }}
                                            </div>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page++">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 1 Select Upload Signature or Draw Signature -->
                                    <div v-if="page === 1">

                                        <!-- Choose option prompt -->
                                        <div class="step">
                                            {{ $t('Settings.SignatureSettings.upload.popUp.choose') }}
                                        </div>

                                        <!-- Buttons to choose mode or close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                <span class="button-txt">
                                                    {{ $t('KeypairAlert.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="drawSignature = false; page = page +1">
                                             <span class="button-txt">
                                                {{ $t('Settings.SignatureSettings.upload.popUp.heading') }}
                                            </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="drawSignature = true; page = page +1">
                                            <span class="button-txt" style=" text-align: center">
                                                {{ $t('Settings.SignatureSettings.upload.popUp.heading2') }}
                                            </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 Upload Signature --->
                                    <div v-if="page === 2 && drawSignature === false">

                                        <!-- Alert that shows if signature is empty -->
                                        <b-alert :show="padEmpty" @dismissed="padEmpty = false">
                                            <div class="content-div">
                                                {{ $t('Settings.SignatureSettings.upload.alert') }}
                                            </div>
                                        </b-alert>

                                        <!-- Alert that shows if uploading went wrong -->
                                        <b-alert :show="showSendingError" dismissible
                                                 @dismissed="showSendingError = false">
                                            <div class="content-div">
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div class="content-div">
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Choose file prompt -->
                                        <div class="step">
                                            {{ $t('Settings.SignatureSettings.upload.popUp.exp') }}
                                            <b-icon id="tooltip-upl" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-upl" triggers="hover">
                                                {{ $t('Settings.SignatureSettings.upload.popUp.accepted') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="display: flex; justify-content: center">
                                            <div class="content-div" style="width: 25em;">
                                                <b-form-file
                                                    v-model="signature"
                                                    :state="Boolean(signature)"
                                                    :placeholder="$t('Settings.SignatureSettings.upload.popUp.placeholder')"
                                                    :drop-placeholder="$t('Settings.SignatureSettings.upload.popUp.dropPlaceholder')"
                                                    accept="image/*"
                                                ></b-form-file>
                                            </div>
                                        </div>

                                        <!-- Buttons to cancel or upload -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                    <span class="button-txt">
                                                        {{ $t('Settings.SignatureSettings.upload.popUp.cancel') }}
                                                    </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="upload()">
                                                    <span class="button-txt">
                                                       {{ $t('Settings.SignatureSettings.upload.upload') }}
                                                    </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 Draw Signature --->
                                    <div v-else-if="page === 2 && drawSignature === true" class="signature-pad">

                                        <!-- Alert that shows if signature is empty -->
                                        <b-alert :show="padEmpty" dismissible @dismissed="padEmpty = false">
                                            <div class="content-div">
                                                {{ $t('Settings.SignatureSettings.upload.alert') }}
                                            </div>
                                        </b-alert>

                                        <!-- Alert that shows if uploading went wrong -->
                                        <b-alert :show="showSendingError" dismissible
                                                 @dismissed="showSendingError = false">
                                            <div class="content-div">
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div class="content-div">
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <div style="alignment: left">
                                            {{ $t('Settings.SignatureSettings.upload.popUp.signaturePad') }}
                                        </div>
                                        <div class="step">
                                            <div class="pad">
                                                <VueSignaturePad ref="signaturePad"/>
                                            </div>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 5">
                                                    <span class="button-txt">
                                                        {{ $t('Settings.SignatureSettings.upload.popUp.cancel') }}
                                                    </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="clear">
                                                    <span class="button-txt">
                                                       {{ $t('Settings.SignatureSettings.upload.popUp.clear') }}
                                                    </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="upload()">
                                                    <span class="button-txt">
                                                       {{ $t('Settings.SignatureSettings.upload.upload') }}
                                                    </span>
                                            </button>
                                        </div>

                                    </div>

                                    <!-- Page 4 (success) -->
                                    <div v-if="page === 4">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('Settings.SignatureSettings.upload.popUp.success') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('Settings.SignatureSettings.upload.popUp.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 5 (leave?) -->
                                    <div v-if="page === 5">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('Settings.SignatureSettings.upload.popUp.sure') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page = pageBefore">
                                                <span class="button-txt">
                                                   {{ $t('Settings.SignatureSettings.upload.popUp.back') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('Settings.SignatureSettings.upload.popUp.cancel') }}
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
    name: "SignatureUploadPopUp",
    props: {
        hasSignature: {
            type: Boolean,
            required: true
        }
    },
    data() {
        return {
            page: 0,
            pageBefore: -1,
            alreadyUpload: this.hasSignature,

            signature: null,
            image: '',
            type: null,

            drawSignature: false,
            padEmpty: false,
            showSendingError: false
        }
    },
    mounted() {
        // checking if user does not have a signature yet
        if (!this.alreadyUpload) {
            this.page = 1
        }
    },
    methods: {
        // checks if  signature exists, if so uploads it to server
        async upload() {

            // checks if the user filled the signature pad or upload an image file
            if (this.drawSignature) {
                const {isEmpty, data} = this.$refs.signaturePad.saveSignature();
                if (!isEmpty) {
                    this.image = data.replace('data:', '').replace(/^.+,/, '')
                    this.type = 'png'
                } else {
                    this.padEmpty = true
                }
            } else {
                // this.signature is the file
                if (this.signature != null) {
                    // getting type of image
                    this.type = this.signature.name.split('.')[1];
                    // converting image to base64
                    this.image = await this.asyncHandleFunction()
                } else {
                    this.padEmpty = true
                }
            }

            // checks whether the signature is not empty, if so then it will show an alert
            if (this.image !== '') {
                // sends image to server
                await this.$store.dispatch('putSignature', {signature: this.image, type: this.type})
                // checking if sending to backend went well
                if (!this.hasSendingError()) {
                    this.page = 4
                    this.padEmpty = false
                    this.drawSignature = false
                } else {
                    this.showSendingError = true
                }
            } else {
                this.padEmpty = true
            }
        },
        async asyncHandleFunction() {
            return await convertImageToBase64(this.signature)
        },
        // clears signature pad drawing
        clear() {
            this.$refs.signaturePad.clearSignature()
        },
        // closes the modal
        closeModal() {
            this.$emit('uploadTrigger');
            this.page = 1
        },
        // checks if signature got send to server correctly
        hasSendingError() {
            return this.putStatus.status !== 200;
        },
    },
    computed: {
        ...mapGetters({
            putStatus: 'getPutSignatureResponse'
        })
    },
    watch: {
        // checks if input file has correct type
        signature(newSignature) {
            if (newSignature && !newSignature.type.startsWith("image/")) {
                this.$nextTick(() => {
                    this.signature = null;
                })
            }
        }
    }
}

// converts an image to base64
const convertImageToBase64 = (file) => {
    const reader = new FileReader()
    return new Promise((resolve, reject) => {
        reader.onerror = (error) => {
            reader.abort()
            reject(error)
        }
        reader.onload = () => {
            resolve(reader.result.replace('data:', '').replace(/^.+,/, ''))
        }
        reader.readAsDataURL(file)
    })
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
