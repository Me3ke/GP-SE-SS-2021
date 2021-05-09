<template>
    <div>
        <Header></Header>
        <BaseHeading name="MessagePage.heading"></BaseHeading>
        <!-- Desktop version -->
        <div class="desktop">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row>
                    <b-col cols="3" style="margin-top:1vh;">
                        <div class="overflow-auto" style="height: 75vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox v-if="selectedMsg === msg" class="selectedMsg" :msg="msg"></MessageBox>
                                <MessageBox v-else :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="8" style="margin-top:2vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 75vh;"></MessageContentBox>
                        <b-container v-else fluid class="card" style="padding:0.5vh; height: 75vh;">
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
                                <MessageBox v-if="selectedMsg === msg" class="selectedMsg" :msg="msg"></MessageBox>
                                <MessageBox v-else :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="7" style="margin-top:2vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 82vh;"></MessageContentBox>
                        <b-container v-else fluid class="card" style="padding:0.5vh; height: 82vh;">
                            <h4 style="margin-top: 15vh">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Weired Tablets Version -->
        <div class="inBetween">
            <b-container fluid style="margin-left: 3vw; padding: 0">
                <b-row>
                    <b-col cols="4" style="margin-top:2vh;">
                        <div class="overflow-auto" style="height: 70vh">
                            <div v-for="msg in messages" :key="msg.id"
                                 @click="selectMsg(msg)"
                                 style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                                <MessageBox v-if="selectedMsg === msg" class="selectedMsg" :msg="msg"></MessageBox>
                                <MessageBox v-else :msg="msg"></MessageBox>
                            </div>
                        </div>
                    </b-col>

                    <b-col cols="7" style="margin-top:3vh;">
                        <MessageContentBox v-if="isSelected()" :msg="selectedMsg"
                                           style="height: 70vh;"></MessageContentBox>
                        <b-container v-else fluid class="card" style="padding:0.5vh; height: 70vh;">
                            <h4 style="margin-top: 15vh">
                                {{ $t('MessagePage.nonSelected') }}
                            </h4>
                        </b-container>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Mobile Version -->
        <div class="mobile"></div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import MessageBox from "@/main/vue/components/messagePage/MessageBox";
import Footer from "@/main/vue/components/Footer";
import MessageContentBox from "@/main/vue/components/messagePage/MessageContentBox";

import {mapState} from 'vuex';
import _ from "lodash";

export default {
    name: "MessagePage",
    components: {MessageContentBox, Footer, MessageBox, Header},
    methods: {
        selectMsg(msg) {
            this.$store.dispatch('patchChangeSelectedMsg', msg)
            this.$store.dispatch('patchChangeWatchedStatus', msg)

        },
        isSelected() {
            return !_.isEmpty(this.selectedMsg);
        }
    },
    computed: {
        ...mapState({
            messages: state => state.messages.messages,
            selectedMsg: state => state.messages.selectedMsg
        })
    }
}
</script>

<style scoped>
.selectedMsg {
    background-color: var(--light-grey);
}

/* Bootstrap vue standard breaks */

/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    .desktop {
        display: none;
    }

    .tablet {
        display: none;
    }

    .inBetween {
        display: none;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    .desktop {
        display: none;
    }

    .tablet {
        display: none;
    }

    .inBetween {
        display: none;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 645px) {
    .mobile {
        display: none;
    }

    .desktop {
        display: none;
    }

    .inBetween {
        display: none;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 654px) {
    .mobile {
        display: none;
    }

    .desktop {
        display: none;
    }

    .tablet {
        display: none;
    }
}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    .mobile {
        display: none;
    }

    .tablet {
        display: none;
    }

    .inBetween {
        display: none;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    .mobile {
        display: none;
    }

    .tablet {
        display: none;
    }

    .inBetween {
        display: none;
    }
}

</style>
