<template>
    <div style="background-color: var(--whitesmoke); height: 100vh">
        <!-- On all devices -->
        <Header></Header>
        <BaseHeading name="MessagePage.heading"></BaseHeading>

        <!-- Desktop version -->
        <div class="desktop">
            <b-container fluid
                         style="margin-left: 3vw; padding: 0; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                <b-row>
                    <b-col cols="3" style="margin-top:1vh;">
                        <div class="overflow-auto" style="height: 75vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="8" style="margin-top:2vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 75vh;"></MessageContentBox>
                        <b-container v-else fluid class="card"
                                     style="padding:0.5vh; height: 75vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                            <h4 style="margin-top: 15vh">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Tablet Version -->
        <div class="tablet">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row>
                    <b-col cols="4" style="margin-top:1vh;">
                        <div class="overflow-auto" style="height: 82vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="7" style="margin-top:2vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 82vh;"></MessageContentBox>
                        <b-container v-else fluid class="card"
                                     style="padding:0.5vh; height: 82vh;  background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                            <h4 style="margin-top: 15vh;">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Not high enough Tablets Version -->
        <div class="inBetweenTablets">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row>
                    <b-col cols="4" style="margin-top:2vh;">
                        <div class="overflow-auto" style="height: 75vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="7" style="margin-top:3vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 75vh;"></MessageContentBox>
                        <b-container v-else fluid class="card"
                                     style="padding:0.5vh; height: 75vh;  background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                            <h4 style="margin-top: 15vh;">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Mobile Version - Landscape for large phones-->
        <div class="mobileLandscapeBig">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row>
                    <b-col cols="4" style="margin-top:3.7vh;">
                        <div class="overflow-auto" style="height: 60vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="7" style="margin-top:4.7vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 60vh;"></MessageContentBox>
                        <b-container v-else fluid class="card"
                                     style="padding:0.5vh; height: 60vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                            <h4 style="margin-top: 15vh">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Mobile Version - Landscape-->
        <div class="mobileLandscape">
            <b-container fluid style="margin-left: 3vw; padding: 0;">
                <b-row cols="1">
                    <b-col style="margin-top:2.5vh;">
                        <div class="overflow-auto" style="height: 60vh" v-if="!isSelected()">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw; margin-right: 6vw">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>

                        <div class="overflow-auto" style="height: 60vh; margin-right: 6vw" v-else>
                            <MessageContentBox :msg="selectedMsg"></MessageContentBox>
                            <b-card-text class="backCard" @click="selectMsg({})">{{ $t('MessagePage.toAll') }}
                            </b-card-text>
                        </div>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Mobile Version - Landscape for small phones-->
        <div class="mobileLandscapeSmall">
            <b-container fluid style="margin-left: 3vw; padding: 0;">
                <b-row cols="1">
                    <b-col style="margin-top:2.5vh;">
                        <div class="overflow-auto" style="height: 60vh" v-if="!isSelected()">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw; margin-right: 6vw">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>

                        <div class="overflow-auto" style="height: 60vh; margin-right: 6vw" v-else>
                            <MessageContentBox :msg="selectedMsg"></MessageContentBox>
                            <b-card-text class="backCard" @click="selectMsg({})">{{ $t('MessagePage.toAll') }}
                            </b-card-text>
                        </div>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Mobile Version - Portrait-->
        <div class="mobilePortrait">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row key="msgList" cols="1">
                    <b-col style="margin-top:1.5vh;">
                        <div class="overflow-auto" style="height: 75vh" v-if="!isSelected()">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw; margin-right: 6vw">
                                <MessageBox :msg="msg"></MessageBox>
                            </div>
                        </div>

                        <div class="overflow-auto" style="height: 75vh; margin-right: 6vw" v-else>
                            <MessageContentBox :msg="selectedMsg"></MessageContentBox>
                            <b-card-text class="backCard" @click="selectMsg({})">{{ $t('MessagePage.toAll') }}
                            </b-card-text>
                        </div>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- On all devices -->
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import MessageBox from "@/main/vue/components/messagePage/MessageBox";
import Footer from "@/main/vue/components/Footer";
import MessageContentBox from "@/main/vue/components/messagePage/MessageContentBox";

import {mapGetters} from 'vuex';
import _ from "lodash";

export default {
    name: "MessagePage",
    components: {MessageContentBox, Footer, MessageBox, Header},
    methods: {
        async selectMsg(msg) {
            var status = msg.watched
            await this.$store.dispatch('messages/patchChangeSelectedMsg', msg)
            await this.$store.dispatch('messages/patchChangeWatchedStatus', msg)
            if (status !== msg.watched) {
                await this.$store.dispatch('messages/fetchMessages')
            }
        },
        isSelected() {
            return !_.isEmpty(this.selectedMsg);
        }
    },
    computed: {
        ...mapGetters({
            messages: 'messages/getMessages',
            selectedMsg: 'messages/getSelectedMessage'
        })
    }
}
</script>

<style scoped>
.backCard {
    font-size: .53em;
    background-color: var(--elsa-blue);
    color: var(--whitesmoke);
    margin-top: 0.5vh;
    padding-top: 0.7vh;
    padding-bottom: 0.7vh;
    border-radius: 3.5px;
}

/* Settings for differently sized screens */

/* Really small phones in landscape */
@media (max-width: 575.98px) and (max-height: 479.98px) {
    .desktop, .tablet, .inBetweenTablets, .mobileLandscapeBig, .mobileLandscape, .mobilePortrait {
        display: none;
    }
}

/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) and (min-height: 480px) {
    .desktop, .tablet, .inBetweenTablets, .mobileLandscapeBig, .mobileLandscape, .mobileLandscapeSmall {
        display: none;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    .desktop, .tablet, .inBetweenTablets, .mobileLandscapeBig, .mobilePortrait, .mobileLandscapeSmall {
        display: none;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .desktop, .tablet, .inBetweenTablets, .mobileLandscape, .mobilePortrait, .mobileLandscapeSmall {
        display: none;
    }

    h4 {
        font-size: .85em;
        text-overflow-mode: ellipse;
    }
}

/* Medium Devices that are not high enough */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) and (max-height: 645px) {
    .desktop, .tablet, .mobileLandscapeBig, .mobileLandscape, .mobilePortrait, .mobileLandscapeSmall {
        display: none;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 645px) {
    .desktop, .inBetweenTablets, .mobileLandscapeBig, .mobileLandscape, .mobilePortrait, .mobileLandscapeSmall {
        display: none;
    }
}


/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) {
    .tablet, .inBetweenTablets, .mobileLandscapeBig, .mobileLandscape, .mobilePortrait, .mobileLandscapeSmall {
        display: none;
    }
}
</style>
