<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ 'SMTP Server' }}
                    </h4>
                </div>
                <b-list-group>

                    <!-- Host -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span style="white-space: nowrap;">
                                    {{ 'Host: '}} {{ this.old.host }}
                                </span>
                      <div style="display: flex; flex-flow:column; width: 100%; justify-items: right">
                        <b-form-input style="max-width: 400px; margin-left: auto"
                                      id="host"
                                      v-model="host"
                                      :state="hostState"
                                      aria-describedby="input-live-help input-live-feedback"

                        ></b-form-input>

                        <b-form-invalid-feedback id="input-live-feedback" style="max-width: 400px; margin-left: auto">

                        </b-form-invalid-feedback>
                      </div>
                    </b-list-group-item>

                    <!-- Port -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span style="white-space: nowrap;">
                                    {{ 'Port: ' }} {{ this.old.port.toString() }}
                                </span>
                        <div style="display: flex; flex-flow:column; width: 100%; justify-items: right">
                            <b-form-input style="max-width: 400px; margin-left: auto"
                                          id="port"
                                          v-model="port"
                                          :state="portState"
                                          aria-describedby="input-live-help input-live-feedback"

                            ></b-form-input>

                            <b-form-invalid-feedback id="input-live-feedback" style="max-width: 400px; margin-left: auto">

                            </b-form-invalid-feedback>
                        </div>
                    </b-list-group-item>

                    <!-- Username -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span style="white-space: nowrap;">
                                    {{ 'Username: ' }}{{ this.old.username }}
                                </span>
                        <div style="display: flex; flex-flow:column; width: 100%; justify-items: right">
                            <b-form-input style="max-width: 400px; margin-left: auto"
                                          id="username"
                                          v-model="username"
                                          :state="usernameState"
                                          aria-describedby="input-live-help input-live-feedback"

                            ></b-form-input>

                            <b-form-invalid-feedback id="input-live-feedback" style="max-width: 400px; margin-left: auto">

                            </b-form-invalid-feedback>
                        </div>
                    </b-list-group-item>

                    <!-- Password -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                                <span>
                                    {{ $t('Login.password') + ': ' }}
                                </span>
                        <div style="display: flex; flex-flow:column; width: 100%; justify-items: right">
                            <b-form-input style="max-width: 400px; margin-left: auto"
                                          id="password"
                                          type="password"
                                          v-model="password"
                                          :state="passwordState"
                                          aria-describedby="input-live-help input-live-feedback"

                            ></b-form-input>

                            <b-form-invalid-feedback id="input-live-feedback" style="max-width: 400px; margin-left: auto">

                            </b-form-invalid-feedback>
                        </div>
                    </b-list-group-item>

                    <!-- mailSMTPAuth -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ 'SMTP Auth: ' }} {{ old.mailSMTPAuth }}
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="mailSMTPAuth" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <!-- mailSMTP TLS -->
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ 'SMTP TLS: ' }} {{old.mailSMTPStartTLSEnable}}
                        </span>

                        <div class="toggle-container">
                            <span style="display: inline-block; margin-right: 0.6em; font-size: 1em;">
                                 {{ $t('Settings.off') }}
                            </span>
                            <b-form-checkbox v-model="mailSMTPStartTLSEnable" switch
                                             style="display: inline-block">
                            </b-form-checkbox>
                            <span style="display: inline-block; font-size: 1em;">
                               {{ $t('Settings.on') }}
                            </span>
                        </div>
                    </b-list-group-item>

                    <!-- save -->
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

    name: "SMTPServerSettingsBox",

    data() {
        return {
                host: '',
                port: '',
                username: '',
                password: '',
                mailSMTPAuth: '',
                mailSMTPStartTLSEnable: '',
                showSave: false,
                old: {
                    host: '',
                    port: '',
                    username: '',
                    mailSMTPAuth: '',
                    mailSMTPStartTLSEnable: ''
                }
        }
    },
    methods: {
        hasNumber(t) {
            let regex =  /\d/g;
            return regex.test(t);
        },
        async save() {
            await trustedDomainAPI.putSMTP(this.host, this.port, this.username, this.password, this.mailSMTPAuth, this.mailSMTPStartTLSEnable)
                .then(response => {
                    console.log("Log")
                    console.log(response.data)
                    this.load()
                    this.showSave = true

                }).catch(error => {
                    console.log("err")
                    console.log(error)
                })

        },
        async load() {
            return api.trustedDomainAPI.getSMTP()
                .then(response => {
                    this.old = response.data
                    this.host = this.old.host
                    this.port = this.old.port.toString()
                    this.username = this.old.username
                    this.mailSMTPAuth = this.old.mailSMTPAuth
                    this.mailSMTPStartTLSEnable = this.old.mailSMTPStartTLSEnable
                }).catch(error => {
                    console.log("err")
                    console.log(error)
                })
        }
    },
    computed: {
      hostState() {
        return ( this.host.length > 3)
      },
      portState() {
          return ( this.port.length > 0 && this.hasNumber(parseInt(this.port)) && this.port.length < 6)
      },
      usernameState() {
          return ( this.username.length > 3)
      },
      passwordState () {
          return ( this.password.length > 3)
      }
    },
    async beforeCreate() {
        return api.trustedDomainAPI.getSMTP()
            .then(response => {
                this.old = response.data
                this.host = this.old.host
                this.port = this.old.port.toString()
                this.username = this.old.username
                this.mailSMTPAuth = this.old.mailSMTPAuth
                this.mailSMTPStartTLSEnable = this.old.mailSMTPStartTLSEnable
            }).catch(error => {
            console.log("err")
            console.log(error)
        })
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
