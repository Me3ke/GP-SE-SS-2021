<template>
    <b-navbar toggleable="sm" id="background" sticky :class="[mobile ? 'mobile' : 'normal']">
        <!-- To-Do: Add  real Route to Home -->
        <b-navbar-brand @click="$router.push(`/`)">
            <b-img v-if="theme === '' " :src="elsaLight" class="responsive-img" :alt="$t('Header.logo')"></b-img>
            <b-img v-else :src="elsaDark" class="responsive-img" :alt="$t('Header.logo')"></b-img>
            <img v-if="theme === '' && !lightEmpty"
                 :src="getLightSource()" class="responsive-img"
                 :alt="$t('Header.logo')"
                 style="margin-left: 2em">
            <img v-if="theme === 'darkMode' && !darkEmpty"
                 :src="getDarkSource()" class="responsive-img"
                 :alt="$t('Header.logo')"
                 style="margin-left: 2em">
        </b-navbar-brand>
        <b-navbar-toggle target=" nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav style="height: 4em">
            <b-navbar-nav class="ml-auto">
                <LanguageSwitcher style="margin-top: 0.25em"></LanguageSwitcher>
                <Messages v-if="user.firstLogin && isNotGuest"></Messages>
                <Avatar v-if="user.firstLogin && isNotGuest"></Avatar>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import Avatar from "@/main/vue/components/header/Avatar";
import Messages from "@/main/vue/components/header/Messages";
import {mapGetters} from "vuex";
import {loadSheet} from "@/main/vue/scripts/stylesheetManipulator";
import _ from "lodash";

export default {
    name: "Header",
    components: {Messages, Avatar, LanguageSwitcher},
    data() {
        return {
            elsaLight: require('../../assets/logos/ELSA_small.svg'),
            elsaDark: require('../../assets/logos/ELSA_small_darkmode.svg'),
            mobile: window.innerWidth < 576
        }
    },
    async created() {
        await loadSheet()
        await this.$store.dispatch('theme/getLogos')
        if (!this.$route.params.tokenId) {
            await this.$store.dispatch('messages/fetchMessages')
        }
    },
    mounted() {
        // reacts when screen size changes
        window.addEventListener("resize", this.updateMobile);
    },
    destroyed() {
        // removes event listener
        window.removeEventListener("resize", this.updateMobile);
    },
    methods: {
        updateMobile() {
            // sets mobile depending on screen width (if smaller than 576 dropdown menu is collapsed)
            this.mobile = window.innerWidth < 576
        }
        ,
        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        }
        ,
        getDarkSource() {
            if (this.logoDarkType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoDark
            } else {
                return 'data:image/' + this.logoDarkType + ';base64,' + this.logoDark
            }
        }
    }
    ,
    computed: {
        ...
            mapGetters({
                theme: 'theme/getTheme',
                user: 'getUser',

                logoLight: 'theme/getLightLogo',
                logoDark: 'theme/getDarkLogo',
                logoLightType: 'theme/getLightLogoType',
                logoDarkType: 'theme/getDarkLogoType',

                guest: 'getGuestSignatory'
            }),
        lightEmpty() {
            return this.logoLight === ""
        }
        ,
        darkEmpty() {
            return this.logoDark === ""
        }
        ,
        isNotGuest() {
            return _.isEmpty(this.getGuestSignatory);
        }
    }
}
</script>

<style scoped>

.responsive-img {
    height: 2em;
    width: auto;
}

.normal {
    position: fixed;
    width: 100%;
    min-height: fit-content;
}

.mobile {
    position: fixed;
    width: 100%;
    height: fit-content;
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
