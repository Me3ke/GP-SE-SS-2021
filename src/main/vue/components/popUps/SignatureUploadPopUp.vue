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
                                        {{ $t('Settings.SignatureSettings.upload.popUp.heading') }}
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

                                    <!-- Page 1 (simple) -->
                                    <div v-if="page === 1">
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
                                                    @click="pageBefore = page; page = 3">
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

                                    <!-- Page 2 (success) -->
                                    <div v-if="page === 2">
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

                                    <!-- Page 3 (leave?) -->
                                    <div v-if="page === 3">
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
export default {
    name: "SignatureUploadPopUp",
    data() {
        return {
            // TODO: axios call to check if already set up, if so change page to 1
            page: 0,
            pageBefore: -1,
            // TODO: connect to API
            alreadyUpload: false,
            signature: null
        }
    },
    methods: {
        // TODO: connect to API
        upload() {
        },
        closeModal() {
            this.$emit('uploadTrigger');
            this.page = 1
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">

</style>
