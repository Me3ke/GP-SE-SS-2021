<template>
    <div>
        <h3 style="margin-top: 2em;">User Manage</h3>

        <div style="margin-top: 3em">
            <button type="button" @click="click">
                Click me
            </button>
        </div>

        <div v-if="allUser" style="padding-top: 4em">
            <b-row v-for="user in users" :key="user.email">
                <b-container style="border: 1px solid black; padding-bottom: 2em; cursor: pointer" @click="printClickedUser(user)">
                <b-col>{{user.firstname}} {{user.lastname}}</b-col>
                <b-col>{{user.email}}</b-col>
                <b-col>{{user.roles}}</b-col>
                <b-col>{{user.adminValidated}}</b-col>
                </b-container>
            </b-row>
        </div>

    </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "BlankTestPage",
    components: {},
    data() {
        return {
            allUser: null
        }
    },

    computed: {
        ...mapGetters({
            users: 'getAllUsers',
            makeAdmin: 'getMakeAdminStatus'
        })
    },
    methods: {
        async click() {
            await this.$store.dispatch('fetchAllUsers')
            console.log(this.users)
            this.allUser = this.users

        },

        printClickedUser(user) {
            console.log(user)
            this.$store.dispatch('makeUserAdmin', user.email)
            console.log(this.makeAdmin)
        }
    }
}
</script>

<style scoped>

</style>
