<template>
    <div>
        <Header></Header>

        <BaseHeading name="AdminSettings.corporate.title"></BaseHeading>

        <b-container fluid="xl" style="overflow: hidden">
            <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 80vh">
                <LogoNormal @modalTrigger="toggleOverflow"></LogoNormal>
                <ColorNormal></ColorNormal>
                <span class="link" @click="$router.push('/'+ $i18n.locale + '/adminSettings/corporate/experimental')"
                      style="color: var(--dark-grey); opacity: 60%; justify-content: right; text-align: right">
                      {{ $t('AdminSettings.corporate.exp') }}
                </span>
            </div>
        </b-container>


        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import LogoNormal from "@/main/vue/components/corporateDesign/LogoNormal";
import ColorNormal from "@/main/vue/components/corporateDesign/ColorNormal";

export default {
    name: "CorporateDesignNormalPage",
    components: {ColorNormal, LogoNormal, Header, Footer},
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

<style scoped src="../assets/css/settingsPage.css">
</style>

