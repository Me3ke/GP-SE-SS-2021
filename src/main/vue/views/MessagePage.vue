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
                            <MessageBox :msg="msg"></MessageBox>
                        </div>
                    </div>
                </b-col>

                <b-col cols="8" style="margin-top:2vh;">
                    <MessageContentBox v-if="selected" :msg="selectedMsg" style="height: 85vh;"></MessageContentBox>
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

export default {
    name: "MessagePage",
    components: {MessageContentBox, Footer, MessageBox, Header},
    props: {
        selectedMsg: Object,
        selected: Boolean
    },
    data() {
        return {
            unwatchedMsgs: [],
            "messages": [
                {
                    "id": 0,
                    "sentBy": "superMail@mailService.de",
                    "category": "Reminder",
                    "dateSent": "30.04.2021",
                    "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "False",
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
                    "watched": "True",
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
                    "watched": "True",
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
        selectMsg(msg) {
            this.selectedMsg = msg
        }
    },
    mounted() {
        this.unwatchedMsgs = this.messages.filter(msg => msg.watched === "False")
    }
}
</script>

<style scoped>

</style>
