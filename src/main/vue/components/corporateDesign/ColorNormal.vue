<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.corporate.color') }}
                    </h4>
                </div>
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('AdminSettings.corporate.color1') }}
                        </span>

                        <!-- Color picker -->
                        <div>
                            <!-- TODO: on click restore with value from api -->
                            <b-icon icon="arrow-clockwise" class="reset-icon" id="r0" @click="resetColor(0)"></b-icon>
                            <span>
                                <input class="color-picker" type="color" v-model="colors[0]">
                            </span>
                        </div>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('AdminSettings.corporate.color2') }}
                        </span>

                        <!-- Color picker -->
                        <div>
                            <!-- TODO: on click restore with value from api -->
                            <b-icon icon="arrow-clockwise" class="reset-icon" id="r1" @click="resetColor(1)"></b-icon>
                            <span>
                                <input class="color-picker" type="color" v-model="colors[1]">
                            </span>
                        </div>
                    </b-list-group-item>


                    <b-list-group-item class="d-flex justify-content-end align-items-center"
                                       style=" padding-top: 0.1em; padding-bottom: 0.1em;">

                        <span class="link"
                              style="color: var(--dark-grey); opacity: 60%;  margin-top: 0.2em; margin-bottom: 0.1em;">
                            {{ $t('AdminSettings.corporate.exp') }}
                        </span>

                        <transition name="saved">
                            <span v-if="showSave" style="margin-left: 3.5em">
                                {{ $t('Settings.saved') }}
                            </span>
                        </transition>

                        <b-button class="elsa-blue-btn" @click="saveColors"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 3.5em">
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

export default {
    name: "ColorNormal",
    data() {
        return {
            showSave: false,
            colors: ["#000000", "#ea0a80"]
        }
    },
    created() {
        //TODO: use loadSheet method here, also do for every other page the same
    },
    mounted() {
        //TODO: initialize color array with colors on server
    },
    methods: {
        saveColors() {
            // TODO: make api call to set colors here and use set colors via api get as array
            constructSheet(this.colors)

            // show saved notification
            this.showSave = true
            setTimeout(() => {
                this.showSave = false
            }, 2000);
        },
        resetColor(index) {
            // TODO: reset color here with color from api

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
        }
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
