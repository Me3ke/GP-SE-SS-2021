<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand href="#">NavBar</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav class="ml-auto">
                    <LanguageSwitcher></LanguageSwitcher>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
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
</style>
