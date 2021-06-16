<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.SignatureSettings.settings') }}
                    </h4>
                </div>
                <b-list-group flush>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('Settings.SignatureSettings.upload.text') }}
                               <b-icon id="tooltip-sig" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-sig" triggers="hover">
                                   {{ $t('Settings.SignatureSettings.upload.exp') }}
                              </b-tooltip>
                          </span>

                        <b-button class="light-btn" @click="upload">
                            {{ $t('Settings.SignatureSettings.upload.upload') }}
                        </b-button>
                    </b-list-group-item>
                    <SignatureUploadPopUp v-if="showUpload" @uploadTrigger="upload()"></SignatureUploadPopUp>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import SignatureUploadPopUp from "@/main/vue/components/popUps/SignatureUploadPopUp";

export default {
    name: "SignatureSettingsBox",
    components: {SignatureUploadPopUp},
    data() {
        return {
            showUpload: false
        }
    },
    methods: {
        upload() {
            this.showUpload = !this.showUpload
            this.$emit('uploadTrigger')
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">

</style>
