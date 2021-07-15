<template>
    <div style="background-color: var(--whitesmoke); padding-bottom: 2em">
        <Header></Header>
        <BaseHeading name="Impressum"></BaseHeading>
        <b-container class="full-container">
            <b-container>
                <b-row class="flex-column-reverse flex-md-row">
                    <b-col class="col1" cols="7">
                        <b-container class="impressum-content" v-if="impressumMessage" v-html="impressumMessage" style="margin-top: 2em"></b-container>

                    </b-col>
                    <b-col class="col2">
                        <!--- Elsa Logo --->
                        <b-row align-h="center" align-v="center">
                            <img v-if="theme === ''" :src="elsaLight"  class="img-fluid" style="padding-bottom: 1em">
                            <img v-else :src="elsaDark"  class="img-fluid" style="padding-bottom: 1em">

                        </b-row>
                        <b-row align-h="center" align-v="center">
                            <img v-if="theme === '' && !lightEmpty"
                                 :src="getLightSource()" class="img-fluid-logo"
                                 style="margin-left: 2em">
                            <img v-if="theme === 'darkMode' && !darkEmpty"
                                 :src="getDarkSource()" class="img-fluid-logo"
                                 style="margin-left: 2em">
                        </b-row>

                    </b-col>
                </b-row>
            </b-container>
        </b-container>
    </div>

</template>

<script>
import Header from "@/main/vue/components/header/Header";
import {mapGetters} from "vuex";
export default {
    name: "Impressum",
    components: {Header},
    data() {
        return {
            elsaLight: require('../assets/logos/ELSA_medium.svg'),
            elsaDark: require('../assets/logos/ELSA_medium_darkmode.svg'),
        }
    },
    computed: {
        ...mapGetters({
            impressumMessage: 'impressum/getImpressumResponse',
            theme: 'theme/getTheme',
            logoLightType: 'theme/getLightLogoType',
            logoDarkType: 'theme/getDarkLogoType',
            logoLight: 'theme/getLightLogo',
            logoDark: 'theme/getDarkLogo',
        }),
        lightEmpty() {
            return this.logoLight === ""
        }
        ,
        darkEmpty() {
            return this.logoDark === ""
        }
    },
    methods: {

        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        }
        ,
        getDarkSource() {
            if (this.logoDarkType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoDark
            } else {
                return 'data:image/' + this.logoDarkType + ';base64,' + this.logoDark
            }
        }

    },

    mounted() {
        this.$store.dispatch('impressum/fetchImpressum')
    }
}
</script>

<style scoped>

/deep/ .impressum-content p {
    margin-bottom: 0;
    text-align: left;
    padding-left: 2em;
    color: var(--dark-grey);
}

/deep/ .impressum-content {
   /* border: 1px solid var(--dark-grey);*/
}


.col2 .img-fluid {
    max-width: 60%;
}
.col2 .img-fluid-logo {
    max-width: 25%;
}

.col2 {
    align-self: center !important;
}


@media (max-width: 268px) {
    /deep/ .impressum-content p {
        text-align: center;
        font-size: .7em;
    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;
    }

    .col2 .img-fluid {
        max-width: 40%;
    }

    .col2 .img-fluid-logo {
        max-width: 14%;
        margin-right: 2em;
    }

}

@media (max-width: 304px) and (min-width: 269px) {
    /deep/ .impressum-content p {
        text-align: center;
        font-size: .68em;
        margin-right: 3em;

    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid {
        max-width: 40%;
    }

    .col2 .img-fluid-logo {
        max-width: 14%;
        margin-right: 2em;
    }

}


@media (max-width: 328px) and (min-width: 305px) {
    /deep/ .impressum-content p {
        text-align: center;
        font-size: .72em;
        margin-right: 3em;

    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid {
        max-width: 40%;
    }

    .col2 .img-fluid-logo {
        max-width: 14%;
        margin-right: 2em;
    }

}



@media (max-width: 348px) and (min-width: 328px) {
    /deep/ .impressum-content p {
        text-align: center;
        font-size: .8em;
        margin-right: 3em;

    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid {
        max-width: 40%;
    }

    .col2 .img-fluid-logo {
        max-width: 14%;
        margin-right: 2em;
    }

}

@media (max-width: 388px) and (min-width: 349px) {
    /deep/ .impressum-content p {
        text-align: center;
        font-size: .87em;
        margin-right: 3em;

    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid {
        max-width: 40%;
    }

    .col2 .img-fluid-logo {
        max-width: 14%;
        margin-right: 2em;
    }

}


@media (max-width: 575.98px) and (min-width: 388px) {
    /deep/ .impressum-content p {
        text-align: center;
        margin-right: 3em;
    }

    .col1 {
        max-width: 100%;
        font-size: 1em;
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid {
        max-width: 30%;
    }

    .col2 .img-fluid-logo {
        max-width: 10%;
        margin-right: 2em;

    }

}

@media (min-width: 576px) and (max-width: 767.98px) {
    .col2 .img-fluid {
        max-width: 30%;
    }
    /deep/ .impressum-content p {
        margin-right:  3em;
        text-align: center;
        font-size: .8em;
    }
    .col1 {
        align-self: center;
        padding-bottom: 1em;

    }

    .col2 .img-fluid-logo {
        max-width: 10%;
        margin-right: 2em;

    }
}



</style>
