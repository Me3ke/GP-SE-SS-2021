<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.trustedDomain.Domain.header') }}
                    </h4>
                </div>
                <b-list-group>


                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span>
                                    {{ $t('AdminSettings.trustedDomain.Domain.domain') }} {{ this.oldDomain }}
                                </span>
                      <div style="display: flex; flex-flow:column; width: 100%; justify-items: right">
                        <b-form-input placeholder="e.g. elsa.de" style="max-width: 400px; margin-left: auto"
                                      id="trustedDomain"
                                      v-model="domain"
                                      :state="domainState"
                                      aria-describedby="input-live-help input-live-feedback"

                        ></b-form-input>

                        <b-form-invalid-feedback id="input-live-feedback" style="max-width: 400px; margin-left: auto">
                          {{ $t('AdminSettings.trustedDomain.Domain.feedback') }}
                        </b-form-invalid-feedback>
                      </div>


                    </b-list-group-item>



                    <b-list-group-item class="d-flex justify-content-end align-items-center">

                        <transition name="saved">
                            <span v-if="showSave" class="content-div">
                                {{ $t('Settings.saved') }}
                            </span>
                        </transition>

                        <b-button class="elsa-blue-btn"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                                  @click="save">
                            {{ $t('Settings.MessageSettings.send') }}
                        </b-button>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>

import api from "@/main/vue/api";
import trustedDomainAPI from "@/main/vue/api/trustedDomainAPI";
export default {

    name: "DomainSettingsBox",

    data() {
        return {
            domain: '',
            oldDomain: 'test',
            showSave: false
        }
    },
    methods: {

        async save() {
            await trustedDomainAPI.putTrustedDomain(this.domain)
                .then(response => {
                    console.log("Log")
                    console.log(response.data)
                    this.load()
                }).catch(error => {
                    console.log("err")
                    console.log(error)
                })

                this.showSave = true
                setTimeout(() => {
                    this.showSave = false
                }, 2000);

        },
        async load() {
            return api.trustedDomainAPI.getTrustedDomain()
                .then(response => {
                    console.log("Log")
                    console.log(response.data)
                    this.oldDomain = response.data.message.substring(3).replace("\\","")
                }).catch(error => {
                    console.log("err")
                    console.log(error)
                })
        }
    },
    computed: {
      domainState() {
        return (this.domain.includes('.') && this.domain.length > 3)
      }
    },
    async beforeMount() {
        return api.trustedDomainAPI.getTrustedDomain()
            .then(response => {
                console.log("Log")
                console.log(response.data)
                this.oldDomain = response.data.message.substring(3).replace("\\","")
                this.domain = this.oldDomain
            }).catch(error => {
                console.log("err")
                console.log(error)
            })
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
