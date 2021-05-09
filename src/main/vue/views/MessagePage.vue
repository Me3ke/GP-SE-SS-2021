<template>
    <div>
        <Header></Header>
        <b-container fluid style="margin: 0; padding: 0">
            <b-row>

                <b-col cols="3" style="margin-top:1vh;">
                    <div class="overflow-auto" style="height: 85vh">
                        <div v-for="msg in messages" :key="msg.id"
                             @click="selectMsg(msg)"
                             style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                            <MessageBox v-if="selectedMsg === msg" class="selectedMsg" :msg="msg"></MessageBox>
                            <MessageBox v-else :msg="msg"></MessageBox>
                        </div>
                    </div>
                </b-col>

                <b-col cols="8" style="margin-top:2vh;">
                    <MessageContentBox v-if="isSelected()" :msg="selectedMsg" style="height: 85vh;"></MessageContentBox>
                    <b-container v-else fluid class="card" style="padding:0.5vh; height: 85vh;">
                        <h4 style="margin-top: 15vh">
                            {{ $t('MessagePage.nonSelected') }}
                        </h4>
                    </b-container>
                </b-col>

            </b-row>
        </b-container>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import MessageBox from "@/main/vue/components/MessageBox";
import Footer from "@/main/vue/components/Footer";
import MessageContentBox from "@/main/vue/components/MessageContentBox";

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
</style>
