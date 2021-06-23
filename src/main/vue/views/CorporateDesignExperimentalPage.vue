<template>
    <div>
        <Header></Header>

        <BaseHeading name="AdminSettings.corporate.titleExp"></BaseHeading>

        <b-container fluid="xl" style="overflow: hidden">
            <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 80vh">
                <LogoExperimental @modalTrigger="toggleOverflow"></LogoExperimental>
                <ColorExperimental></ColorExperimental>
            </div>
        </b-container>


        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import LogoExperimental from "@/main/vue/components/corporateDesign/LogoExperimental";
import ColorExperimental from "@/main/vue/components/corporateDesign/ColorExperimental";

export default {
    name: "CorporateDesignExperimentalPage",
    components: {ColorExperimental, LogoExperimental, Header, Footer},
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
