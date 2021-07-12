<template>
    <div>

        <Header></Header>

        <BaseHeading name="HistoryPage.title"></BaseHeading>


        <div class="container-fluid justify-content-end" style="display: flex">

            <div style="display: flex">
                <div>
                    <!-- Searchbar -->
                    <b-input-group>
                        <b-input-group-prepend is-text>
                            <b-icon icon="search" id="search" style="fill: var(--elsa-blue);"></b-icon>
                        </b-input-group-prepend>
                        <b-form-input v-model="filter.search"
                                      :placeholder="$t('OverviewPage.search')" type="search"></b-form-input>
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
        <div class="container-fluid" v-if="loaded">
            <div class="user-container">
                <div v-for="(document,index) in history(filter, pageLimit, page)" :key="index">
                    <HistoryBox v-if="showPdf === -1 || showPdf === document.id" :document="document"
                                :current="showPdf === document.id"
                                @click.native="togglePdfViewer(document.id)" :key="document.id"></HistoryBox>
                    <PDFViewer v-if="showPdf === document.id" :overflow="true" :pdf-src="getSrc()"
                               :key="document.id + history(filter, pageLimit, page).length "></PDFViewer>
                </div>
            </div>
        </div>

        <!-- Pagination -->
        <div v-if="showPdf === -1" style="display: flex; margin-top: 0.5em"
             class="justify-content-between container-fluid">
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
import HistoryBox from "@/main/vue/components/history/HistoryBox";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import documentAPI from "@/main/vue/api/documentAPI";

export default {
    name: "HistoryPage",
    components: {
        PDFViewer,
        HistoryBox,
        Header,
        Footer
    },
    data() {
        return {
            filter: {
                search: '',
                sort: 'NEWEST',
            },

            showPdf: -1,
            src: null,
            dataError: false,

            pageLimit: 10,
            page: 1,

            loaded: false
        }
    },
    async mounted() {
        await this.$store.dispatch('document/fetchDocumentHistory', {
            envId: this.$route.params.envId,
            docId: this.$route.params.docId
        })
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('document/clearHistory')
    },
    methods: {
        // gets source of document
        getSrc() {
            let chars = atob(this.src);
            let array = new Uint8Array(chars.length);
            for (let i = 0; i < chars.length; i++) {
                array[i] = chars.charCodeAt(i)
            }
            return array
        },

        // toggles pdf viewer, sets viewer for one document wit id id; fetches document data
        async togglePdfViewer(id) {
            if (this.showPdf === id) {
                this.showPdf = -1
                this.src = null
                this.dataError = false
            } else {
                if (parseInt(this.$route.params.docId) === id) {
                    await documentAPI.getDocument(this.$route.params.envId, this.$route.params.docId).then(response => {
                        this.src = response.data.data
                        this.dataError = false
                    }).catch(() => {
                        this.dataError = true
                    })
                } else {
                    await documentAPI.getArchivedDocumentData(id).then(response => {
                        this.src = response.data.data
                        this.dataError = false
                    }).catch(() => {
                        this.dataError = true
                    })
                }

                this.showPdf = id
            }
        }
    },
    computed: {
        ...mapGetters({
            history: 'document/getDocumentHistorySorted'
        })
    }
}
</script>

<style scoped src="../assets/css/userManagement.css">
</style>
