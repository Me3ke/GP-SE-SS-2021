<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="dark">
            <b-navbar-brand href="#">NavBar</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav class="ml-auto">
                    <LanguageSwitcher></LanguageSwitcher>
                    <Avatar></Avatar>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import Avatar from "@/main/vue/components/header/Avatar";

export default {
    name: "Header",
    components: {Avatar, LanguageSwitcher}
}
</script>

<style scoped>
</style>
