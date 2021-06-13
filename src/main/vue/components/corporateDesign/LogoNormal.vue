<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.corporate.logo') }}
                    </h4>
                </div>
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-center align-items-center">
                        <div>
                            <div class="logo-border">
                                <img id="logoLight" style="margin-bottom: 1em"
                                     :src="logo" class="responsive-img"
                                     alt="logo">
                            </div>

                            <b-button class="light-btn" @click="uploadLogo">
                                {{ $t('AdminSettings.corporate.logoUpload') }}
                            </b-button>
                        </div>
                    </b-list-group-item>
                    <UploadLogoPopUp v-if="showLogoUpload" @logoTrigger="uploadLogo()"></UploadLogoPopUp>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>

import UploadLogoPopUp from "@/main/vue/components/popUps/UploadLogoPopUp";

export default {
    name: "LogoNormal",
    components: {UploadLogoPopUp},
    data() {
        return {
            // TODO: gather logo from Api
            logo: require('../../assets/logos/ELSA_small.svg'),
            showLogoUpload: false
        }
    },
    methods: {
        uploadLogo() {
            this.showLogoUpload = !this.showLogoUpload
            this.$emit('modalTrigger')
        },
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
