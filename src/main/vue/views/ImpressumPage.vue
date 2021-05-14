<template>
    <div>
        <Header></Header>
        <b-container>
                <b-row class="base-header-row">
                    <BaseHeading class="baseHeader" :name="$t('AboutPage.name')" style="margin-top: 0.5em; margin-left: 0"></BaseHeading>
                </b-row>

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
                                <b-row> <h6 class="row-header">{{red.resp}}</h6> </b-row>
                                <b-row> {{red.surname}} {{red.lastname}} </b-row>
                                <b-row> {{red.address}} </b-row>
                                <b-row> {{red.zip}} {{red.city}} </b-row>
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
            redInfos: [
                {
                    resp: "Verantwortlich nach § 55 Abs.2",
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
    max-width: 100%;

    padding-bottom: 2em;
}
.contact-row {
    max-width: 100%;
    margin-bottom: 2em;

}

.base-header-row {
    margin-left: -5em;
}
.content-container {
    margin-left: -5em;
}

@media screen and (min-width: 1194px) and (max-width: 1279px) {
    .base-header-row {
        margin-left: -3em;
    }
    .content-container {
        margin-left: -4em;
    }

}
@media screen and (min-width: 921px) and (max-width: 1190px) {
    .base-header-row {

        font-size: 1.3em;
    }
    .content-container {
        margin-left: -5em;
    }

}


@media screen and (max-width: 768px) and (min-width: 679px) {
    .base-header-row {
        margin-left: -4.3em;
        font-size: 1.3em;
    }
    .content-container {
        margin-left: -3em;
    }

}

@media screen and (min-width: 576px) and (max-width: 678px) {
    .base-header-row {
        margin-left: -1.6em;
        font-size: 1.4em;
    }
    .content-container {
        margin-left: -3em;
    }
}

@media screen and (min-width: 415px) and (max-width: 575px) {
    .base-header-row {
        margin-left: -1em;
        font-size: 1.4em;
    }
    .content-container {
        margin-left: -3em;
    }
}

@media screen and (max-width: 414px) {
    .baseHeader {
        font-size: 1.5em;
    }
    .base-header-row {
        margin-left: -1em;
    }

    .content-container {
        margin-left: -3em;
    }
    .redak-row {
        max-width: 100%;
    }
    .contact-row {
    }
    .provider-row {
        max-width: 80%;
    }
}

@media screen and (max-width: 342px) {
    .base-header-row {
        margin-left: -2em;
        font-size: .6em;
    }
    .content-container {
        margin-left: -3em;
        font-size: .8em;
    }

    .row-header {
        font-size: 1.2em;
    }
}

</style>
