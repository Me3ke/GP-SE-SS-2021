<template>
    <div>
        <div v-if="!showMore">
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
                    <span> Status Code : {{ errorChangeStatus }}</span>
                </div>
            </b-alert>

            <div class="user-box entry" style="display: block">
                <b-container fluid style="margin-left: 0; margin-right: 0">
                    <b-row style="margin-bottom: 1rem">
                        <b-col cols="5">
                            <NameBubble :name="entry.firstname + ' ' + entry.lastname" class="bubble"></NameBubble>
                            <span style="margin-left: 1em; opacity: 70%">{{ $t('AddressBook.name') }}</span>
                            <span style="margin-left: 1em">{{ entry.firstname }}  {{ entry.lastname }}</span>
                        </b-col>
                        <b-col cols="5">
                            <span style="opacity: 70%">{{ $t('AddressBook.mail') }}</span>
                            <span style="margin-left: 1em">{{ entry.email }}</span>
                        </b-col>
                        <b-col cols="2" style="text-align: right">
                            <b-icon v-if="favorite" icon="bookmark-star-fill" class="my-icon clickable" scale="1.25"
                                    @click="setFavorite()"></b-icon>
                            <b-icon v-else icon="bookmark-star" class="my-icon clickable" scale="1.25"
                                    @click="setFavorite()"></b-icon>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

            <div class="show-more" style="display: block">
                <hr style="border-color: var(--light-grey); margin-top: 0; margin-bottom: 0.5rem">
                <b-container fluid style="margin-left: 0; margin-right: 0">
                    <b-row>
                        <b-col cols="5">
                        </b-col>
                        <b-col cols="5" style="text-align: left">
                            <span class="clickable" @click="more()">{{ $t('AddressBook.more') }}</span>
                        </b-col>
                        <b-col cols="2" style="text-align: right">
                            <b-icon icon="trash" class="my-icon clickable" id="trash-popover"></b-icon>
                            <b-popover target="trash-popover" triggers="click" placement="bottomleft">
                                <!-- Error Messages -->
                                <b-alert :show="showErrorDelete"
                                         style="margin-bottom: 1em">
                                    {{ $t('MessagePage.deleteError') }} : {{ errorDeleteStatus }}
                                </b-alert>

                                <div style="text-align: center;">
                                    <span>{{ $t('MessagePage.sureDelete') }}</span>
                                </div>


                                <div style="text-align: center;">
                                    <button class="elsa-blue-btn white-btn"
                                            @click="$root.$emit('bv::hide::popover', 'trash-popover')">
                                        <span>{{ $t('MessagePage.cancel') }}</span>
                                    </button>

                                    <button class="elsa-blue-btn dark-elsa-btn"
                                            @click="deleteEntry()">
                                        <span>{{ $t('MessagePage.delete') }}</span>
                                    </button>
                                </div>
                            </b-popover>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

        </div>

        <div v-else>
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
                    <span> Status Code : {{ errorChangeStatus }}</span>
                </div>
            </b-alert>
            <div class="user-box  entry" style="display: block">
                <b-container fluid style="margin-left: 0; margin-right: 0">
                    <b-row style="margin-bottom: 1rem">
                        <b-col cols="5">
                            <NameBubble :name="entry.firstname + ' ' + entry.lastname" class="bubble"></NameBubble>
                            <template v-if="!showEditName">
                                <span style="margin-left: 1em; opacity: 70%">{{ $t('AddressBook.name') }}</span>
                                <b-icon icon="pencil" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                        @click="changeName()"></b-icon>

                                <span style="margin-left: 1em">{{ entry.firstname }}  {{ entry.lastname }}</span>
                            </template>

                            <!-- Editing name -->
                            <template v-else style="display: flex">
                                <span style="margin-left: 1em; opacity: 70%">{{ $t('AddressBook.name') }}</span>
                                <b-icon icon="pencil-fill" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                        @click="changeName()"></b-icon>
                                <transition name="saved">
                                    <span v-if="showSaveName" style="margin-left: 0.5em; color: var(--elsa-blue)">{{
                                            $t('Settings.saved')
                                        }}</span>
                                </transition>

                                <!-- Firstname-->
                                <div style="display: flex">
                                    <b-form-input style="margin-left: 1em" v-model="settings.firstname"
                                                  :placeholder="entry.firstname"
                                                  class="change-entry"></b-form-input>
                                    <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                            @click="resetFirstname()" id="firstname"></b-icon>
                                </div>

                                <!-- Lastname-->
                                <div style="display: flex">
                                    <b-form-input style="margin-left: 1em" v-model="settings.lastname"
                                                  :placeholder="entry.lastname"
                                                  class="change-entry"></b-form-input>
                                    <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                            @click="resetLastname()" id="lastname" style="text-align: center"></b-icon>
                                </div>

                                <span class="clickable save-changes" @click="saveSettingsChange(1)"
                                >{{
                                        $t('AddressBook.save')
                                    }}</span>

                            </template>

                        </b-col>
                        <b-col cols="5">
                            <span style="opacity: 70%">{{ $t('AddressBook.mail') }}</span>
                            <span style="margin-left: 1em">{{ entry.email }}</span>
                        </b-col>
                        <b-col cols="2" style="text-align: right">
                            <b-icon v-if="favorite" icon="bookmark-star-fill" class="my-icon clickable" scale="1.25"
                                    @click="setFavorite()"></b-icon>
                            <b-icon v-else icon="bookmark-star" class="my-icon clickable" scale="1.25"
                                    @click="setFavorite()"></b-icon>
                        </b-col>
                    </b-row>
                    <b-row style="margin-bottom: 1rem">
                        <b-col cols="5">
                            <div>
                                <div>
                                    <span style="opacity: 70%; margin-left: 3.5em">{{
                                            $t('AddressBook.address')
                                        }}</span>
                                    <span style="margin-left: 1em">{{ userById.street }}  {{
                                            userById.houseNumber
                                        }}</span>
                                </div>
                                <div>
                                    <span style="margin-left: 8.3em">{{ userById.postCode }}  {{
                                            userById.homeTown
                                        }}</span>
                                </div>
                                <span style="margin-left: 8.3em">{{ userById.country }}</span>
                            </div>
                        </b-col>
                        <b-col cols="5">
                            <div>
                                <div style="margin-bottom: 0.5rem">
                                    <span style="opacity: 70%;">{{ $t('AddressBook.bday') }}</span>
                                    <span style="margin-left: 1em">{{ userById.birthday }} </span>
                                </div>
                                <div>
                                    <span style="opacity: 70%;">{{ $t('AddressBook.number') }}</span>
                                    <span style="margin-left: 1em">{{ userById.phoneNumber }} </span>
                                </div>
                            </div>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="12">
                            <template v-if="!showEditNote">
                                <span style="opacity: 70%;  margin-left: 3.5em">{{ $t('AddressBook.note') }}</span>
                                <b-icon icon="pencil" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                        @click="changeNote()"></b-icon>
                                <span v-if="entry.note !== null" style="margin-left: 1em">{{ entry.note }}</span>
                                <span v-else style="margin-left: 1em">{{ $t('AddressBook.noNote') }}</span>
                            </template>

                            <!-- Edit Note -->
                            <template v-else>
                                <span style="opacity: 70%;  margin-left: 3.5em">{{ $t('AddressBook.note') }}</span>
                                <b-icon icon="pencil-fill" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                        @click="changeNote()"></b-icon>
                                <transition name="saved">
                                    <span v-if="showSaveNote" style="margin-left: 0.5em; color: var(--elsa-blue)">{{
                                            $t('Settings.saved')
                                        }}</span>
                                </transition>
                                <div style="display: flex">
                                    <b-form-textarea
                                        v-model="settings.note"
                                        :placeholder="entry.note === null ? '...' : entry.note"
                                        rows="1"
                                        class="change-entry"
                                        style="margin-left: 1em; height: auto">
                                    </b-form-textarea>
                                    <b-icon icon="arrow-clockwise" class="my-icon clickable"
                                            @click="resetNote()" id="note" style="text-align: center"></b-icon>
                                </div>

                                <span class="clickable save-changes" @click="saveSettingsChange(2)"
                                >{{
                                        $t('AddressBook.save')
                                    }}</span>
                            </template>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

            <div class="show-more">
                <hr style="border-color: var(--light-grey); margin-top: 0; margin-bottom: 0.5rem">
                <b-container fluid style="margin-left: 0; margin-right: 0">
                    <b-row>
                        <b-col cols="5">
                        </b-col>
                        <b-col cols="5" style="text-align: left">
                            <span class="clickable" @click="less()">{{ $t('AddressBook.less') }}</span>
                        </b-col>
                        <b-col cols="2" style="text-align: right">
                            <b-icon icon="trash" class="my-icon clickable" id="trash-popover2"></b-icon>
                            <b-popover target="trash-popover2" triggers="click" placement="bottomleft">
                                <!-- Error Messages -->
                                <b-alert :show="showErrorDelete"
                                         style="margin-bottom: 1em">
                                    {{ $t('MessagePage.deleteError') }} : {{ errorDeleteStatus }}
                                </b-alert>

                                <div style="text-align: center;">
                                    <span>{{ $t('MessagePage.sureDelete') }}</span>
                                </div>


                                <div style="text-align: center;">
                                    <button class="elsa-blue-btn white-btn"
                                            @click="$root.$emit('bv::hide::popover', 'trash-popover2')">
                                        <span>{{ $t('MessagePage.cancel') }}</span>
                                    </button>

                                    <button class="elsa-blue-btn dark-elsa-btn"
                                            @click="deleteEntry()">
                                        <span>{{ $t('MessagePage.delete') }}</span>
                                    </button>
                                </div>
                            </b-popover>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

        </div>
    </div>


</template>

<script>
import NameBubble from "@/main/vue/components/commentsPage/NameBubble";
import {mapGetters} from "vuex";
import _ from 'lodash';

export default {
    name: "EntryBox",
    components: {NameBubble},
    props: {
        entry: Object,
        favorite: Boolean,
    },
    data() {
        return {
            selected: false,
            showMore: false,
            showEditName: false,
            showEditNote: false,
            settings: {
                email: this.entry.email,
                firstname: '',
                lastname: '',
                note: '',
                favorite: this.favorite
            },
            showSaveError: false,
            showSaveName: false,
            showSaveNote: false,
            showErrorDelete: false,
            errorDeleteStatus: -1,
            errorChangeStatus: -1
        }
    },
    methods: {
        setFavorite() {
            this.settings.favorite = !this.favorite
            this.saveSettingsChange(0)
        },
        async deleteEntry() {
            await this.$store.dispatch('addressBook/deleteEntry', this.entry.id)
            if (_.isEmpty(this.hasError) && this.resDelete.status === 200) {
                this.showErrorDelete = false
            } else {
                this.showErrorDelete = true
                this.errorDeleteStatus = this.resDelete.status
            }
            await this.$store.dispatch('addressBook/fetchBook')
        },
        // saves changes
        // kind: 0: favorite; 1: name; 2: note
        async saveSettingsChange(kind) {
            let newEntry = {}
            if (this.settings.firstname === '') {
                newEntry.firstname = this.entry.firstname
            } else {
                newEntry.firstname = this.settings.firstname
            }
            if (this.settings.lastname === '') {
                newEntry.lastname = this.entry.lastname
            } else {
                newEntry.lastname = this.settings.lastname
            }
            if (this.settings.note === '') {
                newEntry.note = this.entry.note
            } else {
                newEntry.note = this.settings.note
            }
            newEntry.favorite = this.settings.favorite
            newEntry.email = this.settings.email
            await this.$store.dispatch('addressBook/changeEntry', {entryId: this.entry.id, newEntry: newEntry})
            if (_.isEmpty(this.hasError) && this.resChange.status === 200) {
                if (kind === 1) {
                    // show saved notification
                    this.showSaveName = true
                    setTimeout(() => {
                        this.showSaveName = false
                    }, 2000);
                } else if (kind === 2) {
                    // show saved notification
                    this.showSaveNote = true
                    setTimeout(() => {
                        this.showSaveNote = false
                    }, 2000);
                }
                this.settings.firstname = ''
                this.settings.lastname = ''
                this.settings.note = ''
                this.showSaveError = false
                await this.$store.dispatch('addressBook/fetchBook')
            } else {
                this.showSaveError = true
                this.errorChangeStatus = this.resChange.status
            }

        },
        changeName() {
            this.showEditName = !this.showEditName
        },
        resetFirstname() {
            this.settings.firstname = ''
            this.resetAnimation('#firstname')
        },
        resetLastname() {
            this.settings.lastname = ''
            this.resetAnimation('#lastname')
        },
        changeNote() {
            this.showEditNote = !this.showEditNote
        },
        resetNote() {
            this.settings.note = ''
            this.resetAnimation('#note')
        },
        async more() {
            await this.$store.dispatch('fetchUserDataById', this.entry.email)
            this.showMore = true
        },
        less() {
            this.$store.dispatch('clearUserDataById')
            this.showMore = false
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
        }
    },
    computed: {
        ...mapGetters({
            userById: 'getUserByID',
            hasError: 'addressBook/getHasError',
            resChange: 'addressBook/getResChangeEntry',
            resDelete: 'addressBook/getResDeleteEntry'
        })
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
