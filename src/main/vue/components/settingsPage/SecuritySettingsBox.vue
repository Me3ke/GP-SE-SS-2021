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
                    </b-list-group-item>
                    <TwoFakAuthSetUp v-if="showSetUp" @modalTrigger="setUp"></TwoFakAuthSetUp>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('TwoFakAuth.login.always') }}
                             <b-icon id="tooltip-security" icon="info-circle" class="my-icon"></b-icon>
                             <b-tooltip target="tooltip-security" triggers="hover">
                                {{ $t('TwoFakAuth.login.alwaysExp') }}
                            </b-tooltip>
                        </span>

                        <!-- TODO: connect to API -->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="loginActive" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import i18n from "@/i18n";
import {mapActions, mapGetters} from 'vuex';
import TwoFakAuthSetUp from "@/main/vue/components/TwoFakAuth/TwoFakAuthSetUp";
import _ from "lodash";

export default {
    name: "SecuritySettingsBox",
    components: {
        TwoFakAuthSetUp
    },
    props: {
        user: Object,
        userData: Object
    },
    computed: {
      ...mapGetters({
        privateKey: 'getPrivateKey',
        publicKey: 'getPublicKey',
        sendingSuccess: 'getSendingSuccess'
      })
  },
    data() {
        return {
            showSetUp: false,
            // TODO: use value from API
            loginActive: false
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
        hasError(){
         return _.isEmpty(this.sendingSuccess);
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
                    }
                })
            }
        },
      ...mapActions(['callToGenerate']),
        async handleGenerateKeyPairs() {
          this.callToGenerate()
          this.$swal.fire({
            text: this.privateKey
          })
          await this.$store.dispatch('sendPublicKey', {"publicKey": this.publicKey})
        },
        async handleUploadKeyPairs() {
          const {value: file} = await this.$swal.fire({
            title: 'Select image',
            input: 'file',
            inputAttributes: {
              'accept': 'text/*',
            }
          })

          if (file) {
            const reader = new FileReader()
            reader.readAsText(file)
            reader.onload = (e) => {
              this.$store.dispatch('sendPublicKey', {"publicKey": e.target.result})
            }
          }

          if (this.hasError()) {
            this.$swal.fire({
              title: 'Ups',
              icon: 'error',
              text: 'The public key was incorrect; Please try another one'
            })
          }

          else{
            this.$swal.fire({
              title: 'Perfect',
              icon: 'success',
              text: 'Your public key was successfully uploaded'
            })
          }
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
