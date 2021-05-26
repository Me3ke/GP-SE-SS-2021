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
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge @click="clicked = !clicked" :class="[clicked ? 'badge-on' : 'badge-off']">
                                {{ clicked }}
                            </b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                              {{ $t('Settings.MessageSettings.sign.text') }}
                             <b-icon id="tooltip-target-2" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-2" triggers="hover">
                                   {{ $t('Settings.MessageSettings.sign.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                                   {{ $t('Settings.MessageSettings.read.text') }}
                             <b-icon id="tooltip-target-3" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-3" triggers="hover">
                                   {{ $t('Settings.MessageSettings.read.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.MessageSettings.progress.text') }}
                             <b-icon id="tooltip-target-4" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-4" triggers="hover">
                                   {{ $t('Settings.MessageSettings.progress.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('Settings.MessageSettings.newVersion.text') }}
                             <b-icon id="tooltip-target-5" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-5" triggers="hover">
                                   {{ $t('Settings.MessageSettings.newVersion.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('Settings.MessageSettings.protocol.text') }}
                             <b-icon id="tooltip-target-6" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-6" triggers="hover">
                                   {{ $t('Settings.MessageSettings.protocol.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-off">Off</b-badge>
                        </h4>
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
            clicked: false
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
