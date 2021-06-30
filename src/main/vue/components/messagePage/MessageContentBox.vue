<template>
    <b-container fluid class="card"
                 style="padding:0.5vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
        <div class="media">
            <div class="media-body">
                <b-container fluid style="overflow:hidden;">
                    <b-row cols="2">
                        <b-col cols="10" align-self="center">
                            <h4 style="text-align: left;">
                                {{ msg.subject }}
                            </h4>
                        </b-col>
                        <b-col align-self="end" cols="2">
                            <!-- Desktop Delete -->
                            <h4 style="text-align: right" v-if="!noPopover">
                                <b-icon class="my-icon" icon="trash" scale="0.5" id="trash-popover"></b-icon>
                                <b-popover target="trash-popover" triggers="click" placement="bottomleft">
                                    <!-- Error Messages -->
                                    <b-alert :show="showDeleteError"
                                             style="margin-bottom: 1em">
                                        {{ $t('MessagePage.deleteError') }}
                                    </b-alert>

                                    <div style="text-align: center">
                                        {{ $t('MessagePage.sureDelete') }}
                                    </div>

                                    <div style="text-align: center">
                                        <button type="button" class="light-btn"
                                                @click="$root.$emit('bv::hide::popover', 'trash-popover')">
                                                <span class="button-txt">
                                                    {{ $t('MessagePage.cancel') }}
                                                </span>
                                        </button>
                                        <button type="button" class="elsa-blue-btn" @click="deleteMsg">
                                                <span class="button-txt">
                                                    {{ $t('MessagePage.delete') }}
                                                </span>
                                        </button>
                                    </div>
                                </b-popover>
                            </h4>

                            <!-- Mobile Delete -->
                            <h4 style="text-align: right" v-else>
                                <b-icon class="my-icon" icon="trash" scale="0.5" @click="showPoUp = true"></b-icon>
                                <DeleteMessageMobile v-if="showPoUp"
                                                     @closeTrigger="showPoUp = false"></DeleteMessageMobile>
                            </h4>

                        </b-col>
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

                    <hr style="width: 100%; background-color: var(--dark-grey)">

                    <b-row style="margin-top: 3vh; overflow:hidden; display: flex; text-align: left">
                        <div class="col-auto">
                            <h6 v-html=" msg.text">
                            </h6>
                        </div>
                    </b-row>
                </b-container>
            </div>
        </div>
    </b-container>
</template>

<script>
import {mapGetters} from "vuex";
import DeleteMessageMobile from "@/main/vue/components/popUps/DeleteMessageMobile";

export default {
    name: "MessageContentBox",
    components: {DeleteMessageMobile},
    props: {
        msg: Object
    },
    data() {
        return {
            noPopover: window.innerWidth < 992,
            showPoUp: false,
            showDeleteError: false
        }
    },
    mounted() {
        // reacts when screen size changes
        window.addEventListener("resize", this.updateNoPopover);
    },
    destroyed() {
        // removes event listener
        window.removeEventListener("resize", this.updateNoPopover);
    },
    methods: {
        updateNoPopover() {
            // sets noPopover depending on screen width (if smaller than 992 popOver does not work anymore -> modal instead)
            this.noPopover = window.innerWidth < 992
        },
        getDate() {
            return this.msg.timeStamp.split('T')[0]
        },
        // deletes current message
        async deleteMsg() {
            await this.$store.dispatch('messages/patchDeleteMessage')
            if (this.hasError()) {
                this.showDeleteError = true
            } else {
                await this.$store.dispatch('messages/patchChangeSelectedMsg', {})
                await this.$store.dispatch('messages/fetchMessages')
            }
        },
        hasError() {
            return this.deleteRes.status !== 200
        }
    },
    computed: {
        ...mapGetters({
            deleteRes: 'messages/getDeleteResponse'
        })
    }
}
</script>

<style scoped>

.my-icon {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
    cursor: pointer;
}

.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}

.button-txt {
    float: left;
}

/* Buttons */
.elsa-blue-btn, .light-btn {
    padding: 0.25em 1em 0;
    border: 0.03em solid var(--dark-grey);
    margin: 0.5em 0.15em 0;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}


/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h4 {
        font-size: .62em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .43em;
        text-overflow-mode: ellipse;
    }

    .button-txt {
        font-size: 0.52em;
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

    .button-txt {
        font-size: .32em;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h4 {
        font-size: .77em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .54em;
        text-overflow-mode: ellipse;
    }

    .button-txt {
        font-size: .52em;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h4 {
        font-size: 1.2em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .87em;
        text-overflow-mode: ellipse;
    }

    .button-txt {
        font-size: 1em;
    }
}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    h4 {
        text-overflow-mode: ellipse;
    }

    h6 {
        text-overflow-mode: ellipse;
    }

    .button-txt {
        font-size: 0.8em;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    h4 {
        text-overflow-mode: ellipse;
    }

    h6 {
        text-overflow-mode: ellipse;
    }

    .button-txt {
        font-size: 0.8em;
    }
}
</style>
