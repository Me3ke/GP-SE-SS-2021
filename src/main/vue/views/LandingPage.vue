<template>
  <div class= "background">
    <div class = img-wrap>
      <LandingPageHeader></LandingPageHeader>
      <img :src = "image"  class="header-image"/>
    </div>

    <div class = "button-division">
      <b-container fluid="100" class = button-Box>
        <b-row class="text-center" align-v="center" align-h="center" cols="3">
          <b-col cols="3" sm="auto">
            <b-button id ="loginButton" @click="routeToLogin">{{$t('LoginButton.content')}}</b-button>
          </b-col>
          <b-col cols="3" sm="auto">
            <b-button id ="registerButton" @click="routeToRegister">{{$t('RegisterButton.content')}}</b-button>
          </b-col>
        </b-row>
      </b-container>
    </div>

    <div class = "textField">
      <b-container>
        <div class="container-fluid">
          <div style="margin-top:0vh">
            <div class="overflow-auto" style="height: 76.75vh">
              <div v-for="paragraph in paragraphs" :key="paragraph.id" style="position: static; margin-top: 1vh; margin-left: 3vw;">
                <div v-if="paragraph.type === 0">
                  <h1 id="paragraphTitle" style="margin-bottom: 2vw"> {{paragraph.content}}</h1>
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
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              " sed diam nonumy eirmod tempor invidunt ut l"
        },
        {
          id:2,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
          "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
          "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
          "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
          "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:3,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:4,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:5,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:6,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:7,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:8,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:9,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        },
        {
          id:10,
          type: 1,
          content:"Lorem ipsum dolor sit amet, consetetur sadipscing elitr," +
              "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna"+
              "aliquyam erat, sed diam voluptua. At vero eos et accusam et justo"+
              "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata"+
              "sanctus est Lorem ipsum dolor sit amet. Lorem 187 dolor sit amet,"
        }
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
</style>