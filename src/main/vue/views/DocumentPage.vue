<template>
    <div style="width: 100%; height: 100vh; background-color: var(--whitesmoke)">
        <Header></Header>

        <!-- TODO: add advanced prop based on api -->
        <SignPopUp v-if="showSign" :documents="[document]" :advanced="true" @advancedTrigger="toggleSign()"></SignPopUp>

        <!-- Displays if document cannot get fetched by api -->
        <BaseHeading v-if="!hasError()" name=" " :translate="false" style="position: fixed;"></BaseHeading>
        <div v-if="!hasError()" class="d-flex align-items-center" style="height: 80vh">
            <b-container>
                <b-row cols="2">

                    <b-col cols="4">
                        <img :src="turtle" class="responsive-img" alt="turtle">
                    </b-col>

                    <b-col cols="8">
                        <b-row class="text-center" align-v="center" align-h="center" cols="1">
                            <b-col>
                                <h1>{{ $t('DocumentPage.errors.notFound') }}</h1>
                            </b-col>
                            <b-col>
                                <h3>{{ $t('DocumentPage.errors.refresh') }}</h3>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <!-- Displays if document can get fetched by api -->
        <div v-else>
            <BaseHeading :name="document.title" :translate="false" style="position: fixed;"></BaseHeading>

            <!-- Displays that preview is possible -->
            <b-container v-if="document.dataType === 'pdf'"
                         style="width: 100%; margin-top: 0; margin-right: auto; margin-left: auto; padding: 0;">
                <b-row style="width: 100%; margin: auto; padding: 0">
                    <b-col cols="9">
                        <PDFViewer :pdf-src=getPDF() :overflow="showOverflow"></PDFViewer>
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
                            <GreenButtonIconText icon="eyeglasses" text="DocumentPage.read"
                                                 @click.native="showProofread = true"></GreenButtonIconText>
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
                            <GreenButtonIconText icon="pen" text="DocumentPage.sign"
                                                 @click.native="toggleSign()"></GreenButtonIconText>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>

            <!-- Displays that preview is not possible -->
            <b-container v-if="document.dataType !== 'pdf'" fluid="sm" class="container">
                <b-row style="margin: auto; display: block">
                    <h6>
                        {{ $t("DocumentPage.noView") }}
                    </h6>
                    <GreenButtonIconText icon="download" text="DocumentPage.download"></GreenButtonIconText>
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
                    <GreenButtonIconText icon="eyeglasses" text="DocumentPage.read"
                                         @click.native="showProofread = true"></GreenButtonIconText>
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
                    <GreenButtonIconText icon="pen" text="DocumentPage.sign"
                                         @click.native="toggleSign()"></GreenButtonIconText>
                </b-row>
            </b-container>

        </div>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";
import GreenButtonIconText from "@/main/vue/components/GreenButtonIconText";
import SignPopUp from "@/main/vue/components/TwoFakAuth/SignPopUp";

import _ from 'lodash';
import {mapGetters} from 'vuex';


export default {
    name: "DocumentPage",
    components: {SignPopUp, GreenButtonIconText, PDFViewer, Footer, Header}, data() {
        return {
            turtle: require('../assets/turtle.svg'),
            showProofread: false,
            showSign: false,
            showOverflow: true
        }
    },
    methods: {
        getPDF() {
            let chars = atob(this.document.data);
            let array = new Uint8Array(chars.length);
            for (let i = 0; i < chars.length; i++) {
                array[i] = chars.charCodeAt(i)
            }

            return array
        },
        hasError() {
            return _.isEmpty(this.getError);
        },
        toggleSign() {
            this.showSign = !this.showSign
            this.showOverflow = !this.showOverflow
        }
    },
    created() {
        this.$store.dispatch('document/fetchDocument', {envId: this.envId, docId: this.docId})
    },
    computed: {
        ...mapGetters({
            document: 'document/getDocument',
            getError: 'document/getErrorGetDocument'
        }),
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        }
    }
}
</script>

<style scoped>

.responsive-img {
    height: 15em;
    width: auto;
}

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

    h1 {
        font-size: 1.3em;
    }

    h3 {
        font-size: 0.6em;
    }

    .responsive-img {
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

    h1 {
        font-size: 1.3em;
    }

    h3 {
        font-size: 0.6em;
    }

    .responsive-img {
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

    h1 {
        font-size: 1.5em;
    }

    h3 {
        font-size: 0.8em;
    }

    .responsive-img {
        font-size: 0.6em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .container {
        margin-top: 3em;
    }

    h6 {
        font-size: 0.5em;
    }

    h1 {
        font-size: 1.6em;
    }

    h3 {
        font-size: 0.9em;
    }

    .responsive-img {
        font-size: 0.9em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .container {
        margin-top: 6em;
    }

    h1 {
        font-size: 1.6em;
    }

    h3 {
        font-size: 0.9em;
    }

    .responsive-img {
        font-size: 0.9em;
    }
}

@media (min-width: 992px) {
    .container {
        margin-top: 5em;
    }
}
</style>
