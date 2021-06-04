<template>
    <div class="background" style="background-color: var(--whitesmoke);">
        <Header></Header>
        <BaseHeading :name="getEnv(envId).name" :translate="false" style="position: fixed"></BaseHeading>

        <b-container fluid="xl">
            <div style="margin-top:7.5vh">
                <div class="custom-control custom-switch">
                    <input type="checkbox" class="custom-control-input" id="allIndividualSwitch" v-model="individual">
                    <label class="custom-control-label" for="allIndividualSwitch"> {{$t('Settings.DocumentSettings.editAll')}} </label>
                </div>
            </div>
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.reader')}}
                </div>
                <div style="padding:2em">
                    <ReaderMenu :readers="readers"></ReaderMenu>
                </div>
            </div>
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.signatory')}}
                </div>
                <div style="padding:2em">
                    <SignatoryMenu :signatories="signatories"></SignatoryMenu>
                </div>
            </div>
        </b-container>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import SignatoryMenu from "@/main/vue/components/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/ReaderMenu";

export default {
    name: "envSettingsPage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, SignatoryMenu, ReaderMenu},
    data() {
      return{
          individual: false,
          signatories: [],
          readers: []
      }
    },
    computed: {
        getEnv() {
            return this.$store.getters.getEnvelope
        }
    }
}
</script>

<style scoped>

.background {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-image: linear-gradient(to bottom, var(--background-fade-one) 0%, var(--background-fade-two) 30%, var(--background-fade-three) 100%), url(../assets/background.png);
    background-repeat: no-repeat;
    background-size: 100% auto;
}
</style>
