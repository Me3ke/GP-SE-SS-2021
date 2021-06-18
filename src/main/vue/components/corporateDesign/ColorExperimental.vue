<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.corporate.color') }}
                    </h4>
                </div>

                <!-- Alert that shows if uploading colors to backend was unsuccessful -->
                <b-alert :show="showSendingAlert" style="margin-bottom: 0">
                    <div>
                        {{ $t('TwoFakAuth.serverErrorOne') }}
                    </div>
                    <div>
                        {{ $t('TwoFakAuth.serverErrorTwoAdmin') }}
                    </div>
                </b-alert>

                <!-- Modes -->
                <b-container fluid="sm" class="card-header"
                             style="background-color: var(--elsa-blue-transparent); color: var(--dark-grey);">
                    <b-row align-h="center" align-v="center" cols="3">
                        <b-col></b-col>
                        <b-col style="text-align: center;">
                            <span style="margin-left: 1.5em">
                                {{ $t('AdminSettings.corporate.light') }}
                            </span>
                        </b-col>
                        <b-col style="text-align: center">
                            <span style="margin-right: 0.5em">
                                {{ $t('AdminSettings.corporate.dark') }}
                            </span>
                        </b-col>
                    </b-row>
                </b-container>

                <!-- Content -->
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <b-container fluid="sm">
                            <b-row align-h="center" align-v="center" cols="3"
                                   v-for="index in Array.from(Array(14).keys())"
                                   :key="index">
                                <b-col style="text-align: left">
                                    <span style="text-align: left">
                                        {{ $t('AdminSettings.corporate.color' + (index + 1)) }}
                                    </span>
                                </b-col>
                                <b-col>
                                    <!-- Color picker -->
                                    <div>
                                        <b-icon icon="arrow-clockwise" class="reset-icon" :id="'r' + index"
                                                @click="resetColor(index)"></b-icon>
                                        <span>
                                             <input class="color-picker" type="color" v-model="colors[index]">
                                        </span>
                                    </div>
                                </b-col>
                                <b-col>
                                    <!-- Color picker -->
                                    <div>
                                        <b-icon icon="arrow-clockwise" class="reset-icon" :id="'r' + (index+14)"
                                                @click="resetColor(index + 14)"></b-icon>
                                        <span>
                                             <input class="color-picker" type="color" v-model="colors[index+14]">
                                        </span>
                                    </div>
                                </b-col>
                            </b-row>
                        </b-container>
                    </b-list-group-item>


                    <b-list-group-item class="d-flex justify-content-end align-items-center"
                                       style=" padding-top: 0.1em; padding-bottom: 0.1em;">

                        <transition name="saved">
                            <span v-if="showSave" style="margin-left: 3.5em">
                                {{ $t('Settings.saved') }}
                            </span>
                        </transition>

                        <b-button class="elsa-blue-btn" @click="saveColors"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 3.5em; margin-right: 1em">
                            {{ $t('Settings.MessageSettings.send') }}
                        </b-button>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import {constructSheet} from "@/main/vue/scripts/stylesheetManipulator";
import _ from "lodash";
import {mapGetters} from "vuex";

export default {
    name: "ColorExperimental",
    data() {
        return {
            showSave: false,
            showSendingAlert: false,
            colors: []
        }
    },
    async mounted() {
        await this.$store.dispatch('theme/fetchColors')
        this.colors = [...this.sheetColors]
    },
    methods: {
        async saveColors() {
            // setting colors on server
            await this.$store.dispatch('theme/putColors', this.colors)
            await this.$store.dispatch('theme/fetchColors')

            // constructing stylesheet and applying it to DOM
            constructSheet(this.sheetColors)

            // shows 'saved' if no error occurred, otherwise shows warning
            if (!this.hasSendingError()) {
                // show saved notification
                this.showSave = true
                setTimeout(() => {
                    this.showSave = false
                }, 2000);
                this.showSendingAlert = false
            } else {
                this.showSendingAlert = true
            }

        },
        resetColor(index) {
            //  reset color here with color from api
            this.$set(this.colors, index, this.sheetColors[index])

            // rotate animation
            document.querySelector("#r" + index).style.transform = "rotate(45deg)";
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(90deg)";
            }, 50);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(135deg)";
            }, 100);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(180deg)";
            }, 150);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(225deg)";
            }, 200);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(225deg)";
            }, 250);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(270deg)";
            }, 300);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(315deg)";
            }, 350);
            setTimeout(() => {
                document.querySelector("#r" + index).style.transform = "rotate(360deg)";
            }, 400);
        },
        // checks if colors got send to server correctly
        hasSendingError() {
            return !_.isEmpty(this.uploadError)
        },
    },
    computed: {
        ...mapGetters({
            sheetColors: 'theme/getColors',
            uploadError: 'theme/getErrorPutColorResponse'
        })
    },
    watch: {
        colors(index, val) {
            this.colors[index] = val
        }
    },
    beforeDestroy() {
        this.$store.dispatch('theme/resetColors')
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
