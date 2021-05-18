<template>
  <div class= "background">
    <div class = img-wrap>
      <LandingPageHeader></LandingPageHeader>
      <img :src = "image"  class="header-image"/>
    </div>

    <div class = "button-division">
      <b-container fluid="100" class = button-Box>
        <b-row class="text-center" align-v="center" align-h="center" cols="3">
          <b-col sm="auto">
            <b-button id ="loginButton" @click="routeToLogin">{{$t('LoginButton.content')}}</b-button>
          </b-col>
          <b-col sm="auto">
            <b-button id ="registerButton" @click="routeToRegister">{{$t('RegisterButton.content')}}</b-button>
          </b-col>
        </b-row>
      </b-container>
    </div>

    <div class = "textField">
      <b-container>
        <div class="container-fluid">
          <div style="margin-bottom: 2vh">
            <div class="overflow-auto" style="height: 76.75vh">
              <div v-for="paragraph in paragraphs" :key="paragraph.id" style="position: static; margin-top: 1vh; margin-left: 3vw;">
                <div v-if="paragraph.type === 0">
                  <h1 id="paragraphTitle" style="margin-bottom: 2vh"> {{paragraph.content}}</h1>
                </div>
                <div v-if="paragraph.type === 1">
                  <p class="shortParagraph"> {{paragraph.content}}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </b-container>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>

import image from "../assets/logos/ELSA_big.svg";
import Footer from "@/main/vue/components/Footer";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";

export default {
  name: "LandingPage",
  components: {LandingPageHeader, Footer},
  methods:{
    routeToLogin(){
      this.$router.push('/'+this.$i18n.locale + '/login')
    },
    routeToRegister(){
      this.$router.push('/'+this.$i18n.locale + '/register')
    }
  },
  data: function () {
    return {
      image: image,
      paragraphs:[
        {
          id:0,
          type: 0,
          content:"Das ist ELSA"
        },
        {
          id:1,
          type: 1,
          content:
              "Laden Sie Ihre Dokumente hoch. Tragen Sie beliebige Personen zum gegenlesen ein und bennenen Sie Ihre registrierten" +
              " Vertragspartner. ELSA wird sie Informieren."+
              "Legen sie fest in welcher Reihenfolge Ihre Vertragspartner ein Dokument signieren sollen. "+
              "ELSA kümmert sich um die Einhaltung."
        },
        {
          id:2,
          type: 1,
          content:
              "Ihnen sind Unstimmigkeiten in einem Ihrer Dokument aufgefallen? " +
              "Kein Problem - Laden Sie einfach eine neue Version hoch. " +
              "ELSA wird alle Beteiligten informieren - auch die schon erfolgten Signaturen wird ELSA für Sie erneut anfordern."
        },
        {
          id:3,
          type: 1,
          content:
              "Erhalten Sie Benachrichtigungen direkt hier bei ELSA, oder per E-Mail, sobald Sie mit dem Signieren an der Reihe sind."
        },
        {
          id:4,
          type: 1,
          content:
              "Hinterlassen Sie Kommentare, um etwaige Unstimmigkeiten aufzudecken und Eigentümer zu informieren."
        },
        {
          id:5,
          type: 1,
          content:
              "Fordern Sie ein Protokoll an, um alle Informationen zu einem Dokument festzuhalten.\n"+
              "Ganz Bequem mit einem Klick."
        },
        {
          id:6,
          type: 1,
          content:
              "ELSA - Einfach. Schnell. Sicher."
        },
        {
          id:7,
          type: 1,
          content:
              "\"Digitales signieren für alle.\""
        },
        {
          id:8,
          type: 0,
          content:"Wie funktioniert das digitale Unterschreiben mit ELSA?"
        },
        {
          id:9,
          type: 1,
          content:
              "Um die Sicherheit einer tatsächlichen Unterschrift zu gewährleisten, wird für die Unterschriften mit ELSA" +
              " das Public-Key-Verfahren genutzt."+
              "Im Detail bedeutet das, dass für jedes Dokument, nachdem es hochgeladen wurde, ein bestimmter Wert festgelegt " +
              "wird. Dieser wird durch eine sehr lange Zeichenkette dargestellt, den sogenannten Hash-Wert."
        },
        {
          id:10,
          type: 1,
          content:
              "Dadurch wird jedes Dokument eindeutig repräsentiert. Wenn Samuel Ignatory, ein Nutzer von ELSA, eine Unterschrift" +
              " zu einem Dokument tätigen möchte, wird für seine Unterschrift der Hash-Wert verschlüsselt. Die Verschlüsselung wird" +
              " mit einen privaten Schlüssel, den nur Samuel kennt, durchgeführt. Da der Hash-Wert sich eindeutig auf das Dokument" +
              " bezieht und auch der Schlüssel der zur Verschlüsselung genutzt wurde nur Samuel bekannt ist, kann die Unterschrift," +
              " die durch den Verschlüsselten " +
              "Hash-Wert dargestellt wird, nur schwer gefälscht werden."
        },
        {
          id:11,
          type: 1,
          content:"Damit niemand seine Unterschrift fälschen kann, wird von ELSA ein sogenannter Public-Key verlangt;" +
              " Ein Schlüssel,der genau das entschlüsseln kann, was mit dem privaten Schlüssel von Samuel verschlüsselt" +
              " wurde. Dank all dieser Faktoren folgen Unterschriften hier bei ELSA einem sicheren Standard und Samuel kann" +
              " beruhigt seine Geschäfte auch digital abschließen."
        },
      ]
    }
  }
}
</script>

<style scoped>
.background {
  padding: 0;
  margin: 0;
  width: 100%;
  min-height: 100vh;
  background-image: linear-gradient(to bottom, rgba(255,153,153,0) 0%,rgba(255,255,255,1) 30%, rgb(255, 255, 255) 100%), url(../assets/background.png);
  background-repeat: no-repeat;
  background-size: 100% auto;
}

.header-image{
  padding-top: 2vw;
  padding-bottom: 2vw;
  width: 25%;
  height: auto;
}
.button-Box{
  padding-top: 1vh;
  margin: 0;
  padding-bottom: 0vh;
}
.button-division{
  padding: 2vh;
  margin: 0;
  width: 100%;
  border-top-width: 1px;
  border-top-style: solid;
  border-top-color: rgba(0,0,0,0.3);
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-bottom-color: rgba(0,0,0,0.3);
  background-color: rgba(0,0,0,0.05);
  background-size: 100% auto;
}

#registerButton{
  color: #2d2d2d;
  background-color: rgba(0,0,0,0);
  margin-right: 0.5vh;
}
#registerButton{
  margin-left: 0.5vh;
}
.textField{
  background-color: rgba(255,255,255,0.3);
  background-size: 100% auto;
  padding-top: 2vw;
  margin-left: 5vw;
  margin-right: 5vw;
}
.shortParagraph{
  text-align: left;
}


@media screen and (min-width: 1200px){
  .header-image {
    width: 16em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}

@media screen and (min-width: 1191px) and (max-width: 1199px) {
  .header-image {
    width: 16em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}



@media screen and (min-width: 992px) and (max-width: 1190px) {
  .header-image {
    width: 16em ;
  }
  .shortParagraph {
    font-size: 1.5em;
  }
  #paragraphTitle {
    font-size: 1.5em  ;
  }
  #loginButton {
    font-size: 1.5em;
  }
  #registerButton {
    font-size: 1.5em;
  }
}

@media screen and (min-width: 801px) and (max-width: 991px) {
  .header-image {
    width: 15em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}


@media screen and (min-width: 768px) and (max-width: 800px) {
  .header-image {
    width: 15em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}

@media screen and (max-width: 767px) and (min-width: 501px) {
  .header-image {
    width: 15em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
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
    width: 15em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
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
    width: 13em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1em  ;
  }
  #loginButton {
    font-size: 0.9em;
  }
  #registerButton {
    font-size: 0.9em;
  }
}

@media screen and (max-width: 409px) and (min-width: 355px) {
  .header-image {
    width: 13em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1em  ;
  }
  #loginButton {
    font-size: 0.9em;
  }
  #registerButton {
    font-size: 0.9em;
  }
}

@media screen and (max-width: 354px) and (min-width: 330px){
  .header-image {
    width: 15em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}

@media screen and (max-width: 329px) {
  .header-image {
    width: 12em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1em  ;
  }
  #loginButton {
    font-size: 0.8em;
  }
  #registerButton {
    font-size: 0.8em;
  }
}

@media screen and (max-width: 304px) and (min-width: 294px) {
  .header-image {
    width: 11em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1em  ;
  }
  #loginButton {
    font-size: 0.8em;
  }
  #registerButton {
    font-size: 0.8em;
  }
}

@media screen and (max-width: 293px) and (min-width: 289px) {
  .header-image {
    width: 12em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 0.7em;
  }
  #registerButton {
    font-size: 0.7em;
  }
}

@media screen and (max-width: 288px) and (min-width: 240px) {
  .header-image {
    width: 11em ;
  }
  .shortParagraph {
    font-size: 0.7em;
  }
  #paragraphTitle {
    font-size: 0.8em  ;
  }
  #loginButton {
    font-size: 0.7em;
  }
  #registerButton {
    font-size: 0.7em;
  }
}

@media screen and (max-width: 239px) {
  .header-image {
    width: 9em ;
  }
  .shortParagraph {
    font-size: 1em;
  }
  #paragraphTitle {
    font-size: 1.2em  ;
  }
  #loginButton {
    font-size: 1em;
  }
  #registerButton {
    font-size: 1em;
  }
}

</style>