<template>
    <div
        style="border: 1px solid var(--dark-grey); padding-top: 0.5rem; padding-bottom: 0.5rem;">
        <div class="container-fluid justify-content-end"
             style="display: flex; width: 100%; margin-left: 0; margin-right: 0">
            <div style="display: flex">

                <!-- Filter Button -->
                <FilterButton :text="$t('AddressBook.filter.favorite')"
                              :isActive="filter.favorite"
                              :user-management="true"
                              @activeChange="filterFavorite()"
                              class="filter-button"></FilterButton>

                <div>
                    <b-input-group>
                        <!-- Searchbar -->
                        <b-form-input v-model="filter.search"
                                      :placeholder="$t('OverviewPage.search')"></b-form-input>

                        <b-icon class="my-icon clickable" icon="search" id="search"></b-icon>
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
        <div>
            <!-- User -->
            <div class="container-fluid" v-if="loaded">
                <div class="user-container" style="margin-left: 0; margin-right: 0">
                    <div v-for="(entry,index) in book(filter, pageLimit, page)" :key="index" style="display: flex">
                        <SmallEntryBox :entry="entry" :favorite="entry.favorite"></SmallEntryBox>
                        <b-icon v-if="!selected.includes(index) && notAlready(entry)" icon="circle"
                                class="my-icon checker"
                                @click="changeSelected(index)"></b-icon>
                        <b-icon v-if="notAlready(entry) && selected.includes(index)" icon="check-circle"
                                class="my-icon checker"
                                @click="changeSelected(index)"></b-icon>
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
                <div>
                    <button type="button" class="elsa-blue-btn white-btn" @click="close()">
                        <span>{{ $t('DownloadDoc.cancel') }}</span>
                    </button>
                    <button type="button" class="elsa-blue-btn dark-elsa-btn" @click="add()" :disabled="disableSave">
                        <span> {{ $t('Settings.DocumentSettings.save') }}</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import FilterButton from "@/main/vue/components/overviewPage/FilterButton";
import SmallEntryBox from "@/main/vue/components/addressBook/SmallEntryBox";

export default {
    name: "AddressBookSelection",
    props: {
        signatories: Array,
        readers: Array,
        // indicates if its for signatories or readers
        sign: {
            type: Boolean,
            required: true
        }
    },
    components: {
        SmallEntryBox,
        FilterButton
    },
    data() {
        return {
            selected: [],

            filter: {
                search: '',
                sort: 'NEWEST',
                favorite: false
            },

            pageLimit: 5,
            page: 1,
            loaded: false
        }
    },
    methods: {
        filterFavorite() {
            this.filter.favorite = !this.filter.favorite
        },
        // sets selected array
        changeSelected(index) {
            if (this.selected.includes(index)) {
                this.selected.splice(this.selected.indexOf(index), 1)
            } else {
                this.selected.push(index)
            }
        },
        add() {
            if (this.selected.length === 0) {
                return
            }

            let book = this.book(this.filter, this.pageLimit, this.page)
            for (let i = 0; i < this.selected.length; i++) {
                if (this.sign) {
                    this.signatories.push({email: book[this.selected[i]].email});
                } else {
                    this.readers.push({email: book[this.selected[i]].email});
                }
            }

            this.close()
        },
        close() {
            this.$emit('showAddressBook')
        },
        notAlready(entry) {
            if (this.sign) {
                for (var i = 0; i < this.signatories.length; i++) {
                    if (this.signatories[i].email === entry.email) {
                        return false
                    }
                }
                return true
            } else {
                for (var j = 0; j < this.readers.length; j++) {
                    if (this.readers[j].email === entry.email) {
                        return false
                    }
                }
                return true
            }
        }
    },
    async mounted() {
        await this.$store.dispatch('addressBook/fetchBook')
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('addressBook/resetBookState')
    },
    computed: {
        ...mapGetters({
            book: 'addressBook/getFilteredPagedBook',
        }),
        disableSave() {
            if (this.sign) {
                return this.signatories.length === 0 && this.selected.length === 0
            } else {
                return this.readers.length === 0 && this.selected.length === 0
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
