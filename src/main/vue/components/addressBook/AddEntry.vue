<template>
    <div>
        <!-- Error Messages -->
        <b-alert :show="showSaveError" dismissible
                 @dismissed="showSaveError = false"
                 style="margin-bottom: 1em">
            <div style="text-align: center">
                <span> {{ $t('TwoFakAuth.serverErrorOne') }} </span>
            </div>
            <div style="text-align: center">
                <span> {{ $t('AddressBook.serverErrorTwo') }}  </span>
            </div>
            <div style="text-align: center">
                <span> Status Code : {{ errorSaveStatus }}</span>
            </div>
        </b-alert>

        <b-alert :show="showMailMissing" dismissible
                 @dismissed="showMailMissing = false"
                 style="margin-bottom: 1em">
            <div style="text-align: center">
                <span> {{ $t('AddressBook.mailMissing') }} </span>
            </div>
        </b-alert>

        <div class="user-box entry" style="display: block; background-color: var(--elsa-blue-transparent)">
            <b-container fluid style="margin-left: 0; margin-right: 0">
                <b-row style="margin-bottom: 1rem">
                    <b-col cols="4">
                        <span style="margin-left: 1em; opacity: 70%;">{{ $t('AddressBook.name') }}</span>

                        <!-- Firstname-->
                        <div style="display: flex">
                            <b-form-input style="margin-left: 1em !important;" v-model="settings.firstname"
                                          :placeholder="$t('AddressBook.firstname')"
                                          class="change-entry"></b-form-input>
                            <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                    @click="resetFirstname()" id="firstname"></b-icon>
                        </div>

                        <!-- Lastname-->
                        <div style="display: flex">
                            <b-form-input style="margin-left: 1em !important;" v-model="settings.lastname"
                                          :placeholder="$t('AddressBook.lastname')"
                                          class="change-entry"></b-form-input>
                            <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                    @click="resetLastname()" id="lastname" style="text-align: center"></b-icon>
                        </div>
                    </b-col>
                    <b-col cols="3">
                        <span style="opacity: 70%">{{ $t('AddressBook.mail') }}</span>
                        <b-icon icon="asterisk" class="my-icon" scale="0.5"></b-icon>

                        <!-- Mail-->
                        <div style="display: flex">
                            <b-form-input style="margin-left:0 !important;" v-model="settings.email"
                                          :placeholder="$t('AddressBook.mail')"
                                          class="change-entry"></b-form-input>
                            <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                    @click="resetMail()" id="mail"></b-icon>
                        </div>
                    </b-col>
                    <b-col cols="3">
                        <span style="opacity: 70%;  margin-left: 1em">{{ $t('AddressBook.note') }}</span>
                        <div style="display: flex">
                            <b-form-textarea
                                v-model="settings.note"
                                :placeholder="$t('AddressBook.note') "
                                rows="1"
                                class="change-entry"
                                style="margin-left: 1em !important; height: auto;">
                            </b-form-textarea>
                            <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                    @click="resetNote()" id="note" style="text-align: center"></b-icon>
                        </div>
                    </b-col>
                    <b-col cols="2" style="text-align: right">
                        <b-icon v-if="settings.favorite" icon="bookmark-star-fill" class="my-icon clickable"
                                scale="1.25"
                                @click="setFavorite()"></b-icon>
                        <b-icon v-else icon="bookmark-star" class="my-icon clickable" scale="1.25"
                                @click="setFavorite()"></b-icon>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <div class="show-more" style="display: block; background-color: var(--elsa-blue-transparent)">
            <b-container fluid style="margin-left: 0; margin-right: 0; margin-top: 1rem">
                <b-row>
                    <b-col cols="5">
                    </b-col>
                    <b-col cols="5" style="text-align: left">
                        <button class="elsa-blue-btn white-btn"
                                @click="closeEntry()">
                            <span>{{ $t('MessagePage.cancel') }}</span>
                        </button>
                        <button class="elsa-blue-btn dark-elsa-btn"
                                @click="addEntry()">
                            <span>{{ $t('AddressBook.save') }}</span>
                        </button>
                    </b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "AddEntry",
    data() {
        return {
            settings: {
                email: '',
                firstname: '',
                lastname: '',
                note: '',
                favorite: false
            },
            showMailMissing: false,
            showSaveError: false,
            errorSaveStatus: -1
        }
    },
    methods: {
        setFavorite() {
            this.settings.favorite = !this.settings.favorite
        },
        // adds entry
        async addEntry() {
            if(this.settings.email === ''){
                this.showMailMissing = true
                return
            }

            await this.$store.dispatch('addressBook/addEntry', this.settings)
            if (_.isEmpty(this.hasError) && this.resAdd.status === 200) {
                this.showSaveError = false
                await this.$store.dispatch('addressBook/fetchBook')
                this.closeEntry()
            } else {
                this.showSaveError = true
                this.errorSaveStatus = this.resAdd.status
            }
        },
        resetFirstname() {
            this.settings.firstname = ''
            this.resetAnimation('#firstname')
        },
        resetLastname() {
            this.settings.lastname = ''
            this.resetAnimation('#lastname')
        },
        resetNote() {
            this.settings.note = ''
            this.resetAnimation('#note')
        },
        resetMail() {
            this.settings.email = ''
            this.resetAnimation('#mail')
        },
        resetAnimation(obj) {
            // rotate animation
            document.querySelector(obj).style.transform = "rotate(45deg)";
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(90deg)";
            }, 50);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(135deg)";
            }, 100);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(180deg)";
            }, 150);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(225deg)";
            }, 200);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(225deg)";
            }, 250);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(270deg)";
            }, 300);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(315deg)";
            }, 350);
            setTimeout(() => {
                document.querySelector(obj).style.transform = "rotate(360deg)";
            }, 400);
        },
        closeEntry() {
            this.$emit('closeEntry')
        }
    },
    computed: {
        ...mapGetters({
            hasError: 'addressBook/getHasError',
            resAdd: 'addressBook/getResPutEntry'
        })
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
