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

        <b-dropdown-item class="my-dropdown-item" v-for="msg in messages" :key="msg.id"
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

            <!-- For checked messages -->
            <b-icon v-else-if="isHovered === msg.id && msg.category === 'Checked'" icon="file-earmark-check"
                    class="my-icon-hovered"
                    font-scale="2"></b-icon>
            <b-icon v-else-if="msg.category === 'Checked'" icon="file-earmark-check" class="my-icon"
                    font-scale="2"></b-icon>

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
        <b-dropdown-item class="my-dropdown-item" style="text-align: center">
            <span>  {{ $t('Header.Messages.show') }}</span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
import i18n from '@/i18n'

export default {
    name: "Messages",
    data() {
        return {
            isHovered: -1,
            "messages": [
                {
                    "id": 0,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "correspondingDocument": {
                        "id": "00",
                        "title": "Mein super Dokument",
                        "owner": "Ain Owner"
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
                        "title": "Mein besseres Dokument",
                        "owner": "Ian Owner"
                    }
                },
                {
                    "id": 2,
                    "sentBy": "besteMail@mailService.de",
                    "category": "Checked",
                    "dateSent": "21.04.2021",
                    "content": "Das folgende Dokument wurde erfolgreich gegengelesen.",
                    "correspondingDocument": {
                        "id": "22",
                        "title": "Mein bestes Dokument",
                        "owner": "Nai Owner"
                    }
                }
            ]
        }
    },
    methods: {
        handleHover(ele) {
            this.isHovered = ele
        },
        showMsg(msg) {
            this.$swal.fire({
                titleText: msg.correspondingDocument.title,
                text: msg.content,
                customClass: {
                    titleText: 'var(--elsa-blue)',
                },
                showCloseButton: true,
                confirmButtonText: i18n.t('Header.Messages.toDoc'),
                confirmButtonColor: 'var(--dark-grey)'
            }).then((result) => {
                if (result['isConfirmed']) {
                    // To-Do: Add correct link
                    this.$router.push('/')
                }
            })
        }
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
