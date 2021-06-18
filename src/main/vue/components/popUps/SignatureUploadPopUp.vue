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
                                        {{ drawSignature ? $t('Settings.SignatureSettings.upload.popUp.heading2') : $t('Settings.SignatureSettings.upload.popUp.heading') }}
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
                                        <button type="button" class="light-btn" @click="drawSignature = false; page = page +1">
                                            <h5>
                                                {{ $t('Settings.SignatureSettings.upload.popUp.heading') }}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="drawSignature = true; page = page +1">
                                            <h5>
                                                {{ $t('Settings.SignatureSettings.upload.popUp.heading2') }}
                                            </h5>
                                        </button>
                                    </div>

                                    <!-- Page 2 Upload Signature --->
                                    <div v-if="page === 2 && drawSignature === false">

                                            <div class="alert-div" v-if="padEmpty">
                                                <b-alert show variant="danger">
                                                    {{ $t('Settings.SignatureSettings.upload.alert') }}
                                                </b-alert>
                                            </div>
                                            <div class="step">
                                                {{ $t('Settings.SignatureSettings.upload.popUp.exp') }}
                                                <b-icon id="tooltip-upl" icon="info-circle" class="my-icon"></b-icon>
                                                <b-tooltip target="tooltip-upl" triggers="hover">
                                                    {{ $t('Settings.SignatureSettings.upload.popUp.accepted') }}
                                                </b-tooltip>
                                            </div>

                                            <div class="content-div" style="width: 25em">
                                                <b-form-file
                                                    v-model="signature"
                                                    :state="Boolean(signature)"
                                                    :placeholder="$t('Settings.SignatureSettings.upload.popUp.placeholder')"
                                                    :drop-placeholder="$t('Settings.SignatureSettings.upload.popUp.dropPlaceholder')"
                                                    accept="image/*"
                                                ></b-form-file>
                                            </div>


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
                                            <div v-if="padEmpty" style="text-align: center">
                                                <div style="display: inline-block">
                                                    <b-alert show variant="danger">
                                                        {{ $t('Settings.SignatureSettings.upload.alert') }}
                                                    </b-alert>
                                                </div>
                                            </div>

                                            <div style="alignment: left">{{ $t('Settings.SignatureSettings.upload.popUp.signaturePad') }}
                                            </div>
                                            <div class="step">
                                                <div class="pad">
                                                    <VueSignaturePad ref="signaturePad" />
                                                </div>
                                                <div class="pt-2">
                                                    <button class="light-btn btn-sm"
                                                            id="clear-button"
                                                            @click="clear"
                                                            >
                                                        {{ $t('Settings.SignatureSettings.upload.popUp.clear') }}
                                                    </button>
                                                </div>
                                            </div>

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

                                    <!-- Page 4 (success) -->
                                    <div v-if="page === 4">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.success') }}
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

                                    <!-- Page 4 (leave?) -->
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
import VueSignaturePad from 'vue-signature-pad';

export default {
    name: "SignatureUploadPopUp",
    components: {VueSignaturePad},
    data() {
        return {
            // TODO: axios call to check if already set up, if so change page to 1
            page: 0,
            pageBefore: -1,
            // TODO: connect to API
            alreadyUpload: false,
            signature: null,
            image: '',
            drawSignature: false,
            padEmpty: false
        }
    },
    methods: {
        // TODO: connect to API
        async upload() {

            if(this.drawSignature) {
                // checks if the user filled the signature pad or upload a image file
                const {isEmpty, data} = this.$refs.signaturePad.saveSignature();
                if(!isEmpty) {
                    this.image = data.replace('data:', '').replace(/^.+,/, '')
                } else {
                    this.padEmpty = true
                }

            } else {
                // this.signature is the file
                if(this.signature != null) {
                    this.image = await this.asyncHandleFunction()
                } else {
                    this.padEmpty = true
                }
            }

            // checks whether the signature is not empty, if so then it will show an alert
            if(this.image !== '') {
                this.page = 4
                this.padEmpty = false
                this.drawSignature = false
            }

            // todo emit or dispatch (api)
        },
        closeModal() {
            this.$emit('uploadTrigger');
            this.page = 1
        },

        async asyncHandleFunction() {
            return await convertImageToBase64(this.signature)
        },

        clear() {
            this.$refs.signaturePad.clearSignature()
        }


    }
}

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
