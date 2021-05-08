<template>
    <b-navbar toggleable="sm" id="background" sticky>
        <!-- To-Do: Add  real Route to Home -->
        <b-navbar-brand @click="$router.push(`/`)">
            <b-img :src="logo" id="responsive-img" :alt="$t('Header.logo')"></b-img>
        </b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav class="ml-auto">
                <LanguageSwitcher></LanguageSwitcher>
                <Messages></Messages>
                <Avatar></Avatar>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import Avatar from "@/main/vue/components/header/Avatar";
import Messages from "@/main/vue/components/header/Messages";

export default {
    name: "Header",
    components: {Messages, Avatar, LanguageSwitcher},
    data() {
        return {
            logo: require('../../assets/logos/ELSA_small.svg')
        }
    }
}
</script>

<style scoped>

#responsive-img {
    width: calc((10vh + 7vw) / 2);
    height: auto;
}

#background {
    background-image: linear-gradient(to right, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.1)),
    url(../../assets/header_background.png);
    background-size: cover;
    align-content: center;
}
</style>
