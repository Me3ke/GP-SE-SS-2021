<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.corporate.logo') }}
                    </h4>
                </div>

                <b-container fluid="sm" class="card-header"
                             style="background-color: var(--elsa-blue-transparent); color: var(--dark-grey);">
                    <b-row align-h="center" align-v="center" cols="2">
                        <b-col>
                            <span>
                                {{ $t('AdminSettings.corporate.light') }}
                            </span>
                        </b-col>
                        <b-col>
                            <span>
                                {{ $t('AdminSettings.corporate.dark') }}
                            </span>
                        </b-col>
                    </b-row>
                </b-container>

                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-center align-items-center">
                        <b-container fluid="sm">
                            <b-row align-h="center" align-v="center" cols="2">
                                <b-col>
                                    <div>
                                        <div class="logo-border">
                                            <b-img v-if="lightEmpty" :src="elsaLight" class="responsive-img"
                                                   :alt="$t('Header.logo')"></b-img>
                                            <img v-else :src="getLightSource()" class="responsive-img"
                                                 :alt="$t('Header.logo')">
                                        </div>

                                        <b-button class="light-btn" @click="uploadLogo(0)">
                                            {{ $t('AdminSettings.corporate.logoUpload') }}
                                        </b-button>
                                    </div>
                                </b-col>
                                <b-col>
                                    <div>
                                        <div class="logo-border">
                                            <b-img v-if="darkEmpty" :src="elsaDark" class="responsive-img"
                                                   :alt="$t('Header.logo')"></b-img>
                                            <img v-else :src="getDarkSource()" class="responsive-img"
                                                 :alt="$t('Header.logo')">
                                        </div>

                                        <b-button class="light-btn" @click="uploadLogo(1)">
                                            {{ $t('AdminSettings.corporate.logoUpload') }}
                                        </b-button>
                                    </div>
                                </b-col>
                            </b-row>
                        </b-container>
                    </b-list-group-item>
                    <UploadLogoPopUp :exp="true" :type="type" v-if="showLogoUpload"
                                     @logoTrigger="uploadLogo(-1)"></UploadLogoPopUp>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import {mapGetters} from "vuex";
import UploadLogoPopUp from "@/main/vue/components/popUps/UploadLogoPopUp";

export default {
    name: "LogoExperimental",
    components: {UploadLogoPopUp},
    data() {
        return {
            elsaLight: require('../../assets/logos/ELSA_small.svg'),
            elsaDark: require('../../assets/logos/ELSA_small_darkmode.svg'),

            showLogoUpload: false,
            // indicates which logo is getting changed
            type: -1
        }
    },
    methods: {
        async uploadLogo(type) {
            if (type === -1) {
                await this.$store.dispatch('theme/getLogos')
            }
            this.type = type
            this.showLogoUpload = !this.showLogoUpload
            this.$emit('modalTrigger')
        },
        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        },
        getDarkSource() {
            if (this.logoDarkType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoDark
            } else {
                return 'data:image/' + this.logoDarkType + ';base64,' + this.logoDark
            }
        }
    },
    computed: {
        ...mapGetters({
            logoLight: 'theme/getLightLogo',
            logoLightType: 'theme/getLightLogoType',
            logoDark: 'theme/getDarkLogo',
            logoDarkType: 'theme/getDarkLogoType'
        }),
        lightEmpty() {
            return this.logoLight === ""
        },
        darkEmpty() {
            return this.logoDark === ""
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
