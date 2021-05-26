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
                    <b-list-group-item>
                        <b-row>
                            <b-col style="text-align: left">
                                <span>
                                    {{ $t('Settings.SecuritySettings.publicKey') }} {{ this.userData.publicKey }}
                                </span>
                            </b-col>
                            <b-col style="text-align: right">
                                <b-button id="keyButton" @click="decideLanguage">
                                    {{ $t('Settings.SecuritySettings.newKeypair') }}
                                </b-button>
                            </b-col>
                        </b-row>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import i18n from "@/i18n";
import {mapGetters} from 'vuex';
export default {
    name: "SecuritySettingsBox",
    props: {
        user: Object,
        userData: Object
    },
    computed: {
      ...mapGetters({
        keypair: 'getKeypair',
      })
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
                    'generate': 'Generate keypair' ,
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
                        this.handleUploadKeyPairs()
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
          this.$store.dispatch('callToGenerate')
          this.$swal.fire({
            text: this.keypair.privateKey
          })
            //TODO Handling of the keypairs via generating

        },
        handleUploadKeyPairs() {
            //TODO Handling of the keypairs via uploading
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
