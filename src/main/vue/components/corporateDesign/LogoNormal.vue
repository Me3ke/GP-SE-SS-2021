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
                                <img :src="getLightSource()" class="responsive-img"
                                     :alt="$t('Header.logo')">
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
import {mapGetters} from "vuex";

export default {
    name: "LogoNormal",
    components: {UploadLogoPopUp},
    data() {
        return {
            showLogoUpload: false
        }
    },
    methods: {
        async uploadLogo() {
            await this.$store.dispatch('theme/getLogos')
            this.showLogoUpload = !this.showLogoUpload
            this.$emit('modalTrigger')
        },
        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        }
    },
    computed: {
        ...mapGetters({
            logoLight: 'theme/getLightLogo',
            logoLightType: 'theme/getLightLogoType'
        })
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
