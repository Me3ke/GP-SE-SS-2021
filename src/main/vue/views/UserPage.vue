<template>
  <div class="background">
    <div>
      <Header> </Header>
    </div>

    <div>
      <b-container fluid style="margin-top:6vh; margin-right:2vw;">
        <b-row class="text-center" align-v="center" align-h="center">
            <h2>
              {{$t('UserPage.content')}}
            </h2>
        </b-row>
      </b-container>
    </div>

  <div>
    <UserInfoBox :user-information="user"></UserInfoBox>
  </div>
    
  </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import UserInfoBox from "@/main/vue/components/UserInfoBox";

export default {
  name: "UserPage",
  components:{Header,UserInfoBox},
  data(){
    return {
      user: {
        email: "sehrTolle@email.com",
        firstname: "Otto",
        lastname: "Wehner",
        street: "Siegwardsweg",
        houseNumber: 42,
        postcode: 55555,
        hometown: "Ownerhausen",
        country: "Deutschland",
        birthday: "30-04-2021",
        phoneNumber: "+49 93483932",
        publicKey: "z10f8dh736rz98712c6tz7r983t"
      }
    }
  }
}
</script>

<style scoped>

.background{
  padding: 0;
  margin: 0;
  width: 100%;
  min-height: 100vh;
  background-image: linear-gradient(to bottom, rgba(255,153,153,0) 0%,rgba(255,255,255,1) 30%, rgb(255, 255, 255) 100%), url(../assets/background.png);
  background-repeat: no-repeat;
  background-size: 100% auto;
}
</style>