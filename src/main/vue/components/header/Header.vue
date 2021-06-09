<template>
    <b-navbar toggleable="sm" id="background" sticky style="position: fixed; width: 100%; height: 5vh">
        <!-- To-Do: Add  real Route to Home -->
        <b-navbar-brand @click="$router.push(`/`)">
            <b-img v-if="theme === '' " :src="logoLightMode" class="responsive-img" :alt="$t('Header.logo')"></b-img>
            <b-img v-else :src="logoDarkMode" class="responsive-img" :alt="$t('Header.logo')"></b-img>
        </b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav style="height: 4em">
            <b-navbar-nav class="ml-auto">
                <LanguageSwitcher style="margin-top: 0.25em"></LanguageSwitcher>
                <Messages v-if="user.firstLogin"></Messages>
                <Avatar v-if="user.firstLogin"></Avatar>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import Avatar from "@/main/vue/components/header/Avatar";
import Messages from "@/main/vue/components/header/Messages";
import {mapGetters} from "vuex";

export default {
    name: "Header",
    components: {Messages, Avatar, LanguageSwitcher},
    data() {
        return {
            logoLightMode: require('../../assets/logos/ELSA_small.svg'),
            logoDarkMode: require('../../assets/logos/ELSA_small_darkmode.svg'),
        }
    },
    computed: {
        ...mapGetters({
            theme: 'theme/getTheme',
            user: 'getUser'
        })
    }
}
</script>

<style scoped>

.responsive-img {
    height: 2em;
    width: auto;
}

#background {
    background-image: linear-gradient(to right, var(--headerFadeOne), var(--headerFadeTwo), var(--headerFadeThree), var(--headerFadeTwo), var(--headerFadeOne)),
    url(../../assets/header_background.png);
    background-size: cover;
    align-content: center;
    z-index: 1000000000;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.75em;
    }
}

@media (min-width: 992px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.75em;
    }
}
</style>
