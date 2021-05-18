<template>
    <b-container fluid="100">

        <b-row class="text-center" align-v="center" align-h="center">
            <div class="card">
                <div class="card-header">
                    <h2 class="baseHeader-h2">
                        {{ $t('UserInfoBox.accountInformation') }}
                    </h2>
                </div>
                <b-list-group flush>
                    <b-list-group-item style="text-align: left; background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.email') }} {{ this.userInformation.email }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left; background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.firstName') }} {{ this.userInformation.firstname }}
                        </h3></b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.lastName') }} {{ this.userInformation.lastname }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.birthday') }} {{ this.userInformation.birthday }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.phoneNumber') }} {{ this.userInformation.phoneNumber }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.country') }} {{ this.userInformation.country }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.city') }} {{ this.userInformation.hometown }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.street') }} {{ this.userInformation.street }}
                        </h3>
                    </b-list-group-item>
                    <b-list-group-item style="text-align: left;background-color: var(--user-info)">
                        <h3 class="table-Content">
                            {{ $t('UserInfoBox.houseNumber') }} {{ this.userInformation.houseNumber }}
                        </h3>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>

        <b-row class="text-center" align-v="center" align-h="center">
            <div class="card">
                <div class="card-header">
                    <h2 class="baseHeader-h2">
                        {{ $t('UserInfoBox.publicKeys') }}
                    </h2>
                </div>
                <b-list-group>
                    <b-list-group-item style="background-color: var(--user-info)">
                        <b-row>
                            <b-col style="text-align: left">
                                <h3 class="table-Content">
                                    {{ $t('UserInfoBox.publicKey') }} {{ this.userInformation.publicKey }}
                                </h3>
                            </b-col>
                            <b-col style="text-align: right">
                                <b-button @click="decideLanguage" id="keyButton">
                                    {{ $t('UserInfoBox.newKeypair') }}
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
import i18n from "@/i18n";

export default {
    name: "UserInfoBox",
    props: {
        userInformation: Object,
    },
    methods: {

        decideLanguage() {
            if (i18n.locale === 'de') {
                this.showAlertDE()
            } else {
                this.showAlertEN()
            }
        },

        async showAlertEN() {
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
                    title: 'Warning',
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
            } else if (updateOption === 'upload') {
                this.$swal.fire({
                    title: 'Warning',
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

        async showAlertDE() {
            const inputOptions = new Promise((resolve) => {
                resolve({
                    'generate': 'Generiere Schlüsselpaar',
                    'upload': 'Lade Schlüsselpaar hoch',
                })
            })

            const {value: updateOption} = await this.$swal.fire({
                title: 'Wähle Option',
                input: 'radio',
                width: 500,
                inputOptions: inputOptions,
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Es muss etwas ausgewählt werden!'
                    }
                }
            })

            if (updateOption === 'generate') {
                this.$swal.fire({
                    title: 'Warnung',
                    icon: 'warning',
                    text: 'Wenn Sie fortfahren, wird das alte Schlüsselpaar für immer verloren gehen; Sind Sie sich sicher?',
                    confirmButtonText: `Fortfahren`,
                    showCancelButton: true,
                    cancelButtonText: 'Abbruch'
                }).then((result) => {

                    if (result.isConfirmed) {
                        this.handleGenerateKeyPairs()
                        this.$swal.fire('Generieren...')
                    }
                })
            } else if (updateOption === 'upload') {
                this.$swal.fire({
                    title: 'Warnung',
                    icon: 'warning',
                    text: 'Wenn Sie fortfahren, wird das alte Schlüsselpaar für immer verloren gehen; Sind Sie sich sicher?',
                    confirmButtonText: `Fortfahren`,
                    showCancelButton: true,
                    cancelButtonText: 'Abbruch'
                }).then((result) => {
                    /* Read more about isConfirmed, isDenied below */
                    if (result.isConfirmed) {
                        this.handleUploadKeyPairs
                        this.$swal.fire('Hochladen...')
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

.card {
    width: 80vh;
    margin-bottom: 5vh;
    background-color: var(--whitesmoke);
    border-color: var(--dark-grey);
}

@media screen and (min-width: 1200px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1em;
    }

    #keyButton {
        font-size: 1em;
    }
}

@media screen and (min-width: 1191px) and (max-width: 1199px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1em;
        margin-right: 1em;
    }

    #keyButton {
        font-size: 1em;
    }
}


@media screen and (min-width: 992px) and (max-width: 1190px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1em;
    }

    #keyButton {
        font-size: 1em;
    }
}

@media screen and (min-width: 801px) and (max-width: 991px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1em;
    }

    #keyButton {
        font-size: 1em;
    }
}


@media screen and (min-width: 768px) and (max-width: 800px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1.3em;
    }

    #keyButton {
        font-size: 1em;
    }
}

@media screen and (max-width: 767px) and (min-width: 501px) {

    .baseHeader-h2 {
        font-size: 1.3em;
    }

    .table-Content {
        font-size: 1em;
    }

    #keyButton {
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

    #keyButton {
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

    #keyButton {
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

    #keyButton {
        font-size: 1em;
    }
}

@media screen and (max-width: 354px) and (min-width: 330px) {
    .baseHeader-h2 {
        font-size: 1em;
    }

    .table-Content {
        font-size: 1em;
    }

    #keyButton {
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

    #keyButton {
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

    #keyButton {
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

    #keyButton {
        font-size: 1em;
    }
}

@media screen and (max-width: 288px) and (min-width: 240px) {
    .baseHeader-h2 {
        font-size: 1em;
    }

    .table-Content {
        font-size: 1em;
        margin-right: 3em;
    }

    #keyButton {
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

    #keyButton {
        font-size: 1em;
    }
}

</style>
