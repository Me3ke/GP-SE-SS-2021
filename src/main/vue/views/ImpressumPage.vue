<template>
    <div>
        <Header></Header>
        <b-container class="full-container">
                <b-container class="head-with-arrow">
                    <b-row class="base-header-row">
                        <b-col>
                            <BaseHeading class="baseHeader" name="" ></BaseHeading>
                        </b-col>
                    </b-row>
                    <b-col style="margin-left: 2em; padding-left: 2em">
                        <h2 class="baseHeader-h2">{{$t('AboutPage.name')}}</h2>
                    </b-col>
                </b-container>
                <b-container class="content-container" style="margin-top: 6em">
                    <b-col style="margin-top: -5em; margin-left: 4em">
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
                        <b-row class="imprint-row">
                            <b-col v-for="(imprintInfo, i) in imprintInfos" :key="i">
                                <b-row style="margin-bottom: -.4em"><h5 class="row-header">{{ $t('AboutPage.editorial') }}</h5></b-row>
                                <b-row v-if="$i18n.locale === 'de'"> <h6 class="row-header2">{{imprintInfo.respDE}}</h6> </b-row>
                                <b-row v-else> <h6 class="row-header2">{{imprintInfo.respEN}}</h6> </b-row>
                                <b-row style="margin-top: -.5em"> {{imprintInfo.surname}} {{imprintInfo.lastname}} </b-row>
                                <b-row> {{imprintInfo.address}} </b-row>
                                <b-row> {{imprintInfo.zip}} {{imprintInfo.city}} </b-row>
                            </b-col>
                        </b-row>
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

            // Example Data for the About us Page
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
            imprintInfos: [
                {
                    //Verantwortlich nach § 55 Abs.2
                    respDE: "Verantwortlich nach § 55 Abs.2",
                    // set the english resp text
                    respEN: "Responsible according to § 55 Abs. 2",
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
.imprint-row {
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
    margin-left: 0;
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
        margin-left: -.6em;
        margin-top: 8em !important;
    }
    .baseHeader-h2 {
        margin-top: -1em !important;
        margin-left: -30.3em !important;
    }

}

@media screen and (min-width: 1191px) and (max-width: 1199px) {
    .base-header-row {
        margin-left: -6.8em;
    }
    .content-container {
        margin-left: -5.8em;
    }
    .baseHeader-h2 {
        margin-left: -30em;
        margin-top: -1em;
    }

}



@media screen and (min-width: 992px) and (max-width: 1190px) {
    .full-container {
        margin-left: 0;
    }
    .baseHeader-h2 {
        margin-left: -23.5em;
        margin-top: -.9em;
    }
    .content-container {
        margin-left: .6em;
    }
}

@media screen and (min-width: 801px) and (max-width: 991px) {
    .full-container {
        margin-left: 1em;

    }
    .baseHeader-h2 {
        margin-left: -15em;
        margin-top: -.8em;
    }
    .content-container {
        margin-left: 1em;
        padding-top: 1.2em;
    }
}


@media screen and (min-width: 768px) and (max-width: 800px) {
    .base-header-row {
        margin-left: -1.8em;
        font-size: 1.3em;
    }
    .content-container {
        margin-left: -1.8em;
        padding-top: 1.2em;
    }
    .baseHeader-h2 {
        margin-left: -19em;
        margin-top: -.8em;
    }
    .full-container {
        margin-left: 4em;
    }
}

@media screen and (max-width: 767px) and (min-width: 501px) {
    .base-header-row {
        font-size: 1.3em;
        margin-left: -2em;
    }
    .content-container {
        margin-left: -2.7em;
        padding-top: .7em;
    }
    .baseHeader-h2 {
        margin-left: -14em;
        margin-top: -.8em;
    }
    .full-container {
        margin-left: 4em;
    }
}

@media screen and (max-width: 500px) and (min-width: 459px) {
    .base-header-row {
        font-size: 1em;
        margin-left: -3em;
    }
    .row-header {
        font-size: 1.1em;
    }
    .content-container {
        margin-left: -4em;
    }
    .baseHeader-h2 {
        margin-left: -17em;
        margin-top: -.9em;
        font-size: 1.6em;
        padding-left: 0;
    }
    .full-container {
        margin-left: 3em;
    }

}


@media screen and (min-width: 410px) and (max-width: 458px) {
    .baseHeader-h2 {
        font-size: 1.8em;
        margin-left: -10em;
        margin-top: -.9em;
        padding-left: .4em;
    }
    .imprint-row {
        font-size: 1em;
        max-width: 100%;
    }
    .base-header-row {
        margin-left: 0.1em;
    }
    .contact-row {
        font-size: 1em;
        max-width: 100%;

    }
    .provider-row {
        font-size: 1em;
        max-width: 100%;

    }
    .row-header {
        font-size: 1.3em;
    }
    .row-header2 {
        font-size: 1em;
    }
    .content-container {
        padding-left: 0;
    }
}

@media screen and (max-width: 409px) and (min-width: 355px) {
    .baseHeader-h2 {
        font-size: 1.8em;
        margin-top: -.9em;
        padding-left: 21.8em;

    }
    .imprint-row {
        font-size: 1em;
        max-width: 100%;
    }
    .contact-row {
        font-size: 1em;
        max-width: 100%;
    }
    .provider-row {
        font-size: 1em;
        max-width: 100%;
    }
    .row-header {
        font-size: 1.3em;
    }
    .row-header2 {
        font-size: .8em;
    }
    .content-container {
        padding-top: .7em;
        padding-left: 0;
        margin-left: -1.3em;
    }
}

@media screen and (max-width: 354px) and (min-width: 330px){
    .baseHeader-h2 {
        font-size: 1.7em;
        margin-left: -8em;
        margin-top: -.9em;
        padding-left: .5em;
        padding-bottom: .6em;
    }

    .imprint-row {
        font-size: 1em;
        max-width: 100%;
        margin-left: -3.5em;
    }
    .contact-row {
        font-size: 1em;
        max-width: 100%;
        margin-left: -3.5em;
    }
    .provider-row {
        font-size: 1em;
        max-width: 100%;
        margin-left: -3.5em;
    }
    .row-header {
        font-size: 1.3em;
    }
    .row-header2 {
        font-size: .8em;
    }
}

@media screen and (max-width: 329px) {
    .baseHeader-h2 {
        font-size: 1.7em;
        margin-left: -7.5em;
        margin-top: -.9em;
        padding-left: .5em;
        padding-bottom: .6em;
    }
    .imprint-row {
        font-size: 1em;
        max-width: 100%;
    }
    .contact-row {
        font-size: 1em;
        max-width: 100%
    }
    .provider-row {
        font-size: 1em;
        max-width: 100%
    }
    .row-header {
        font-size: 1.3em;
    }
    .row-header2 {
        font-size: .8em;
    }
    .content-container {
        margin-left: -2em;
    }
}

@media screen and (max-width: 304px) and (min-width: 294px) {
    .imprint-row {
        font-size: .9em;
        max-width: 80%;
    }
    .contact-row {
        font-size: .9em;
        max-width: 80%;
    }
    .provider-row {
        font-size: .9em;
        max-width: 80%;
    }
    .baseHeader-h2 {
        margin-left: -6.5em;
        margin-top: -.8em;
    }
    .content-container {
        margin-left: -2em;
    }

}

@media screen and (max-width: 293px) and (min-width: 289px) {
    .imprint-row {
        font-size: .8em;
        max-width: 80%;
    }
    .contact-row {
        font-size: .8em;
    }
    .provider-row {
        font-size: .8em;
    }
    .baseHeader-h2 {
        margin-left: -6.7em;
        margin-top: -.8em;
    }

}
@media screen and (max-width: 288px) and (min-width: 240px) {
    .imprint-row {
        font-size: .8em;
        max-width: 80%;
    }
    .contact-row {
        font-size: .8em;
    }
    .provider-row {
        font-size: .8em;
    }
    .baseHeader-h2 {
        margin-left: -6em;
        margin-top: -.9em;
        padding-left: .9em;
    }
    .full-container {
        padding-left: 1em;
    }

}

@media screen and (max-width: 239px) {
    .baseHeader-h2 {
        position: center;
        margin-left: -5em;
        margin-top: .4em;
        padding-bottom: 0;
    }
    .content-container {
        margin-left: -4em;
    }

}

</style>
