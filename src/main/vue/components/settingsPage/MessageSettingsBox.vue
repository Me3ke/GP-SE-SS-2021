<template>
    <b-container fluid="sm">

        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.MessageSettings.settings') }}
                    </h4>
                </div>
                <b-list-group flush>
                    <b-list-group-item v-if="isAdmin" class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('Settings.MessageSettings.toDo.text') }}
                               <b-icon id="tooltip" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip" triggers="hover">
                                   {{ $t('Settings.MessageSettings.toDo.exp') }}
                              </b-tooltip>
                          </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.toDo" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                              {{ $t('Settings.MessageSettings.sign.text') }}
                             <b-icon id="tooltip-target-2" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-2" triggers="hover">
                                   {{ $t('Settings.MessageSettings.sign.exp') }}
                              </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.sign" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                                   {{ $t('Settings.MessageSettings.read.text') }}
                             <b-icon id="tooltip-target-3" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-3" triggers="hover">
                                   {{ $t('Settings.MessageSettings.read.exp') }}
                              </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.read" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.MessageSettings.progress.text') }}
                             <b-icon id="tooltip-target-4" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-4" triggers="hover">
                                   {{ $t('Settings.MessageSettings.progress.exp') }}
                              </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.progress" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('Settings.MessageSettings.newVersion.text') }}
                             <b-icon id="tooltip-target-5" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-5" triggers="hover">
                                   {{ $t('Settings.MessageSettings.newVersion.exp') }}
                              </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.newVersion" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-end align-items-center"
                                       style=" padding-top: 0.1em; padding-bottom: 0.1em;">

                        <transition name="saved">
                            <span v-if="showSave" class="content-div">
                                {{ $t('Settings.saved') }}
                            </span>
                        </transition>

                        <b-button class="elsa-blue-btn"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em" @click="save">
                            {{ $t('Settings.MessageSettings.send') }}
                        </b-button>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>

    </b-container>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "MessageSettingsBox",
    data() {
        return {
            showSave: false,
            clicked: false,
        }
    },
    async mounted() {
        await this.$store.dispatch('messages/fetchMessagesConfig')
    },
    methods: {
        // sends config to backend
        async save() {
            await this.$store.dispatch('messages/patchChangeMessageSettings', this.config)

            // show saved notification
            this.showSave = true
            setTimeout(() => {
                this.showSave = false
            }, 2000);
        }
    },
    computed: {
        ...mapGetters({
            config: 'messages/getConfig',
            isAdmin: 'isAdmin'
        })
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
