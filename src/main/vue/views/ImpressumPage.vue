<template>
    <div>
    <Header></Header>
    <b-container id="impressum-container">
        <BaseHeading style="margin-top: 2ex;" name=""></BaseHeading>
        <h1 id='AboutPage-heading'>{{$t('AboutPage.name')}}</h1>
        <b-row id="b-row-class">
            <b-container id="impressum-provider-class">
                <b-col>
                    <!-- provisionally (because of newline) -->
                    <p>{{$t('AboutPage.provider')}}</p>
                    <p>{{info[0].surname}} {{info[0].lastname}}</p>
                    <p>{{info[0].address}}</p>
                    <p>{{info[0].zip}} {{info[0].city}} </p>
                </b-col>
            </b-container>
            <b-container id="impressum-contact-class">
                <b-col>
                    <!-- provisionally (because of newline) -->
                    <p>{{$t('AboutPage.contact')}}</p>
                    <p>Telefon: {{info[0].tel}}</p>
                    <p>Telefax: {{info[0].fax }}</p>
                    <p>E-Mail: {{info[0].mail}}</p>
                    <p>Website: {{info[0].web}}</p>
                </b-col>
            </b-container>
            <b-container id="redaktionell-text">
                <b-col>
                    <!-- provisionally (because of newline) -->
                    <p> Bei redaktionellen Inhalten:</p>
                    <p>Verantwortlich nach § 55 Abs.2 RStV</p>
                    <p>Moritz Schreiberling</p>
                    <p>Musterstraße 2</p>
                    <p>80999 München</p>
                </b-col>
            </b-container>
        </b-row>
    </b-container>
    </div>
</template>

<script>
import Header from "@/main/vue/components/Header";
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
                tel: "089/1234567-8",
                fax: "089/1234567-9",
                mail: "mail@mustermann.de",
                web: "www.mustermann.de"
            }
            ]

        }
    }
}
</script>

<style scoped>
#impressum-container {
    margin-top: 10em;
}
#impressum-provider-class {
    color: #47525E;
    line-height: 15px;
    text-align: left;
    margin-top: 2em;
    margin-bottom: 2em;

}
#impressum-contact-class {
    color: #47525E;
    line-height: 15px;
    text-align: left;
    margin-bottom: 2em;
}
#redaktionell-text {
    color: #47525E;
    line-height: 15px;
    text-align: left;
    margin-bottom: 2em
}
#AboutPage-heading {
    font-size: 30px;
    margin-top: -70px;
}

@media only screen and (max-width: 1600px) {
    #AboutPage-heading {
        margin-top: -70px;
    }

}
@media only screen and (max-width: 766px) {
    #AboutPage-heading {
        margin-top: -100px;

    }
}

</style>
