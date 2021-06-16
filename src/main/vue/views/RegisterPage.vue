<template>
  <div class=" background">


    <LandingPageHeader></LandingPageHeader>
    <div class=img-wrap>
      <img :src="image" alt="logo" class="header-image"/>
    </div>
    <div class="register-division">
      <register-form></register-form>
    </div>


    <Footer></Footer>
  </div>

</template>

<script>
import Footer from "@/main/vue/components/Footer";
import image from "../assets/logosRequestBody/ELSA_big.svg";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";
import RegisterForm from "../components/RegisterForm";

export default {
  data: function () {
    return {
      image: image
    }
  },
  name: "RegisterPage",
  components: {RegisterForm, LandingPageHeader, Footer},
  methods: {}
}
</script>

<style>

body {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  min-height: 100vh;
  margin: 0;

}
#app {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  min-height: 100vh;
  margin: 0;

}

.background {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  min-height: 100%;
  padding: 0;
  margin: 0;
  background-image: linear-gradient(to bottom, var(--background-fade-one) 0%, var(--background-fade-two) 30%, var(--background-fade-three) 100%), url(../assets/background.png);
  background-repeat: no-repeat;
  background-size: 100% auto;
}

.register-division {
  display: flex;
  flex-flow: column;
  flex-grow: 1;
  min-height: 100%;
  border-top-width: 1px;
  border-top-style: solid;
  border-top-color: var(--shadow-grey);
  background-color: var(--headerFadeOne);
  background-size: 100% auto;
}


.header-image {
  padding-top: 2vw;
  padding-bottom: 2vw;
  width: 25%;
  height: auto;
}
</style>
