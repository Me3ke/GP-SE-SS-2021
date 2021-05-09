<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon icon="bell" class="my-icon" font-scale="2"></b-icon>
        </template>

        <!-- If there are no new messages -->
        <b-dropdown-item class="my-dropdown-item-header" v-if="messages.length === 0">
            <b-icon icon="emoji-laughing" class="my-icon-hovered" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Messages.noMsg') }}</span>
        </b-dropdown-item>

        <!--If there are messages -->
        <b-dropdown-item class="my-dropdown-item-header" v-else>
            <b-icon icon="chat-left-dots" class="my-icon-hovered" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Messages.newMsg') }}</span>
        </b-dropdown-item>

        <b-dropdown-divider class="my-divider"></b-dropdown-divider>

        <b-dropdown-item class="my-dropdown-item" v-for="msg in cutOffMessages" :key="msg.id"
                         @mouseover.native="handleHover(msg.id)" @mouseleave.native="handleHover(-1)"
                         @click="showMsg(msg)">

            <!-- For reminder messages -->
            <b-iconstack v-if="isHovered === msg.id && msg.category === 'Reminder'" font-scale="2">
                <b-icon class="my-icon-hovered" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon class="my-icon-hovered" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
            </b-iconstack>
            <b-iconstack v-else-if="msg.category === 'Reminder'" font-scale="2">
                <b-icon class="my-icon" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
            </b-iconstack>

            <!-- For signed messages -->
            <b-iconstack v-else-if="isHovered === msg.id && msg.category === 'Sign'" font-scale="2">
                <b-icon class="my-icon-hovered" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon-hovered" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
            </b-iconstack>
            <b-iconstack v-else-if="msg.category === 'Sign'" font-scale="2">
                <b-icon class="my-icon" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
            </b-iconstack>

            <!-- For checked messages -->
            <b-iconstack v-else-if="isHovered === msg.id && msg.category === 'Checked'" font-scale="2">
                <b-icon class="my-icon-hovered" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon-hovered" stacked icon="eye-fill" scale="0.5" shift-v="-4" shift-h="3.5"
                        rotate="5"></b-icon>
            </b-iconstack>
            <b-iconstack v-else-if="msg.category === 'Checked'" font-scale="2">
                <b-icon class="my-icon" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="eye-fill" scale="0.5" shift-v="-4" shift-h="3.5"
                        rotate="5"></b-icon>
            </b-iconstack>

            <!-- For updated messages -->
            <b-iconstack v-else-if="isHovered === msg.id && msg.category === 'Updated'" font-scale="2">
                <b-icon class="my-icon-hovered" stacked icon="file-earmark" scale="1"></b-icon>
                <b-icon class="my-icon-hovered" stacked icon="arrow-clockwise" scale="0.7" shift-v="-0.4"
                        rotate="45"></b-icon>
                <b-icon class="my-icon-hovered" stacked icon="exclamation" scale="0.5" shift-v="-0.4"></b-icon>
            </b-iconstack>
            <b-iconstack v-else font-scale="2">
                <b-icon class="my-icon" stacked icon="file-earmark" scale="1"></b-icon>
                <b-icon class="my-icon" stacked icon="arrow-clockwise" scale="0.7" shift-v="-0.4" rotate="45"></b-icon>
                <b-icon class="my-icon" stacked icon="exclamation" scale="0.5" shift-v="-0.4"></b-icon>
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
import _ from 'lodash';

export default {
    name: "Messages",
    data() {
        return {
            isHovered: -1,
            cutOffMessages: [],
            "messages": [
                {
                    "id": 0,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument"
                    }
                },
                {
                    "id": 1,
                    "sentBy": "bessereMail@mailService.de",
                    "category": "Updated",
                    "dateSent": "27.04.2021",
                    "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
                    "correspondingDocument": {
                        "id": "11",
                        "title": "Mein besseres Dokument"
                    }
                },
                {
                    "id": 2,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument"
                    }
                },
                {
                    "id": 3,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Sign",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein drittes Dokument"
                    }
                }, {
                    "id": 4,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument"
                    }
                },
                {
                    "id": 5,
                    "sentBy": "bessereMail@mailService.de",
                    "category": "Updated",
                    "dateSent": "27.04.2021",
                    "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
                    "correspondingDocument": {
                        "id": "11",
                        "title": "Mein besseres Dokument"
                    }
                },
                {
                    "id": 6,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument"
                    }
                },
                {
                    "id": 7,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Sign",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein drittes Dokument"
                    }
                },
                {
                    "id": 8,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument"
                    }
                },
                {
                    "id": 9,
                    "sentBy": "bessereMail@mailService.de",
                    "category": "Updated",
                    "dateSent": "27.04.2021",
                    "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
                    "correspondingDocument": {
                        "id": "11",
                        "title": "Mein besseres Dokument"
                    }
                },
                {
                    "id": 10,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument"
                    }
                },
                {
                    "id": 11,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Sign",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein drittes Dokument"
                    }
                },
                {
                    "id": 12,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument"
                    }
                },
                {
                    "id": 13,
                    "sentBy": "bessereMail@mailService.de",
                    "category": "Updated",
                    "dateSent": "27.04.2021",
                    "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
                    "correspondingDocument": {
                        "id": "11",
                        "title": "Mein besseres Dokument"
                    }
                },
                {
                    "id": 14,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument"
                    }
                },
                {
                    "id": 15,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Sign",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein drittes Dokument"
                    }
                },
                {
                    "id": 16,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument"
                    }
                },
                {
                    "id": 17,
                    "sentBy": "bessereMail@mailService.de",
                    "category": "Updated",
                    "dateSent": "27.04.2021",
                    "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
                    "correspondingDocument": {
                        "id": "11",
                        "title": "Mein besseres Dokument"
                    }
                },
                {
                    "id": 18,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument"
                    }
                },
                {
                    "id": 19,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Sign",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein drittes Dokument"
                    }
                }
            ]
        }
    },
    methods: {
        handleHover(ele) {
            this.isHovered = ele
        },
        showMsg: function (msg) {
            // navigates to messages page, passes msg as selectedMsg as prop to MessagePage
            this.$router.push({
                name: 'messages',
                params: {'selectedMsg': msg, 'selected': !_.isEmpty(msg)}
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
    mounted() {
        this.cutOffMessages = this.messages.slice(0, 7)
    }
}
</script>

<style scoped>

@import "../../assets/css/dropdown.css";

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
    color: whitesmoke;
    background-color: var(--elsa-blue);
    padding-left: 0.5vw;
    text-align: center;
    opacity: 80%;
}

.my-dropdown-item-header:hover >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
}

.my-dropdown-item:hover >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
    opacity: 80%;
    transition-duration: 0.4s;
}

.my-dropdown-item:active >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
}
</style>
