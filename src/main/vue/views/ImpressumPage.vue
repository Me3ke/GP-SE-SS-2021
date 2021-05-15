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
                        <b-row class="redak-row">
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
                    respEN: "TEST",
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
    }
    .baseHeader-h2 {
        margin-top: -1em !important;
        margin-left: -30.3em !important;
    }

}

@media screen and (min-width: 1191px) and (max-width: 1199px) {
    .base-header-row {
        margin-left: -6em;
    }
    .content-container {
        margin-left: -5.8em;
    }
    .baseHeader-h2 {
        margin-left: -30em;
        margin-top: -.9em;
    }

}



@media screen and (min-width: 992px) and (max-width: 1190px) {
    .full-container {
        margin-left: 0;
    }
    .baseHeader-h2 {
        margin-left: -23.5em;
        margin-top: -.8em;
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
        margin-left: -17em;
        margin-top: -.7em;
    }
    .content-container {
        margin-left: 1em;
    }
}


@media screen and (min-width: 768px) and (max-width: 800px) {
    .base-header-row {
        margin-left: -1.8em;
        font-size: 1.3em;
    }
    .content-container {
        margin-left: -1.8em;
    }
    .baseHeader-h2 {
        margin-left: -20em;
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
        font-size: 1em;
        margin-left: -3em;
    }
    .content-container {
        margin-left: -3em;
    }
    .baseHeader-h2 {
        margin-left: -17em;
        margin-top: -0.9em;
        font-size: 1.6em;
    }
    .full-container {
        margin-left: 3em;
    }

}

@media screen and (min-width: 459px) and (max-width: 499px) {
    .baseHeader-h2 {
        margin-top: -0.9em;
        font-size: 1.6em;
        margin-left: -12.5em;
    }

    .baseHeader {
        margin-left: -2.8em;
        margin-top: 0;
    }
    .full-container {
        margin-left: -1em;
    }
    .content-container {
        padding-right: 2em;
    }
    .head-with-arrow {
        margin-left: 1em;
    }

}
@media screen and (min-width: 410px) and (max-width: 458px) {
    .baseHeader-h2 {
        font-size: 1.5em;
        margin-left: -13em;
        margin-top: -.8em;
    }
    .redak-row {
        font-size: .7em;
    }
    .base-header-row {
        margin-left: 0.5em;
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
    .row-header2 {
        font-size: .8em;
    }
}

@media screen and (max-width: 409px) and (min-width: 355px) {
    .baseHeader-h2 {
        font-size: 1.5em;
        margin-left: -11em;
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
    .row-header2 {
        font-size: .8em;
    }
}

@media screen and (max-width: 354px) and (min-width: 330px){
    .baseHeader-h2 {
        font-size: 1.5em;
        margin-left: -10em;
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
    .row-header2 {
        font-size: .8em;
    }
}

@media screen and (max-width: 329px) {
    .baseHeader-h2 {
        font-size: 1.5em;
        margin-left: -9em;
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
    .row-header2 {
        font-size: .8em;
    }
    .content-container {
        margin-left: -1em;
    }
}

@media screen and (max-width: 304px) {
    .redak-row {
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
        margin-left: -7em;
        margin-top: -.7em;
    }

}
</style>
