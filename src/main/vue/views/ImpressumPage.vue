<template>
    <div>
        <Header></Header>
        <b-container class="full-container">
                <b-container class="head-with-arrow">
                    <b-row class="base-header-row">
                        <b-col>
                            <BaseHeading class="baseHeader" name="" ></BaseHeading>
                            <!--<h2 class="baseHeader-h2">{{$t('AboutPage.name')}}</h2>-->
                        </b-col>
                    </b-row>
                    <b-col style="margin-left: 2em; padding-left: 2em">
                        <h2 class="baseHeader-h2">{{$t('AboutPage.name')}}</h2>

                    </b-col>
                </b-container>


                <b-container class="content-container" style="margin-top: 2em">
                    <b-col style="margin-top: 2em; margin-left: 4em">
                        <b-row class="provider-row" style="">
                            <b-col v-for="(inf, c) in info" :key="c">
                                <b-row style="margin-bottom: -.4em"> <h5 class="row-header">{{$t('AboutPage.provider')}}</h5></b-row>
                                <b-row> {{inf.surname}} {{inf.lastname}} </b-row>
                                <b-row> {{inf.address}} </b-row>
                                <b-row> {{inf.zip}} {{inf.city}} </b-row>
                            </b-col>
                        </b-row>

                        <b-row class="contact-row">
                            <b-col v-for="(con, a) in contact" :key="a">
                                <b-row style="margin-bottom: -.4em"> <h5 class="row-header">{{$t('AboutPage.contact')}}</h5></b-row>
                                <b-row> {{con.tel}} </b-row>
                                <b-row> {{con.fax}} </b-row>
                                <b-row> {{con.mail}} </b-row>
                                <b-row> {{con.web}} </b-row>
                            </b-col>

                        </b-row>

                        <b-row class="redak-row">
                            <b-col v-for="(red, i) in redInfos" :key="i">
                                <b-row style="margin-bottom: -.4em"><h5 class="row-header">{{ $t('AboutPage.editorial') }}</h5></b-row>
                                <b-row v-if="$i18n.locale === 'de'"> <h6 class="row-header">{{red.resp}}</h6> </b-row>
                                <b-row v-else> <h6 class="row-header">{{red.respEN}}</h6> </b-row>
                                <b-row> {{red.surname}} {{red.lastname}} </b-row>
                                <b-row> {{red.address}} </b-row>
                                <b-row> {{red.zip}} {{red.city}} </b-row>
                            </b-col>

                        </b-row>

                        <b-container class="text-input-class">
                            <b-row style="margin-top: 1em">
                                <label style="padding-right: .5em">
                                    <input type="text" v-model="redInfos[0].resp"> <b-button>Enter</b-button>
                                </label>
                            </b-row>
                            <b-row style="padding-bottom: 3em">
                                <label>
                                    <input type="text" v-model="redInfos[0].respEN"> <b-button>Enter</b-button>
                                </label>


                            </b-row>
                        </b-container>

                    </b-col>
                </b-container>
        </b-container>
    </div>

</template>

<script>
import Header from "@/main/vue/components/header/Header";
export default {
    name: "Impressum",
    components: {Header},
    data() {
        return {
            info: [
            {
                surname: "Max",
                lastname: "Mustermann",
                address: "Musterstraße 1",
                zip: "80999",
                city: "München",
            }
            ],
            contact: [
                {
                    tel: "089/1234567-8",
                    fax: "089/1234567-9",
                    mail: "mail@mustermann.de",
                    web: "www.mustermann.de"
                }
            ],
            redInfos: [
                {
                    //Verantwortlich nach § 55 Abs.2
                    resp: "",
                    respEN: "",
                    surname: "Moritz",
                    lastname: "Schreiberling",
                    address: "Musterstraße 2",
                    zip: "80999",
                    city: "München"
                }
            ]

        }
    }
}
</script>

<style scoped>
.provider-row {
    max-width: 100%;
    padding-bottom: 2em
}
.redak-row {
    max-width: 50%;
    text-align: left;

    padding-bottom: 2em;
}
.contact-row {
    max-width: 100%;
    margin-bottom: 2em;

}

.base-header-row {
    margin-left: 0;
    padding-left: -2em;
}
.content-container {
    margin-left: 0em;
}
.baseHeader {
    margin-top: 0.5em;
    margin-left: -3em
}

.baseHeader-h2 {
    margin-left: -30em;
    margin-top: -1em
}


@media screen and (min-width: 1200px){
    .base-header-row {
        margin-left: -1em;
    }
    .full-container {
        margin-left: 2em;
    }
    .content-container {
        margin-left: -2.4em;
    }
    .baseHeader-h2 {
        margin-top: -1em !important;
        margin-left: -31em !important;
    }

}

@media screen and (min-width: 1191px) and (max-width: 1199px) {
    .base-header-row {
        margin-left: -6em;
    }
    .content-container {
        margin-left: -8em;
    }
    .baseHeader-h2 {
        margin-left: -42em;
    }

}



@media screen and (min-width: 992px) and (max-width: 1190px) {
    .full-container {
        margin-left: 4em;
    }
    .baseHeader-h2 {
        margin-left: -23.5em;
        margin-top: -1em;
    }
    .content-container {
        margin-left: -4em;
    }
}

@media screen and (min-width: 801px) and (max-width: 991px) {
    .full-container {
        margin-left: 4em;
    }
    .baseHeader-h2 {
        margin-left: -12em;
    }
}





@media screen and (min-width: 768px) {
    .base-header-row {
        margin-left: -0.8em !important;
        font-size: 1.3em;
    }
    .content-container {
        margin-left: -1.8em;
    }
    .baseHeader-h2 {
        margin-left: -19em;
        margin-top: -0.8em;
    }
    .full-container {
        margin-left: 4em;
    }
}

@media screen and (max-width: 767px) and (min-width: 501px) {
    .base-header-row {
        margin-left: -1em;
        font-size: 1.3em;
    }
    .content-container {
        margin-left: -1.8em;
    }
    .baseHeader-h2 {
        margin-left: -12.5em;
        margin-top: -0.7em;
    }
    .full-container {
        margin-left: 4em;
    }
}

@media screen and (max-width: 500px) and (min-width: 460px) {
    .base-header-row {
        font-size: 1.3em;
        margin-left: -.6em;
    }
    .content-container {
        margin-left: -4em;
    }
    .baseHeader-h2 {
        margin-left: -13em;
        margin-top: -0.7em;
    }
    .full-container {
        margin-left: 3em;
    }
}

@media screen and (max-width: 459px) {
    .baseHeader-h2 {
        margin-top: -0.9em;
        font-size: 1.6em;
        margin-left: -10.5em;
    }

    .baseHeader {
        margin-left: -2.8em;
        margin-top: 0em;
    }
    .full-container {
        margin-left: -1em;
    }
    .content-container {
        padding-right: 2em;
    }
    .text-input-class {
        margin-left: -2em;
    }

    .head-with-arrow {
        margin-left: 1em;
    }

}

@media screen and (max-width: 377px) {
    .baseHeader-h2 {
        font-size: 1.5em;
        margin-left: -8em;
        margin-top: -.8em;
    }
    .redak-row {
        font-size: .7em;
    }
    .contact-row {
        font-size: .7em;
    }
    .provider-row {
        font-size: .7em;
    }
    .row-header {
        font-size: 1.3em;
    }
}

</style>
