<template>
    <div id="container">
        <b-nav>
        <language-switcher></language-switcher>
    </b-nav>
        </div>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
export default {
    name: "Header",
    components: {LanguageSwitcher}
}
</script>

<style scoped>
#container {
    background-color: #5D9543;
    width: 100%;
    margin-left: 0;
    margin-bottom: 2vw;
    padding-top: 1.2vw;
    padding-bottom: 1.2vw;
    position: fixed;
    top: 0;
}
</style>
