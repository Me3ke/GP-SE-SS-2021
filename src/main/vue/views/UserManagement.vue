<template>
    <div>
        <Header></Header>

        <BaseHeading name="AdminSettings.manage.title"></BaseHeading>


        <b-container fluid style="margin-top:2vh; margin-right:2vw; text-align: left">
            <b-row align-h="between" no-gutters>
                <div class="col-aut">
                    <b-row align-h="end">
                        <!-- Filter Buttons -->
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.open')"
                                              :isActive="filter.active"
                                              :switch="true"
                                              @activeChange="filterActive()"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span>
                                <FilterButton v-bind:text="$t('Filter.closed')"
                                              :isActive="filter.inactive"
                                              :switch="true"
                                              @activeChange="filterInactive()"></FilterButton>
                            </span>
                        </b-col>
                        <b-col>
                            <span @click="filterAdmin()">
                                <FilterButton v-bind:text="$t('Filter.owned')"
                                              :isActive="filter.admin"
                                              @activeChange="filter.admin = !filter.admin"></FilterButton>
                            </span>
                        </b-col>
                    </b-row>
                </div>
            </b-row>
        </b-container>


        <Footer></Footer>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import FilterButton from "@/main/vue/components/FilterButton";

export default {
    name: "UserManagement",
    components: {Footer, Header, FilterButton},
    data() {
        return {
            filter: {
                active: false,
                inactive: false,
                admin: false
            },
        }
    },
    async mounted() {
        await this.$store.dispatch('userManagement/fetchAllUsers')
    },
    methods: {
        // Change filter and make sure active and inactive filter is not activated at the same time
        filterActive() {
            if (!this.filter.active && !this.filter.inactive) {
                this.filter.active = true
            } else if (this.filter.active && !this.filter.inactive) {
                this.filter.active = false
            } else if (!this.filter.active && this.filter.inactive) {
                this.filter.inactive = false
                this.filter.active = true
            }
        },
        filterInactive() {
            if (!this.filter.active && !this.filter.inactive) {
                this.filter.inactive = true
            } else if (!this.filter.active && this.filter.inactive) {
                this.filter.inactive = false
            } else if (this.filter.active && !this.filter.inactive) {
                this.filter.active = false
                this.filter.inactive = true
            }
        },
        filterAdmin() {
            this.filter.admin = !this.filter.admin
        }
    },
    computed: {
        ...mapGetters({
            allUsers: 'userManagement/getAllUsers'
        })
    }
}
</script>

<style scoped>

</style>
