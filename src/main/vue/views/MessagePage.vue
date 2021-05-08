<template>
    <div>
        <Header></Header>

        <div class="container-fluid">
            <div style="margin-top:1vh; margin-right: 70vw">
                <div class="overflow-auto" style="height: 85vh">
                    <div v-for="msg in messages" :key="msg.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <MessageBox :msg="msg"></MessageBox>
                    </div>
                </div>
            </div>
        </div>


        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import MessageBox from "@/main/vue/components/MessageBox";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "MessagePage",
    components: {Footer, MessageBox, Header},
    data() {
        return {
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
    }
}
</script>

<style scoped>

</style>
