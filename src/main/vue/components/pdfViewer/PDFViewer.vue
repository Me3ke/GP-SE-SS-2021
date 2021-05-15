<template>
    <b-container id="container">

        <!-- TODO : test with a big document if it is working -->
        <b-row>
            <b-col v-show="loading>0 && loading < 1">
                <b-progress :value="loading*100" :max="100" show-progress animated></b-progress>
            </b-col>
        </b-row>

        <b-row cols="3" id="backgroundButtons">

            <!-- Switch between Page and Scroll Mode -->
            <b-col>
                <PDFViewerButton v-if="pageMode" @click.native="changeMode()" icon="hr"></PDFViewerButton>
                <PDFViewerButton v-if="!pageMode" @click.native="changeMode()" icon="files"></PDFViewerButton>
            </b-col>

            <!-- Jump backward -->
            <b-col>
                <PDFViewerButton :disabled="!pageMode || currentPage <= 1" @click.native="currentPage = 1"
                                 icon="chevron-double-left"
                                 style="margin-right: 0.1vw"></PDFViewerButton>
                <PDFViewerButton :disabled="!pageMode || currentPage <= 1" @click.native="currentPage--"
                                 icon="chevron-left"
                                 style="margin-right: 0.2vw"></PDFViewerButton>
                <!-- Page Display -->
                {{ currentPage }} / {{ pageCount }}

                <!-- Jump forward -->
                <PDFViewerButton :disabled="!pageMode || currentPage >= pageCount" @click.native="currentPage++"
                                 icon="chevron-right"
                                 style="margin-left: 0.2vw"></PDFViewerButton>
                <PDFViewerButton :disabled="!pageMode || currentPage >= pageCount"
                                 @click.native="currentPage = pageCount"
                                 icon="chevron-double-right"
                                 style="margin-left: 0.1vw"></PDFViewerButton>
            </b-col>

            <!-- Download -->
            <b-col>
                <PDFViewerButton icon="download"></PDFViewerButton>
            </b-col>
        </b-row>


        <!-- PDF -->
        <b-row cols="1" style="margin-top: 1vh">
            <b-col>
                <div class="overflow-auto" style="height: 75vh" v-if="pageMode">
                    <pdf :page="currentPage"
                         :src="src"
                         @page-loaded="currentPage = $event"
                         @page-progress="loading = $event"
                         @error="error"></pdf>
                </div>

                <div class="overflow-auto" style="height: 75vh" v-if="!pageMode">
                    <pdf
                        v-for="page in pageCount"
                        :key="page"
                        :src="src"
                        :page="page"
                        @page-progress="loading = $event"
                        @error="error"
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
    props: ['pdfSrc'],
    components: {PDFViewerButton, pdf},
    data() {
        return {
            src: undefined,
            currentPage: 1,
            pageCount: 0,
            loading: 0,
            pageMode: true
        }
    },
    created() {
        this.src = pdf.createLoadingTask(this.pdfSrc)
        this.src.promise.then(pdf => {
            this.pageCount = pdf.numPages;
        });
    },
    methods: {
        error(e) {
            console.log(e);
        },
        changeMode() {
            this.src = pdf.createLoadingTask(this.pdfSrc)
            this.src.promise.then(pdf => {
                this.pageCount = pdf.numPages;
            });
            this.pageMode = !this.pageMode
        }
    }
}
</script>

<style scoped>
#container {
    width: 75%;
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

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    #container {
        font-size: 0.3em;
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
