<template>
    <div>
        <h3>User Manage</h3>

        <div>
            <button type="button" @click="click">
                Click me
            </button>
        </div>

    </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "BlankTestPage",
    components: {},
    data() {
        return {}
    },

    computed: {
        ...mapGetters({
            users: 'userManage/getUsers'
        })
    },
    methods: {
        async click() {
            await this.$store.dispatch('fetchAllUsers')
            console.log(this.users)

        }
    }
}
</script>

<style scoped>

</style>
