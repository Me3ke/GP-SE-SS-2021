<template>
    <div>

        <Header></Header>

        <BaseHeading name="HistoryPage.title"></BaseHeading>


        <div class="container-fluid justify-content-end" style="display: flex">

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
                            :class="filter.sort === 'ABC' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'ABC'">{{
                                $t('HistoryPage.abc')
                            }}
                        </b-dropdown-item>
                        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                        <b-dropdown-item
                            :class="filter.sort === 'CBA' ? 'my-dropdown-item my-dropdown-item-selected' : 'my-dropdown-item'"
                            @click="filter.sort = 'CBA'">{{
                                $t('HistoryPage.cba')
                            }}
                        </b-dropdown-item>
                    </b-dropdown>
                </div>
            </div>
        </div>

        <!-- Versions -->
        <div class="container-fluid">
            <div class="user-container">
            </div>
        </div>

        <!-- Pagination -->
        <div style="display: flex; margin-top: 0.5em" class="justify-content-between container-fluid">
            <b-pagination
                v-model="page"
                :total-rows="5"
                :per-page="pageLimit"
                class="button-txt"
                style="margin-top: auto;margin-bottom: auto;"
            ></b-pagination>
        </div>


        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import {mapGetters} from "vuex";

export default {
    name: "HistoryPage",
    components: {
        Header,
        Footer
    },
    data() {
        return {
            filter: {
                search: '',
                sort: 'NEWEST',
            },

            pageLimit: 10,
            page: 1
        }
    },
    async mounted() {
        console.log('console')
       await this.$store.dispatch('document/fetchDocumentHistory', {
            envId: this.$route.params.envId,
            docId: this.$route.params.docId
        })
    },
    computed: {
        ...mapGetters({
            history: 'document/getDocumentHistory'
        })
    }
}
</script>

<style scoped src="../assets/css/userManagement.css">
</style>
