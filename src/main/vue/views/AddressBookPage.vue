<template>
    <div>
        <Header></Header>

        <BaseHeading name="AddressBook.title"></BaseHeading>

        <div class="container-fluid justify-content-between" style="display: flex">

            <button class="elsa-blue-btn" @mouseover="hover = true" @mouseleave="hover = false"
                    @click="newEntry = true">
                <b-icon icon="plus-circle" class="my-icon" :id="hover ? 'upload-hover' : 'upload'"></b-icon>
                <span>{{ $t('AddressBook.new') }}</span>
            </button>

            <div style="display: flex">

                <!-- Filter Button -->
                <FilterButton :text="$t('AddressBook.filter.favorite')"
                              :isActive="filter.favorite"
                              :user-management="true"
                              @activeChange="filterFavorite()"
                              class="filter-button" style="margin-left: 1em"></FilterButton>

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

        <!-- User list -->
        <div v-if="!newEntry">
            <!-- User -->
            <div class="container-fluid" v-if="loaded">
                <div class="user-container">
                    <div v-for="(entry,index) in book(filter, pageLimit, page)" :key="index">
                        <EntryBox :entry="entry" :favorite="entry.favorite"></EntryBox>
                    </div>
                </div>
                <div v-if="book(filter, pageLimit, page).length === 0"
                     style="margin-bottom: 2em; border: solid 1px var(--whitesmoke); border-bottom-color: var(--light-grey)">
                <span id="empty-user">
                    {{ $t('AdminSettings.manage.empty') }}
                </span>
                </div>
            </div>

            <!-- Pagination-->
            <div style="display: flex; margin-top: 0.5em" class="justify-content-between container-fluid">
                <b-pagination
                    v-model="page"
                    :total-rows="5"
                    :per-page="pageLimit"
                    class="button-txt"
                    style="margin-top: auto;margin-bottom: auto;"
                ></b-pagination>
            </div>
        </div>

        <!-- Adding new entry -->
        <div v-else class="container-fluid">
            <div class="user-container">
                <AddEntry @closeEntry="newEntry = false"></AddEntry>
            </div>
        </div>


        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import FilterButton from "@/main/vue/components/overviewPage/FilterButton";
import {mapGetters} from "vuex";
import EntryBox from "@/main/vue/components/addressBook/EntryBox";
import AddEntry from "@/main/vue/components/addressBook/AddEntry";

export default {
    name: "AddressBookPage",
    components: {
        AddEntry,
        EntryBox,
        FilterButton,
        Footer,
        Header
    },
    data() {
        return {
            filter: {
                search: '',
                sort: 'NEWEST',
                favorite: false
            },

            pageLimit: 5,
            page: 1,

            // for changing hover color of plus icon on add entry button
            hover: false,
            newEntry: false,

            loaded: false
        }
    },
    methods: {
        filterFavorite() {
            this.filter.favorite = !this.filter.favorite
        }
    },
    async mounted() {
        await this.$store.dispatch('addressBook/fetchBook')
        await this.$store.dispatch('userManagement/fetchAllUsers')
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('userManagement/emptyStore')
        this.$store.dispatch('addressBook/resetBookState')
        this.$store.dispatch('clearUserDataById')
    },
    computed: {
        ...mapGetters({
            book: 'addressBook/getFilteredPagedBook',
        })
    }
}
</script>

<style scoped src="../assets/css/userManagement.css">
</style>
