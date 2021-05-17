<template>
  <b-container fluid="100">

    <b-row class="text-center" align-v="center" align-h="center">
      <div class="card">
        <div class="card-header">
          <h2 class="baseHeader-h2">
            {{$t('UserInfoBox.accountInformation')}}
          </h2>
        </div>
        <b-list-group flush>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.email')}} {{this.userInformation.email}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.firstName')}} {{this.userInformation.firstname}}
            </h3></b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.lastName')}} {{this.userInformation.lastname}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.birthday')}} {{this.userInformation.birthday}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.phoneNumber')}} {{this.userInformation.phoneNumber}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.country')}} {{this.userInformation.country}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.city')}} {{this.userInformation.hometown}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.street')}} {{this.userInformation.street}}
            </h3>
          </b-list-group-item>
          <b-list-group-item style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.houseNumber')}} {{this.userInformation.houseNumber}}
            </h3>
          </b-list-group-item>
        </b-list-group>
      </div>
    </b-row>

    <b-row class="text-center" align-v="center" align-h="center">
      <div class="card">
        <div class="card-header">
          <h2 class="baseHeader-h2">
            {{$t('UserInfoBox.publicKeys')}}
          </h2>
        </div>
        <b-list-group flush>
          <b-list-group-item class="listElement" style="text-align: left">
            <h3 class = "table-Content">
              {{$t('UserInfoBox.publicKey')}} {{this.userInformation.publicKey}}
            </h3>
          </b-list-group-item>
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
  width: 50vh;
  margin-bottom: 5vh;
}

@media screen and (min-width: 1200px){

  .baseHeader-h2 {
    font-size: 1.3em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (min-width: 1191px) and (max-width: 1199px) {

  .baseHeader-h2 {
    font-size: 1.3em;
  }
  .table-Content {
    font-size: 1em;
  }
}



@media screen and (min-width: 992px) and (max-width: 1190px) {

  .baseHeader-h2 {
    font-size: 2em;
  }
  .table-Content {
    font-size: 2em;
  }
}

@media screen and (min-width: 801px) and (max-width: 991px) {

  .baseHeader-h2 {
    font-size: 1.3em;
  }
  .table-Content {
    font-size: 1em;
  }
}


@media screen and (min-width: 768px) and (max-width: 800px) {

  .baseHeader-h2 {
    font-size: 1.5em;
  }
  .table-Content {
    font-size: 1.3em;
  }
}

@media screen and (max-width: 767px) and (min-width: 501px) {

  .baseHeader-h2 {
    font-size: 1.3em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 500px) and (min-width: 459px) {

  .baseHeader-h2 {
    font-size: 1.3em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (min-width: 410px) and (max-width: 458px) {

  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 409px) and (min-width: 355px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 354px) and (min-width: 330px){
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 329px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 304px) and (min-width: 294px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 293px) and (min-width: 289px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 288px) and (min-width: 240px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

@media screen and (max-width: 239px) {
  .baseHeader-h2 {
    font-size: 1em;
  }
  .table-Content {
    font-size: 1em;
  }
}

</style>