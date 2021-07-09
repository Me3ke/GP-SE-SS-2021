<template>
    <div>
        <div v-if="!showMore">
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
                            <b-icon icon="trash" class="my-icon clickable" @click="deleteEntry()"></b-icon>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

        </div>

        <div v-else>
            <div class="user-box  entry" style="display: block">
                <b-container fluid style="margin-left: 0; margin-right: 0">
                    <b-row style="margin-bottom: 1rem">
                        <b-col cols="5">
                            <NameBubble :name="entry.firstname + ' ' + entry.lastname" class="bubble"></NameBubble>
                            <span style="margin-left: 1em; opacity: 70%">{{ $t('AddressBook.name') }}</span>
                            <b-icon icon="pencil" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                    @click="changeName()"></b-icon>

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
                            <span style="opacity: 70%;  margin-left: 3.5em">{{ $t('AddressBook.note') }}</span>
                            <b-icon icon="pencil" class="my-icon clickable" style="opacity: 70%" scale="0.75"
                                    @click="changeNote()"></b-icon>
                            <span v-if="entry.note !== null" style="margin-left: 1em">{{ entry.note }}</span>
                            <span v-else style="margin-left: 1em">{{ $t('AddressBook.noNote') }}</span>
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
                            <b-icon icon="trash" class="my-icon clickable" @click="deleteEntry()"></b-icon>
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
            showMore: true
        }
    },
    methods: {
        setFavorite() {
            // TODO: API call to make favorite
        },
        deleteEntry() {
            // TODO: API call to delete
        },
        changeName() {
            // TODO: make possible to edit name
        },
        changeNote() {
            // TODO: make possible to edit note
        },
        async more() {
            await this.$store.dispatch('fetchUserDataById', this.entry.email)
            this.showMore = true
        },
        less() {
            this.$store.dispatch('clearUserDataById')
            this.showMore = false
        }
    },
    computed: {
        ...mapGetters({
            userById: 'getUserByID'
        })
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
