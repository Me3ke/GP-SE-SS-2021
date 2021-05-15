<template>
  <div>
    <div>
      <Header> </Header>
    </div>

    <div>
      <b-container fluid style="margin-top:6vh; margin-right:2vw;">
        <b-row class="text-center" align-v="center" align-h="center">
          <b-col col=2>
            <h2>
              {{$t('UserPage.content')}}
            </h2>
          </b-col>
        </b-row>
      </b-container>
    </div>

  <div>
    <b-container>
      <b-row class="text-center" align-v="center" align-h="center">
        <UserInfoBox :user-information="user" style="position: center"></UserInfoBox>
      </b-row>
    </b-container>
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
        publicKey: ""
      }
    }
  }
}
</script>

<style scoped>

</style>