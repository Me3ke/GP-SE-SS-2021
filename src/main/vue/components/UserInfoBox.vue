<template>
  <b-container fluid="100">

    <b-row class="text-center" align-v="center" align-h="center">
      <div class="card">
        <div class="card-header">
          {{$t('UserInfoBox.profileInformation')}}
        </div>
        <b-list-group flush>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.email')}} {{this.userInformation.email}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.firstName')}} {{this.userInformation.firstname}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.lastName')}} {{this.userInformation.lastname}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.birthday')}} {{this.userInformation.birthday}}</b-list-group-item>
        </b-list-group>
      </div>
    </b-row>

    <b-row class="text-center" align-v="center" align-h="center">
      <div class="card">
        <div class="card-header">
          {{$t('UserInfoBox.accountInformation')}}
        </div>
        <b-list-group flush>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.phoneNumber')}} {{this.userInformation.phoneNumber}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.country')}} {{this.userInformation.country}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.city')}} {{this.userInformation.hometown}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.street')}} {{this.userInformation.street}}</b-list-group-item>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.houseNumber')}} {{this.userInformation.houseNumber}}</b-list-group-item>
        </b-list-group>
      </div>
    </b-row>

    <b-row class="text-center" align-v="center" align-h="center">
      <div class="card">
        <div class="card-header">
          {{$t('UserInfoBox.publicKeys')}}
        </div>
        <b-list-group flush>
          <b-list-group-item style="text-align: left">{{$t('UserInfoBox.publicKey')}} {{this.userInformation.publicKey}}</b-list-group-item>
        </b-list-group>
      </div>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "UserInfoBox",
  props: {
    userInformation: Object,
  }
}
</script>

<style scoped>

.card{
  width: 40vw;
  margin-bottom: 5vh;
}
</style>