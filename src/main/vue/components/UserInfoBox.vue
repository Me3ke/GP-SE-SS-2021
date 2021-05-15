<template>
  <div>

    <div class="card" style="width: 20vw; margin-bottom: 5vh">
      <div class="card-header" style="text-align: left" >
        Profilinformationen
      </div>
      <b-list-group flush>
        <b-list-group-item style="text-align: left">Email: {{this.userInformation.email}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Vorname: {{this.userInformation.firstname}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Nachname: {{this.userInformation.lastname}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Geburtsdatum: {{this.userInformation.birthday}}</b-list-group-item>
      </b-list-group>
    </div>

    <div class="card" style="width: 20vw; margin-bottom: 5vh">
      <div class="card-header" style="text-align: left" >
        Kontoinformationen
      </div>
      <b-list-group flush>
        <b-list-group-item style="text-align: left">Land: {{this.userInformation.country}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Stadt: {{this.userInformation.hometown}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Straße: {{this.userInformation.street}}</b-list-group-item>
        <b-list-group-item style="text-align: left">Hausnummer: {{this.userInformation.houseNumber}}</b-list-group-item>
      </b-list-group>
    </div>

    <div class="card" style="width: 20vw;">
      <div class="card-header" style="text-align: left" >
        Öffentliche Schlüssel
      </div>
      <b-list-group flush>
        <b-list-group-item style="text-align: left">Öffentlicher Schlüssel: {{this.userInformation.publicKey}}</b-list-group-item>
      </b-list-group>
    </div>
  </div>
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

</style>