<template>
    <b-container id="container">

        <!-- TODO : test with a big document if it is working -->
        <b-row>
            <b-col v-show="loading>0 && loading < 1">
                <b-progress :value="loading*100" :max="100" show-progress animated></b-progress>
            </b-col>
        </b-row>

        <b-row cols="1" id="backgroundButtons">

            <!-- Switch between Page and Scroll Mode -->
            <b-col>
                <PDFViewerButton v-if="pageMode"
                                 @click.native="changeMode()"
                                 icon="hr"
                                 class="separation">
                </PDFViewerButton>
                <PDFViewerButton v-else
                                 @click.native="changeMode()"
                                 icon="files"
                                 class="separation">
                </PDFViewerButton>

                <!-- Jump backward -->
                <PDFViewerButton :disabled="!pageMode || currentPage <= 1"
                                 @click.native="currentPage = 1"
                                 icon="chevron-double-left"
                                 style="margin-right: 0.1vw">
                </PDFViewerButton>
                <PDFViewerButton :disabled="!pageMode || currentPage <= 1"
                                 @click.native="currentPage--"
                                 icon="chevron-left"
                                 style="margin-right: 0.2vw">
                </PDFViewerButton>
                <!-- Page Display -->
                {{ currentPage }} / {{ pageCount }}

                <!-- Jump forward -->
                <PDFViewerButton :disabled="!pageMode || currentPage >= pageCount"
                                 @click.native="currentPage++"
                                 icon="chevron-right"
                                 style="margin-left: 0.2vw">
                </PDFViewerButton>
                <PDFViewerButton :disabled="!pageMode || currentPage >= pageCount"
                                 @click.native="currentPage = pageCount"
                                 icon="chevron-double-right"
                                 style="margin-left: 0.1vw"
                                 class="separation">
                </PDFViewerButton>

                <!-- Zoom in and out -->
                <PDFViewerButton :disabled="zoom <= 10"
                                 @click.native="changeZoom(-10)"
                                 icon="zoom-out">
                </PDFViewerButton>
                {{ zoom }}%
                <PDFViewerButton :disabled="zoom >= 400"
                                 @click.native="changeZoom(10)"
                                 icon="zoom-in"
                                 class="separation">
                </PDFViewerButton>


                <!-- Download -->
                <PDFViewerButton icon="download"></PDFViewerButton>
            </b-col>
        </b-row>


        <!-- PDF -->
        <b-row cols="1" style="margin-top: 1vh">
            <b-col>
                <div :class="[overflow ? 'overflow-auto' : '']" style="height: 75vh" v-if="pageMode">
                    <pdf :page="currentPage"
                         :src="src"
                         @page-loaded="currentPage = $event"
                         @page-progress="loading = $event"
                         :style="style">
                    </pdf>
                </div>

                <div :class="[overflow ? 'overflow-auto' : '']" style="height: 75vh" v-else>
                    <pdf
                        v-for="page in pageCount"
                        :key="page"
                        :src="src"
                        :page="page"
                        @page-progress="loading = $event"
                        :style="style"
                    ></pdf>
                </div>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import pdf from 'vue-pdf';
import PDFViewerButton from "@/main/vue/components/pdfViewer/PDFViewerButton";

export default {
    name: "PDFViewer",
    props: ['pdfSrc', 'overflow'],
    components: {PDFViewerButton, pdf},
    data() {
        return {
            src: undefined,
            currentPage: 1,
            pageCount: 0,
            loading: 0,
            pageMode: true,
            zoom: 100
        }
    },
    created() {
        this.newSrc()
    },
    methods: {
        changeMode() {
            this.newSrc()
            this.pageMode = !this.pageMode
        },
        changeZoom(val) {
            this.zoom += val
            this.newSrc()
        },
        newSrc() {
            this.src = pdf.createLoadingTask(this.pdfSrc)
            this.src.promise.then(pdf => {
                this.pageCount = pdf.numPages;
            });
        }
    },
    computed: {
        style() {
            return 'width: ' + this.zoom + '%'
        }
    }
}
</script>

<style scoped>
#container {
    width: 100%;
    height: 70vh;
    margin-left: auto;
    margin-right: auto;
    margin-top: 9vh;
    overflow: hidden;
    padding: 0;
}

#backgroundButtons {
    background-color: var(--elsa-blue-transparent);
    padding-top: 0.5vh;
    padding-bottom: 0.5vh;
}

.separation {
    margin-right: 5vw;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    #container {
        font-size: 0.26em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    #container {
        font-size: 0.55em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    #container {
        font-size: 0.45em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    #container {
        font-size: 0.65em;
    }
}

@media (min-width: 992px) {
    #container {
        font-size: 0.95em;
    }
}
</style>
