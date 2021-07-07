<template>
    <div>
        <Header></Header>

        <BaseHeading name="AdminSettings.manage.title"></BaseHeading>


        <div class="container-fluid justify-content-between" style="display: flex">
            <!-- Filter Buttons -->
            <div>
                <FilterButton :text="$t('AdminSettings.manage.filter.active')"
                              :isActive="filter.active"
                              :switch="true"
                              :user-management="true"
                              @activeChange="filterActive()"
                              class="filter-button"></FilterButton>

                <FilterButton :text="$t('AdminSettings.manage.filter.inactive')"
                              :isActive="filter.inactive"
                              :switch="true"
                              :user-management="true"
                              @activeChange="filterInactive()"
                              class="filter-button"></FilterButton>

                <FilterButton :text="$t('AdminSettings.manage.filter.admin')"
                              :isActive="filter.admin"
                              :user-management="true"
                              @activeChange="filterAdmin()"
                              class="filter-button"></FilterButton>
            </div>

            <div style="display: flex">
                <div>
                    <b-input-group>
                        <!-- Searchbar -->
                        <b-form-input v-model="filter.search"
                                      :placeholder="$t('OverviewPage.search')"></b-form-input>

                        <b-icon class="my-icon" icon="search" id="search"></b-icon>
                    </b-input-group>
                </div>

                <div>
                    <!-- Sort -->
                    <b-dropdown class="my-dropdown-menu my-dropdown-toggle" style="margin-left: 2em" no-caret right>
                        <template #button-content>
                            <b-icon icon="bar-chart-fill" rotate="-90"
                                    style="transform: rotateY(180deg);"
                                    class="my-icon"></b-icon>
                        </template>
                        <b-dropdown-item
                            :class="filter.sort === 'NEWEST' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'NEWEST'">
                            {{
                                $t('AdminSettings.manage.sort.new')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'OLDEST' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'OLDEST'">{{
                                $t('AdminSettings.manage.sort.old')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'ABCN' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'ABCN'">{{
                                $t('AdminSettings.manage.sort.abcN')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'CBAN' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'CBAN'">{{
                                $t('AdminSettings.manage.sort.cbaN')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'ABCE' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'ABCE'">{{
                                $t('AdminSettings.manage.sort.abcE')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'CBAE' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'CBAE'">{{
                                $t('AdminSettings.manage.sort.cbaE')
                            }}
                        </b-dropdown-item>
                    </b-dropdown>
                </div>
            </div>
        </div>

        <!-- User -->
        <div class="container-fluid" v-if="loaded">
            <div class="user-container">
                <div v-for="(user,index) in allUsers(filter, pageLimit,page)" :key="index" style="display: flex">
                    <UserBox :user="user" :deactivated="!user.adminValidated"
                             :admin="user.roles.includes('ROLE_ADMIN')" :seen="user.seen"></UserBox>
                    <b-icon v-if="!selected.includes(index)" icon="circle" class="my-icon checker"
                            @click="changeSelected(index)"></b-icon>
                    <b-icon v-else icon="check-circle" class="my-icon checker"
                            @click="changeSelected(index)"></b-icon>
                </div>
            </div>
            <div v-if="allUsers(filter, pageLimit,page).length === 0"
                 style="margin-bottom: 2em; border: solid 1px var(--whitesmoke); border-bottom-color: var(--light-grey)">
                <span id="empty-user">
                    {{ $t('AdminSettings.manage.empty') }}
                </span>
            </div>
        </div>

        <!-- Pagination and Buttons -->
        <div style="display: flex; margin-top: 0.5em" class="justify-content-between container-fluid">
            <b-pagination
                v-model="page"
                :total-rows="allUsers.length"
                :per-page="pageLimit"
                class="button-txt"
                style="margin-top: auto;margin-bottom: auto;"
            ></b-pagination>

            <div>
                <transition name="saved">
                            <span v-if="showSave">
                                {{ $t('Settings.saved') }}
                            </span>
                </transition>
                <transition name="saved">
                            <span v-if="showError">
                                {{ $t('AdminSettings.manage.error') }}
                            </span>
                </transition>
                <button type="button" class="elsa-blue-btn" @click="makeAdmin()">
                    <span class="button-txt">
                        {{ $t('AdminSettings.manage.options.admin') }}
                    </span>
                </button>
                <button type="button" class="elsa-blue-btn" @click="deactivate()">
                      <span class="button-txt">
                           {{ $t('AdminSettings.manage.options.inactive') }}
                      </span>
                </button>
                <button type="button" class="elsa-blue-btn" @click="activate()">
                      <span class="button-txt">
                           {{ $t('AdminSettings.manage.options.active') }}
                      </span>
                </button>
            </div>

        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import FilterButton from "@/main/vue/components/FilterButton";
import UserBox from "@/main/vue/components/userManagement/UserBox";
import _ from "lodash";

export default {
    name: "UserManagement",
    components: {UserBox, Footer, Header, FilterButton},
    data() {
        return {
            filter: {
                search: '',
                sort: 'NEWEST',

                active: false,
                inactive: false,
                admin: false,
            },
            selected: [],

            pageLimit: 10,
            page: 1,

            showSave: false,
            showError: false,

            loaded: false
        }
    },
    async mounted() {
        await this.$store.dispatch('userManagement/fetchAllUsers')
        this.loaded = true
    },
    async beforeDestroy() {
        await this.seeAll()
        await this.$store.dispatch('userManagement/emptyStore')
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
        },

        // sets selected array
        changeSelected(index) {
            if (this.selected.includes(index)) {
                this.selected.splice(this.selected.indexOf(index), 1)
            } else {
                this.selected.push(index)
            }
        },

        // makes user(s) admin
        async makeAdmin() {
            if (this.selected.length === 0) {
                return
            }

            let user = this.allUsers(this.filter, this.pageLimit, this.page)
            let didSomething = false
            for (let i = 0; i < this.selected.length; i++) {
                if (!user[this.selected[i]].roles.includes('ROLE_ADMIN')) {
                    await this.$store.dispatch('userManagement/makeUserAdmin', user[this.selected[i]].email)
                    if (!user[this.selected[i]].seen) {
                        await this.makeSeen(user[this.selected[i]].email)
                    }
                    didSomething = true
                }
            }

            await this.resolve(didSomething)
        },

        // deactivates user(s)
        async deactivate() {
            if (this.selected.length === 0) {
                return
            }

            let user = this.allUsers(this.filter, this.pageLimit, this.page)
            let didSomething = false
            for (let i = 0; i < this.selected.length; i++) {
                if (user[this.selected[i]].adminValidated && !user[this.selected[i]].roles.includes('ROLE_ADMIN')) {
                    await this.$store.dispatch('userManagement/lockUser', user[this.selected[i]].email)
                    if (!user[this.selected[i]].seen) {
                        await this.makeSeen(user[this.selected[i]].email)
                    }
                    didSomething = true
                }
            }

            await this.resolve(didSomething)
        },

        // activates user(s)
        async activate() {
            if (this.selected.length === 0) {
                return
            }

            let user = this.allUsers(this.filter, this.pageLimit, this.page)
            let didSomething = false
            for (let i = 0; i < this.selected.length; i++) {
                if (!user[this.selected[i]].adminValidated) {
                    await this.$store.dispatch('userManagement/validateUser', user[this.selected[i]].email)
                    if (!user[this.selected[i]].seen) {
                        await this.makeSeen(user[this.selected[i]].email)
                    }
                    didSomething = true
                }
            }

            await this.resolve(didSomething)
        },

        // method after admin toke action via button
        async resolve(didSomething) {
            await this.$store.dispatch('userManagement/fetchAllUsers')
            this.selected = []

            if (didSomething && !this.hasError()) {
                // show saved notification
                this.showSave = true
                setTimeout(() => {
                    this.showSave = false
                }, 2000);
            } else {
                // show error notification
                this.showError = true
                setTimeout(() => {
                    this.showError = false
                }, 2000);
            }
        },

        // marks user as seen
        async makeSeen(userId) {
            await this.$store.dispatch('userManagement/seeUser', userId)
        },

        // marks all users seen
        async seeAll() {
            let user = this.allUsers(this.filter, this.pageLimit, this.page)
            for (let i = 0; i < user.length; i++) {
                if (!user[i].seen) {
                    await this.makeSeen(user[i].email)
                }
            }
        },

        hasError() {
            return !_.isEmpty(this.error);
        }
    }
    ,
    computed: {
        ...mapGetters({
            allUsers: 'userManagement/getFilteredPagesUsers',
            error: 'userManagement/userManagementError'
        })
    }
}
</script>

<style scoped src="../assets/css/userManagement.css">
</style>
