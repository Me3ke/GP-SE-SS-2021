<template>
    <b-container fluid="sm">

        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('Settings.UserInfoBox.accountInformation') }}
                    </h4>
                </div>

                <b-list-group flush>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('Settings.UserInfoBox.email') }} {{ this.user.email }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.firstName') }} {{ this.user.firstname }}
                        </span></b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.lastName') }} {{ this.user.lastname }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.birthday') }} {{ this.userData.birthday }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.phoneNumber') }} {{ this.userData.phoneNumber }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.country') }} {{ this.userData.country }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.city') }} {{ this.userData.homeTown }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.street') }} {{ this.userData.street }}
                        </span>
                    </b-list-group-item>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                       <span>
                            {{ $t('Settings.UserInfoBox.houseNumber') }} {{ this.userData.houseNumber }}
                        </span>
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
        user: Object,
        userData: Object
    }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
