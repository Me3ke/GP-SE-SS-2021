<template>
    <div>
        <Header></Header>

        <BaseHeading name="AdminSettings.corporate.title"></BaseHeading>

        <b-container fluid="xl" style="overflow: hidden">
            <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 85vh">
                <LogoNormal @modalTrigger="toggleOverflow"></LogoNormal>
            </div>
        </b-container>

        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import LogoNormal from "@/main/vue/components/corporateDesign/LogoNormal";

export default {
    name: "CorporateDesignNormalPage",
    components: {LogoNormal, Header, Footer},
    data() {
        return {
            showOverflow: true
        }
    },
    methods: {
        toggleOverflow() {
            this.showOverflow = !this.showOverflow
        }
    }
}
</script>

<style scoped>

</style>
