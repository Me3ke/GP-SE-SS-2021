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
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('Settings.MessageSettings.toDo.text') }}
                               <b-icon id="tooltip-target-1" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-1" triggers="hover">
                                   {{ $t('Settings.MessageSettings.toDo.exp') }}
                              </b-tooltip>
                          </span>

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="requests" switch
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

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="signReminder" switch
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

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="readReminder" switch
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

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="progress" switch
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

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="update" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.MessageSettings.protocol.text') }}
                             <b-icon id="tooltip-target-6" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-6" triggers="hover">
                                   {{ $t('Settings.MessageSettings.protocol.exp') }}
                              </b-tooltip>
                        </span>

                        <!-- TODO: connect to api-->
                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="protocol" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>


                    <b-list-group-item class="d-flex justify-content-end align-items-center"
                                       style=" padding-top: 0.1em; padding-bottom: 0.1em;">
                        <b-button class="green-btn">
                            {{ $t('Settings.MessageSettings.send') }}
                        </b-button>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>

    </b-container>
</template>

<script>
export default {
    name: "MessageSettingsBox",
    data() {
        return {
            clicked: false,
            // TODO: connect with api
            requests: true,
            signReminder: false,
            readReminder: true,
            progress: false,
            update: true,
            protocol: true
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
