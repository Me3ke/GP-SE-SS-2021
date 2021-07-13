<template>
    <b-container fluid="sm">

        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.AddressBookSettings.settings') }}
                    </h4>
                </div>
                <b-list-group flush>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('Settings.AddressBookSettings.all') }}
                               <b-icon id="tooltip-addbook1" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-addbook1" triggers="hover click">
                                   {{ $t('Settings.AddressBookSettings.allExp') }}
                              </b-tooltip>
                          </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.addAllAutomatically" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                                    {{ $t('Settings.AddressBookSettings.domain') }}
                             <b-icon id="tooltip-addbook2" icon="info-circle" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-addbook2" triggers="hover click">
                                     {{ $t('Settings.AddressBookSettings.domainExp') }}
                              </b-tooltip>
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="config.addDomainAutomatically" switch
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
    name: "AddressBookSettingsBox",
    data() {
        return {
            showSave: false
        }
    },
    async mounted() {
        await this.$store.dispatch('addressBook/fetchSettings')
    },
    methods: {
        // sends config to backend
        async save() {
            await this.$store.dispatch('addressBook/changeSettings', this.config)
            await this.$store.dispatch('addressBook/fetchSettings')

            // show saved notification
            this.showSave = true
            setTimeout(() => {
                this.showSave = false
            }, 2000);
        }
    },
    computed: {
        ...mapGetters({
            config: 'addressBook/getSettings'
        })
    },
    watch: {
        // checks if add all is true, if so add domain has to be true as well
        config: async function (val) {
            if (val.addAllAutomatically && !this.config.addDomainAutomatically) {
                this.config.addDomainAutomatically = true
                await this.$store.dispatch('addressBook/changeSettings', this.config)
                await this.$store.dispatch('addressBook/fetchSettings')
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
