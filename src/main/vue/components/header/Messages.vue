<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon v-if="count === 0" icon="bell" class="my-icon"></b-icon>
            <b-iconstack v-else class="my-icon">
                <b-icon stacked icon="bell" class="my-icon" scale="0.95"></b-icon>
                <b-icon stacked icon="exclamation-circle-fill" class="my-icon" scale="0.5" shift-v="3"
                        shift-h="-5"></b-icon>
            </b-iconstack>
        </template>

        <!-- If there are no new messages -->
        <b-dropdown-item class="my-dropdown-item-header" v-if="count === 0">
            <b-icon icon="emoji-laughing" class="my-icon" style="fill: var(--whitesmoke)"></b-icon>
            <span class="letters"> {{ $t('Header.Messages.noMsg') }}</span>
        </b-dropdown-item>

        <!--If there are messages -->
        <b-dropdown-item class="my-dropdown-item-header" v-else>
            <b-icon icon="chat-left-dots" class="my-icon" style="fill: var(--whitesmoke)"></b-icon>
            <span class="letters"> {{ $t('Header.Messages.newMsg') }}</span>
        </b-dropdown-item>

        <b-dropdown-divider class="my-divider"></b-dropdown-divider>

        <b-dropdown-item class="my-dropdown-item" v-for="msg in getMessages(7)" :key="msg.id"
                         @click="showMsg(msg)">

            <!-- For reminder messages -->
            <b-iconstack v-if="msg.category === 'Reminder'" class="my-icon">
                <b-icon class="my-icon" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill" class="my-icon"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For signed messages -->
            <b-iconstack v-else-if="msg.category === 'Sign'" class="my-icon">
                <b-icon class="my-icon" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill" class="my-icon"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For checked messages -->
            <b-iconstack v-else-if="msg.category === 'Checked'" class="my-icon">
                <b-icon class="my-icon" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="eye-fill" scale="0.5" shift-v="-4" shift-h="3.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill" class="my-icon"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For updated messages -->
            <b-iconstack v-else class="my-icon">
                <b-icon class="my-icon" stacked icon="file-earmark" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="arrow-clockwise" scale="0.7" shift-v="-0.4" rotate="45"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill" class="my-icon"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For all messages -->
            <span class="letters">{{ msg.dateSent }}: </span>
            <span class="letters">{{ msg.correspondingDocument.title }}</span>
        </b-dropdown-item>

        <b-dropdown-divider class="my-divider"></b-dropdown-divider>

        <!-- Show all messages -->
        <b-dropdown-item class="my-dropdown-item" style="text-align: center"
                         @click="showMsg({})">
            <span>  {{ $t('Header.Messages.show') }}</span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
import {mapGetters} from 'vuex';

export default {
    name: "Messages",
    methods: {
        showMsg: function (msg) {
            //changes selected message and its watched status
            this.$store.dispatch('patchChangeSelectedMsg', msg)
            this.$store.dispatch('patchChangeWatchedStatus', msg)

            // navigates to messages page, passes msg as selectedMsg as prop to MessagePage
            this.$router.push({
                name: 'messages'
            }).catch(e => {
                // Avoids displaying of navigation duplicate error that arises due to the :lang
                if (
                    e.name !== 'NavigationDuplicated' &&
                    !e.message.includes('Avoided redundant navigation to current location')
                ) {
                    console.log(e);
                }
            })
        }
    },
    computed: {
        getMessages() {
            return this.$store.getters.getFirstNMessages
        },
        ...mapGetters({
            count: 'unwatchedCount'
        })
    }
}
</script>

<style scoped>

.letters {
    margin-left: 0.75vw;
    position: relative;
    bottom: 0.5vw;
}

.my-icon {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
}

.my-divider >>> .dropdown-divider {
    margin-top: 0;
    margin-bottom: 0;
}

.my-dropdown-menu >>> .dropdown-menu {
    color: var(--dark-grey);
    padding-top: 0;
    padding-bottom: 0;
    margin: 0;
    border-color: var(--elsa-blue);
}

.my-dropdown-item-header >>> .dropdown-item {
    color: var(--whitesmoke);
    background-color: var(--elsa-blue);
    padding-left: 0.5vw;
    text-align: center;
    opacity: 80%;
}

.my-dropdown-item-header:hover >>> .dropdown-item {
    color: var(--whitesmoke);
    background-color: var(--elsa-blue);
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
    background-color: var(--whitesmoke);
}

.my-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .dropdown-menu > li {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}

@media (min-width: 992px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}
</style>
