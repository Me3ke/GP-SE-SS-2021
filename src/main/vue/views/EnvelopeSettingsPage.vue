<template>
    <div class="background" style="background-color: var(--whitesmoke);">
        <Header></Header>

        <BaseHeading :name="getEnv(envId).name" :translate="false" style="position: fixed"></BaseHeading>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "envSettingsPage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header},
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
