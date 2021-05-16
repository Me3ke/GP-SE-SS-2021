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
          <b-list-group-item>
            <b-row>
              <b-col style="text-align: left">
                {{$t('UserInfoBox.publicKey')}} {{this.userInformation.publicKey}}
              </b-col>
              <b-col style="text-align: right">
                <b-button @click="showAlert">
                  {{$t('UserInfoBox.newKeypair')}}
                </b-button>
              </b-col>
            </b-row>
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
  },
  methods: {
    async showAlert() {
      const inputOptions = new Promise((resolve) => {
          resolve({
            'generate': 'Generate keypair',
            'upload': 'Upload public key',
          })
      })

      const {value: updateOption} = await this.$swal.fire({
        title: 'Select option',
        input: 'radio',
        width: 500,
        inputOptions: inputOptions,
        showCancelButton: true,
        inputValidator: (value) => {
          if (!value) {
            return 'You need to choose something!'
          }
        }
      })

      if (updateOption === 'generate') {
        this.$swal.fire({
          title:'Warning',
          icon: 'warning',
          text: 'If you continue, your current keypair will be lost ' +
          'forever. Are you sure you want to continue?',
          confirmButtonText: `Continue`,
          showCancelButton: true,
          cancelButtonText: 'Cancel'
        }).then((result) => {

          if (result.isConfirmed) {
            this.handleGenerateKeyPairs()
            this.$swal.fire('Generating...')
          }
        })
      }

      else if (updateOption === 'upload') {
        this.$swal.fire({
          title:'Warning',
          icon: 'warning',
          text: 'If you continue, your current keypair will be lost ' +
              'forever. Are you sure you want to continue?',
          confirmButtonText: `Continue`,
          showCancelButton: true,
          cancelButtonText: 'Cancel'
        }).then((result) => {
          /* Read more about isConfirmed, isDenied below */
          if (result.isConfirmed) {
            this.handleUploadKeyPairs
            this.$swal.fire('Uploading...')
          }
        })
      }
    },
    handleGenerateKeyPairs() {
      //TODO Handling of the keypairs via generating
    },
    handleUploadKeyPairs() {
      //TODO Handling of the keypairs via uploading
    }
  }
}
</script>

<style scoped>

.card{
  width: 50vw;
  margin-bottom: 5vh;
}
</style>