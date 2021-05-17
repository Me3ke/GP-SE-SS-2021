<template>
    <div style="width: 100%">
        <Header></Header>

        <BaseHeading :name="document.title" :translate="false" style="position: fixed;"></BaseHeading>

        <!-- Displays that preview is possible -->
        <b-container v-if="document.dataType === 'PDF'"
                     style="width: 100%; margin-top: 0; margin-right: auto; margin-left: auto; padding: 0;">
            <b-row style="width: 100%; margin: auto; padding: 0">
                <b-col cols="9">
                    <PDFViewer :pdf-src=src></PDFViewer>
                </b-col>

                <b-col cols="3" id="textCol">
                    <!-- Displays if user already proofread -->
                    <b-row style="margin: auto; display: block"
                           v-if="document.reader === true && document.read === true">
                        <h6>
                            {{ $t("DocumentPage.didRead") }}
                        </h6>
                        <hr v-if="document.signatory === true || document.signed === true">
                    </b-row>

                    <!-- Displays if user should proofread -->
                    <b-row style="margin: auto; display: block"
                           v-else-if="document.reader === true && document.read === false">
                        <h6>
                            {{ $t("DocumentPage.doRead") }}
                        </h6>
                        <LightButtonIconText icon="eyeglasses" text="DocumentPage.read"></LightButtonIconText>
                        <hr v-if="document.signatory === true || document.signed === true">
                    </b-row>

                    <!-- Displays if user already signed -->
                    <b-row style="margin: auto; display: block"
                           v-if="document.signatory === true && document.signed === true">
                        <h6>
                            {{ $t("DocumentPage.didSign") }}
                        </h6>
                    </b-row>

                    <!-- Displays if user should sign -->
                    <b-row style="margin: auto; display: block"
                           v-else-if="document.signatory === true && document.signed === false">
                        <h6>
                            {{ $t("DocumentPage.doSign") }}
                        </h6>
                        <LightButtonIconText icon="pen" text="DocumentPage.sign"></LightButtonIconText>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>

        <!-- Displays that preview is not possible -->
        <b-container v-if="document.dataType !== 'PDF'" fluid="sm" class="container">
            <b-row style="margin: auto; display: block">
                <h6>
                    {{ $t("DocumentPage.noView") }}
                </h6>
                <LightButtonIconText icon="download" text="DocumentPage.download"></LightButtonIconText>
                <hr v-if="document.signatory === true || document.signed === true || document.reader === true || document.read === true">
            </b-row>

            <!-- Displays if user already proofread -->
            <b-row v-if="document.reader === true && document.read === true" style="margin: auto; display: block">
                <h6>
                    {{ $t("DocumentPage.didRead") }}
                </h6>
                <hr v-if="document.signatory === true || document.signed === true">
            </b-row>

            <!-- Displays if user should proofread -->
            <b-row v-else-if="document.reader === true && document.read === false"
                   style="margin: auto; display: block">
                <h6>
                    {{ $t("DocumentPage.doRead") }}
                </h6>
                <LightButtonIconText icon="eyeglasses" text="DocumentPage.read"></LightButtonIconText>
                <hr v-if="document.signatory === true || document.signed === true">
            </b-row>

            <!-- Displays if user already signed -->
            <b-row v-if="document.signatory === true && document.signed === true"
                   style="margin: auto; display: block">
                <h6>
                    {{ $t("DocumentPage.didSign") }}
                </h6>
            </b-row>

            <!-- Displays if user should sign -->
            <b-row v-else-if="document.signatory === true && document.signed === false"
                   style="margin: auto; display: block">
                <h6>
                    {{ $t("DocumentPage.doSign") }}
                </h6>
                <LightButtonIconText icon="pen" text="DocumentPage.sign"></LightButtonIconText>
            </b-row>
        </b-container>


        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import LightButtonIconText from "@/main/vue/components/LightButtonIconText";

import {mapGetters} from 'vuex';

export default {
    name: "DocumentPage",
    props: {
        docId: {
            type: [Number, String]
        },
        envId: {
            type: [Number, String]
        },
        userId: {
            type: [Number, String]
        }
    },
    components: {LightButtonIconText, PDFViewer, Footer, Header},
    data() {
        return {
            src: "https://cdn.filestackcontent.com/5qOCEpKzQldoRsVatUPS"
        }
    },
    created() {
        this.$store.dispatch('fetchDocument', this.docId)
    },
    computed: {
        ...mapGetters({
            document: 'document'
        })
    }
}
</script>

<style scoped>

hr {
    background-color: var(--dark-grey);
}

#textCol {
    display: block;
    margin: auto;
    padding-right: 5vw;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) and (min-height: 370px) {
    .container {
        margin-top: 4em;
    }

    h6 {
        font-size: 0.4em;
    }
}

@media (max-width: 575.98px) and (max-height: 369.98px) {
    .container {
        margin-top: 2em;
    }

    h6 {
        font-size: 0.4em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .container {
        margin-top: 3em;
    }

    h6 {
        font-size: 0.45em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .container {
        margin-top: 3em;
    }

    h6 {
        font-size: 0.5em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .container {
        margin-top: 6em;
    }
}

@media (min-width: 992px) {
    .container {
        margin-top: 5em;
    }
}
</style>
