<template>
    <b-container fluid :class="selected(msg.messageID) ? 'card message-box selected-box' : 'card message-box'">
        <div class="media">

            <!-- For admin stuff-->
            <b-iconstack v-if="msg.category === 'TODO'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-person" scale="1"></b-icon>
                <b-icon v-if="msg.watched===false" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For sign stuff -->
            <b-iconstack v-else-if="msg.category === 'SIGN'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="pen-fill" scale="0.5" shift-v="-2"
                        shift-h="4.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched===false" stacked icon="exclamation-circle-fill" class="my-icon"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For proofread stuff -->
            <b-iconstack v-else-if="msg.category === 'READ'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="eye-fill" scale="0.5" shift-v="-4"
                        shift-h="3.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched===false" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- Document made Progress -->
            <b-iconstack v-else-if="msg.category === 'PROGRESS'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon v-if="msg.watched===false" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- New Version of Document -->
            <b-iconstack v-else font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="arrow-clockwise" scale="0.7" shift-v="-0.4"
                        rotate="45"></b-icon>
                <b-icon v-if="msg.watched===false" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <div class="media-body">
                <b-container fluid style="overflow:hidden">
                    <b-row align-h="start">
                        <div class="col-auto">
                            <b-row align-h="between">
                                <div class="col-auto">
                                    <h4>
                                        {{ msg.subject }}
                                    </h4>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6 v-if="msg.sendingUser !== null">
                                        {{ $t('MessagePage.sentBy') }}: {{ msg.sendingUser.firstname }}
                                        {{ msg.sendingUser.lastname }}
                                    </h6>
                                    <h6 v-else>
                                        {{ $t('MessagePage.sentBy') }}: System
                                    </h6>
                                </div>
                                <div class="col-auto">
                                    <h6>
                                        {{ $t('MessagePage.sentOn') }}: {{ getDate() }}
                                    </h6>
                                </div>
                            </b-row>
                        </div>
                    </b-row>
                </b-container>
            </div>
        </div>
    </b-container>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: 'MessageBox',
    props: {
        msg: Object
    },
    methods: {
        getDate() {
            return this.msg.timeStamp.split('T')[0]
        }
    },
    computed: {
        ...mapGetters({
            selected: 'messages/isSelected'
        })
    },
}
</script>

<style scoped>

.message-box {
    padding: 0.5vh;
    background-color: var(--whitesmoke);
    border-color: var(--dark-grey);
    cursor: pointer
}

.message-box:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}

.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}

.selected-box {
    background-color: var(--light-grey) !important;
}

/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h4 {
        font-size: .52em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .33em;
        text-overflow-mode: ellipse;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    h4 {
        font-size: .52em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .33em;
        text-overflow-mode: ellipse;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h4 {
        font-size: .52em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .33em;
        text-overflow-mode: ellipse;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h4 {
        font-size: .85em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .6em;
        text-overflow-mode: ellipse;
    }
}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    h4 {
        font-size: 0.8em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .47em;
        text-overflow-mode: ellipse;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    h4 {
        font-size: 0.9em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .57em;
        text-overflow-mode: ellipse;
    }
}
</style>
