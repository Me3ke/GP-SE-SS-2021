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

                    <b-list-group-item v-if="signature" class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('Settings.SignatureSettings.upload.current') }}
                          </span>

                        <div>
                            <img :src="getSignatureSource()" class="responsive-img" id="responsive-signature"
                                 :alt="$t('Settings.SignatureSettings.signature')">
                        </div>
                    </b-list-group-item>

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
                    <SignatureUploadPopUp :hasSignature="signature !== null" v-if="showUpload"
                                          @uploadTrigger="upload()"></SignatureUploadPopUp>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import SignatureUploadPopUp from "@/main/vue/components/popUps/SignatureUploadPopUp";
import userAPI from "@/main/vue/api/userAPI";

export default {
    name: "SignatureSettingsBox",
    components: {SignatureUploadPopUp},
    data() {
        return {
            showUpload: false,
            signature: null,
            signatureType: null
        }
    },
    async mounted() {
        await userAPI.getSignature().then(response => {
            this.signature = response.data.imageSignature
            this.signatureType = response.data.imageSignatureType
        }).catch(error => {
            console.log(error)
        })
    },
    methods: {
        async upload() {
            this.showUpload = !this.showUpload
            this.$emit('uploadTrigger')

            // updating signature image that gets displayed
            await userAPI.getSignature().then(response => {
                this.signature = response.data.imageSignature
                this.signatureType = response.data.imageSignatureType
            }).catch(error => {
                console.log(error)
            })
        },
        getSignatureSource() {
            if (this.signatureType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.signature
            } else {
                return 'data:image/' + this.signatureType + ';base64,' + this.signature
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">

</style>
