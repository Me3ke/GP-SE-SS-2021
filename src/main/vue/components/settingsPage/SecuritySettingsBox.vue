<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.SecuritySettings.settings') }}
                    </h4>
                </div>
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span>
                                    {{ $t('Settings.SecuritySettings.publicKey') }} {{ this.userData.publicKey }}
                                </span>

                        <b-button class="elsa-blue-btn" @click="decideLanguage">
                            {{ $t('Settings.SecuritySettings.newKeypair') }}
                        </b-button>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.SecuritySettings.twoFacAuthSetUp') }}
                        </span>

                        <b-button class="elsa-blue-btn" @click="setUp()">
                            {{ $t('Settings.SecuritySettings.setUp') }}
                        </b-button>

                        <TwoFakAuthSetUp v-if="showSetUp" @modalTrigger="setUp"></TwoFakAuthSetUp>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import i18n from "@/i18n";
import TwoFakAuthSetUp from "@/main/vue/components/TwoFakAuth/TwoFakAuthSetUp";

export default {
    name: "SecuritySettingsBox",
    components: {
        TwoFakAuthSetUp
    },
    props: {
        user: Object,
        userData: Object
    },
    data() {
        return {
            showSetUp: false
        }
    },
    methods: {
        decideLanguage() {
            if (i18n.locale === 'de') {
                this.showAlertDE()
            } else {
                this.showAlertEN()
            }
        },

        async showAlertEN() {
            const inputOptions = new Promise((resolve) => {
                resolve({
                    'generate': 'Generate keypair',
                    'upload': 'Upload public key',
                })
            })

            const {value: updateOption} = await this.$swal.fire({
                title: 'Select option',
                input: 'radio',
                width: 500,
                inputOptions: inputOptions,
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'You need to choose something!'
                    }
                }
            })

            if (updateOption === 'generate') {
                this.$swal.fire({
                    title: 'Warning',
                    icon: 'warning',
                    text: 'If you continue, your current keypair will be lost ' +
                        'forever. Are you sure you want to continue?',
                    confirmButtonText: `Continue`,
                    showCancelButton: true,
                    cancelButtonText: 'Cancel'
                }).then((result) => {

                    if (result.isConfirmed) {
                        this.handleGenerateKeyPairs()
                        this.$swal.fire('Generating...')
                    }
                })
            } else if (updateOption === 'upload') {
                this.$swal.fire({
                    title: 'Warning',
                    icon: 'warning',
                    text: 'If you continue, your current keypair will be lost ' +
                        'forever. Are you sure you want to continue?',
                    confirmButtonText: `Continue`,
                    showCancelButton: true,
                    cancelButtonText: 'Cancel'
                }).then((result) => {
                    /* Read more about isConfirmed, isDenied below */
                    if (result.isConfirmed) {
                        this.handleUploadKeyPairs
                        this.$swal.fire('Uploading...')
                    }
                })
            }
        },

        async showAlertDE() {
            const inputOptions = new Promise((resolve) => {
                resolve({
                    'generate': 'Generiere Schlüsselpaar',
                    'upload': 'Lade Schlüsselpaar hoch',
                })
            })

            const {value: updateOption} = await this.$swal.fire({
                title: 'Wähle Option',
                input: 'radio',
                width: 500,
                inputOptions: inputOptions,
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Es muss etwas ausgewählt werden!'
                    }
                }
            })

            if (updateOption === 'generate') {
                this.$swal.fire({
                    title: 'Warnung',
                    icon: 'warning',
                    text: 'Wenn Sie fortfahren, wird das alte Schlüsselpaar für immer verloren gehen; Sind Sie sich sicher?',
                    confirmButtonText: `Fortfahren`,
                    showCancelButton: true,
                    cancelButtonText: 'Abbruch'
                }).then((result) => {

                    if (result.isConfirmed) {
                        this.handleGenerateKeyPairs()
                        this.$swal.fire('Generieren...')
                    }
                })
            } else if (updateOption === 'upload') {
                this.$swal.fire({
                    title: 'Warnung',
                    icon: 'warning',
                    text: 'Wenn Sie fortfahren, wird das alte Schlüsselpaar für immer verloren gehen; Sind Sie sich sicher?',
                    confirmButtonText: `Fortfahren`,
                    showCancelButton: true,
                    cancelButtonText: 'Abbruch'
                }).then((result) => {
                    /* Read more about isConfirmed, isDenied below */
                    if (result.isConfirmed) {
                        this.handleUploadKeyPairs()
                        this.$swal.fire('Hochladen...')
                    }
                })
            }
        },
        handleGenerateKeyPairs() {
            //TODO Handling of the keypairs via generating

        },
        handleUploadKeyPairs() {
            //TODO Handling of the keypairs via uploading
        },
        setUp() {
            this.showSetUp = !this.showSetUp
            this.$emit('modalTrigger')
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
