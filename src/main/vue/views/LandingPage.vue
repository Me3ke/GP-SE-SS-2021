<template>
    <div class="all">
        <div class="above">
            <div class=img-wrap>
                <LandingPageHeader @languageSwitch="switchLanguage"></LandingPageHeader>
                <img v-if="theme === '' " :src="elsaLight" alt="logo" class="header-image"/>
                <img v-else :src="elsaDark" alt="logo" class="header-image"/>
            </div>

            <div class="button-division">
                <b-container fluid="100" class=button-Box>
                    <b-row class="text-center" align-v="center" align-h="center" cols="3">
                        <b-col sm="auto">
                            <b-button id="registerButton" class="light-btn" @click="routeToRegister">
                                {{ $t('RegisterButton.content') }}
                            </b-button>
                        </b-col>
                        <b-col sm="auto">
                            <b-button id="loginButton" class="elsa-blue-btn" @click="routeToLogin">
                                {{ $t('LoginButton.content') }}
                            </b-button>
                        </b-col>
                    </b-row>
                </b-container>
            </div>
        </div>

        <div class="text-container">
            <div>
                <b-container class="textField">
                    <div class="container-fluid">
                        <div style="margin-bottom: 2vh">
                            <div class="overflow-auto" style="height: 52vh;">
                                <div v-for="paragraph in paragraphs" :key="paragraph.id"
                                     style="position: static; margin-top: 1vh; margin-left: 3vw;">
                                    <div v-if="paragraph.type === 0">
                                        <h1 id="paragraphTitle" style="margin-bottom: 2vh"> {{ paragraph.content }}</h1>
                                    </div>
                                    <div v-if="paragraph.type === 1">
                                        <p class="shortParagraph"> {{ paragraph.content }}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </b-container>
            </div>
        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";
import {mapGetters} from "vuex";

export default {
    name: "LandingPage",
    components: {LandingPageHeader, Footer},
    created() {
        this.switchLanguage()
    },
    methods: {
        routeToLogin() {
            this.$router.push('/' + this.$i18n.locale + '/login')
        },
        routeToRegister() {
            this.$router.push('/' + this.$i18n.locale + '/register')
        },
        switchLanguage() {
            if (this.$i18n.locale === 'de') {
                this.paragraphs = [
                    {
                        id: 0,
                        type: 0,
                        content: "Das ist ELSA"
                    },
                    {
                        id: 1,
                        type: 1,
                        content:
                            "Laden Sie Ihre Dokumente hoch. Tragen Sie beliebige Personen zum Gegenlesen ein und bennenen Sie Ihre" +
                            " Vertragspartner. ELSA wird sie Informieren." +
                            "Legen Sie fest in welcher Reihenfolge Ihre Vertragspartner ein Dokument signieren sollen. " +
                            "ELSA kümmert sich um die Einhaltung."
                    },
                    {
                        id: 2,
                        type: 1,
                        content:
                            "Ihnen sind Unstimmigkeiten in einem Ihrer Dokument aufgefallen? " +
                            "Kein Problem - Laden Sie einfach eine neue Version hoch. " +
                            "ELSA wird alle Beteiligten informieren - auch die schon erfolgten Signaturen wird ELSA für Sie erneut anfordern."
                    },
                    {
                        id: 3,
                        type: 1,
                        content:
                            "Erhalten Sie Benachrichtigungen direkt hier bei ELSA, oder per E-Mail, sobald Sie mit dem Signieren an der Reihe sind."
                    },
                    {
                        id: 4,
                        type: 1,
                        content:
                            "Hinterlassen Sie Kommentare, um etwaige Unstimmigkeiten aufzudecken und Eigentümer zu informieren."
                    },
                    {
                        id: 5,
                        type: 1,
                        content:
                            "Fordern Sie ein Protokoll an, um alle Informationen zu einem Dokument festzuhalten.\n" +
                            "Ganz Bequem mit einem Klick."
                    },
                    {
                        id: 6,
                        type: 1,
                        content:
                            "ELSA - Einfach. Schnell. Sicher."
                    },
                    {
                        id: 7,
                        type: 1,
                        content:
                            "\"Digitales Signieren für alle.\""
                    },
                    {
                        id: 8,
                        type: 0,
                        content: "Wie funktioniert das digitale Unterschreiben mit ELSA?"
                    },
                    {
                        id: 9,
                        type: 1,
                        content:
                            "Um die Sicherheit einer tatsächlichen Unterschrift zu gewährleisten, wird für die Unterschriften mit ELSA" +
                            " das Public-Key-Verfahren genutzt." +
                            "Im Detail bedeutet das, dass für jedes Dokument, nachdem es hochgeladen wurde, ein bestimmter Wert festgelegt " +
                            "wird. Dieser wird durch eine sehr lange Zeichenkette dargestellt, den sogenannten Hash-Wert."
                    },
                    {
                        id: 10,
                        type: 1,
                        content:
                            "Dadurch wird jedes Dokument eindeutig repräsentiert. Wenn Samuel Ignatory, ein Nutzer von ELSA, eine Unterschrift" +
                            " zu einem Dokument tätigen möchte, wird für seine Unterschrift der Hash-Wert verschlüsselt. Die Verschlüsselung wird" +
                            " mit einen privaten Schlüssel, den nur Samuel kennt, durchgeführt. Da der Hash-Wert sich eindeutig auf das Dokument" +
                            " bezieht und auch der Schlüssel der zur Verschlüsselung genutzt wurde nur Samuel bekannt ist, kann die Unterschrift," +
                            " die durch den verschlüsselten " +
                            "Hash-Wert dargestellt wird, nur schwer gefälscht werden."
                    },
                    {
                        id: 11,
                        type: 1,
                        content: "Damit niemand seine Unterschrift fälschen kann, wird von ELSA ein sogenannter Public-Key verlangt;" +
                            " Ein Schlüssel, der genau das Entschlüsseln kann, was mit dem privaten Schlüssel von Samuel verschlüsselt" +
                            " wurde. Dank all dieser Faktoren folgen Unterschriften hier bei ELSA einem sicheren Standard und Samuel kann" +
                            " beruhigt seine Geschäfte auch digital abschließen."
                    },
                ]
            } else {
                this.paragraphs = [
                    {
                        id: 0,
                        type: 0,
                        content: "This is ELSA"
                    },
                    {
                        id: 1,
                        type: 1,
                        content:
                            "Upload your documents, roster any person to counter-check and designate your affiliates. " +
                            "ELSA will inform you. " +
                            "Set the order which determines whose turn it is to sign; " +
                            "ELSA will surely take care of the adherence."
                    },
                    {
                        id: 2,
                        type: 1,
                        content:
                            "Noticed any issues in your documents? " +
                            "Do not worry; Just upload a new version. " +
                            "ELSA does inform every associate - even the received signatures will be requested anew by ELSA."
                    },
                    {
                        id: 3,
                        type: 1,
                        content:
                            "Receive your notifications, via ELSA or by email, as soon as it is your turn to sign."
                    },
                    {
                        id: 4,
                        type: 1,
                        content:
                            "Post comments to detect any discrepancies and notify the owner."
                    },
                    {
                        id: 5,
                        type: 1,
                        content:
                            "Request the protocol to access the information about a document.\n" +
                            "With just a simple click."
                    },
                    {
                        id: 6,
                        type: 1,
                        content:
                            "ELSA - Simple. Swift. Secure."
                    },
                    {
                        id: 7,
                        type: 1,
                        content:
                            "\"Digital signing for everyone.\""
                    },
                    {
                        id: 8,
                        type: 0,
                        content: "How does the digital signing with ELSA work?"
                    },
                    {
                        id: 9,
                        type: 1,
                        content:
                            "To guarantee the security of an actual signature for its digital counterpart, " +
                            "ELSA makes uses of the so called RSA-Keypair technique. " +
                            "In detail this means the following: Every document receives a unique value after the upload, a so called hash-value. " +
                            "This hash value is represented by a long character string."
                    },
                    {
                        id: 10,
                        type: 1,
                        content:
                            "This procedure guarantees the uniqueness of every document. If Samuel Ignatory, a fictional user of ELSA, wants " +
                            "to sign any document, then the hash value of his digital signature will be encrypted. The encryption is performed " +
                            "with a private key which is only known by Samuel. Since the hash value of the document is uniquely bound to the document " +
                            "and the key used to encrypt is only known by Samuel, the digital signature can hardly be replicated by an outsider."
                    },
                    {
                        id: 11,
                        type: 1,
                        content: "To ensure the correct kesy was used for the enryption, ELSA demands a so called public-key before the signing process. " +
                            "This key is the only one which can be used to decrypt the signature of Samuel. " +
                            "Thanks to those safety procedures ELSA can provide a high security standard and Samuel " +
                            "is able to do his signing business in the digital world."
                    },
                ]
            }
        }
    },
    data: function () {
        return {
            elsaLight: require('../assets/logos/ELSA_big.svg'),
            elsaDark: require('../assets/logos/ELSA_big_darkmode.svg'),
            paragraphs: null
        }
    },
    computed: {
        ...mapGetters({
            theme: 'theme/getTheme'
        }),
    }

}
</script>

<style scoped>
.above {

}

.all {
    background: linear-gradient(90deg,  var(--elsa-blue-transparent) 0%,
    var(--whitesmoke) 13%,
var(--whitesmoke) 50%,
    var(--whitesmoke) 85%,
    var(--elsa-blue-transparent) 100%);
    background-size: cover;
    height: 100vh;
}

.header-image {
    padding-top: 2vw;
    padding-bottom: 2vw;
    height: auto;
}

.button-Box {
    margin: 0;
    padding-bottom: 0;
}

.button-division {
    margin: 0;
    width: 100%;
    background-color: var(--elsa-blue-transparent);
    border: 1px solid var(--elsa-blue);
}

#registerButton, #loginButton {
    padding: 0.25em 0.25em 0;
    border: 0.03em solid var(--dark-grey);
    width: 7em;
    margin-top: 0.5em;
    margin-bottom: 0.5em;
}

#registerButton :focus, . #loginButton :focus {
    border: 0.03vw solid var(--dark-grey);
}

#registerButton {
    background-color: var(--elsa-blue-transparent);
}

#registerButton:hover {
    background-color: var(--light-grey);
}


.text-container {
    background-attachment: fixed;
    background-size: cover;
    display: grid;
    align-items: center;
    justify-content: center;
}

.textField {
    padding-top: 2rem;
    margin: 1rem 1.7vw;
    box-shadow: 0 0 1rem 0 var(--elsa-blue-transparent);
    border-radius: 5px;
    position: relative;
    z-index: 1;
    border: 1px solid var(--elsa-blue);
}

.textField:before {
    content: "";
    position: absolute;
    background: inherit;
    z-index: -1;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    box-shadow: inset 0 0 2000px rgba(255, 255, 255, .5);
    filter: blur(10px);
    margin: -20px;
}

.shortParagraph {
    text-align: left;
}


@media screen and (min-width: 1191px) {
    .header-image {
        width: 28em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 2em;
    }

    #loginButton, #registerButton {
        font-size: 1.75em;
    }
}


@media screen and (min-width: 992px) and (max-width: 1190px) {
    .header-image {
        width: 28em;
    }

    .shortParagraph {
        font-size: 1.5em;
    }

    #paragraphTitle {
        font-size: 2.3em;
    }

    #loginButton, #registerButton {
        font-size: 1.75em;
    }
}

@media screen and (min-width: 768px) and (max-width: 991px) {
    .header-image {
        width: 27em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 2em;
    }

    #loginButton, #registerButton {
        font-size: 1.75em;
    }
}

@media screen and (max-width: 767px) and (min-width: 501px) {
    .header-image {
        width: 27em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 2em;
    }

    #loginButton {
        font-size: 1em;
    }

    #registerButton {
        font-size: 1em;
    }
}

@media screen and (max-width: 500px) and (min-width: 459px) {
    .header-image {
        width: 27em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 2em;
    }

    #loginButton {
        font-size: 1em;
    }

    #registerButton {
        font-size: 1em;
    }
}

@media screen and (min-width: 410px) and (max-width: 458px) {
    .header-image {
        width: 25em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 1.8em;
    }

    #loginButton {
        font-size: 0.9em;
    }

    #registerButton {
        font-size: 0.9em;
    }
}

@media screen and (max-width: 409px) and (min-width: 294px) {
    .header-image {
        width: 11em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 1.8em;
    }

    #loginButton {
        font-size: 0.8em;
    }

    #registerButton {
        font-size: 0.8em;
    }
}

@media screen and (max-width: 293px) {
    .header-image {
        width: 12em;
    }

    .shortParagraph {
        font-size: 1em;
    }

    #paragraphTitle {
        font-size: 2em;
    }

    #loginButton {
        font-size: 0.7em;
    }

    #registerButton {
        font-size: 0.7em;
    }
}
</style>
