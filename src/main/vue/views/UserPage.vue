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
import {mapState} from "vuex"

export default {
  name: "UserPage",
  components:{Header,UserInfoBox},
  computed: mapState({
    user: state => state.user
  }),
  /*
  methods: {
    ...mapActions([
      'requestUser' //<1>
    ]),
  },
  created() {
    this.id = 'hans.schneider@mail.de'
    this.requestUser(this.id) //<2>
  }
   */
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