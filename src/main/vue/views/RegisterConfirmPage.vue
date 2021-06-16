<template>
  <div class=" background">


    <LandingPageHeader></LandingPageHeader>
    <div class=img-wrap>
      <img :src="image" alt="logo" class="header-image"/>
    </div>
    <div class="register-division">
      Token: {{$route.params.id}}
      <b-alert v-model="success" variant="success">
        Erfolgreich validiert!
        Log dich jetzt ein!
        <b-button variant="outline-primary" @click="routeToLogin">{{$t('Register.login')}}</b-button>
      </b-alert>
      <b-alert style="height: 10rem; display: flex; align-items: center; justify-content: center" v-model="fail" variant="danger">
        Das Token ist abgelaufen oder schon benutzt! Registrieren Sie sich erneut oder wenden Sie sich an Ihren Administrator.
      </b-alert>
    </div>




    <Footer></Footer>
  </div>

</template>

<script>
import Footer from "@/main/vue/components/Footer";
import image from "../assets/logosRequestBody/ELSA_big.svg";
import LandingPageHeader from "@/main/vue/components/header/LandingPageHeader";
import userAPI from "../api/userAPI";

export default {
  data: function () {
    return {
      image: image,
      success: false,
      fail: false,
    }
  },
  name: "RegisterConfirmPage",
  components: {LandingPageHeader, Footer},
  methods: {
    routeToLogin(){
      this.$router.push('/'+this.$i18n.locale + '/login')
    },
    async confirmNewUser() {
      await userAPI.confirmUser(this.$route.params.id)
          .then(response => {
            if (response.data.status == 200) {
              this.success = true;
              this.fail = false;
            } else {
              alert(response.data.status)
              this.success = false;
              this.fail = true;
            }
          })
          .catch(function (error) {
            this.success = false;
            this.fail = true;
            if (error.response) {
              console.log(error.response.data);
              alert("Server fehler:" + error.response.status);
              console.log(error.response.headers);
            }else { alert("else catch")}
          })
    }
  },
  created() {
    this.confirmNewUser();
  }
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
