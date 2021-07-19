<template>
    <b-navbar toggleable="sm" id="background" sticky>
        <b-navbar-brand>
            <img v-if="themeColor === '' && !lightEmpty"
                 :src="getLightSource()" class="responsive-img"
                 :alt="$t('Header.logo')"
                 style="margin-left: 2em">
            <img v-if="themeColor === 'darkMode' && !darkEmpty"
                 :src="getDarkSource()" class="responsive-img"
                 :alt="$t('Header.logo')"
                 style="margin-left: 2em">
        </b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav style="height: 4em">
            <b-navbar-nav class="ml-auto">
                <LanguageSwitcher style="margin-top: 0.25em"
                                  @languageSwitch="signalLanguageSwitch"></LanguageSwitcher>
                <ModeSwitch></ModeSwitch>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
import ModeSwitch from "@/main/vue/components/header/ModeSwitch";
import {loadSheet} from "@/main/vue/scripts/stylesheetManipulator";
import {mapGetters} from "vuex";

export default {
    name: "LandingPageHeader",
    components: {ModeSwitch, LanguageSwitcher},
    data() {
        return {
            'theme': ''
        }
    },
    async created() {
        await loadSheet()
        await this.$store.dispatch('theme/getLogos')
    },
    methods: {
        toggleTheme() {
            this.theme = this.theme === 'darkMode' ? '' : 'darkMode';
            document.documentElement.setAttribute('data-theme', this.theme);
            localStorage.setItem('theme', this.theme);
        },
        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        },
        getDarkSource() {
            if (this.logoDarkType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoDark
            } else {
                return 'data:image/' + this.logoDarkType + ';base64,' + this.logoDark
            }
        },
        signalLanguageSwitch() {
            this.$emit('languageSwitch')
        }
    },
    computed: {
        ...mapGetters({
            themeColor: 'theme/getTheme',

            logoLight: 'theme/getLightLogo',
            logoDark: 'theme/getDarkLogo',
            logoLightType: 'theme/getLightLogoType',
            logoDarkType: 'theme/getDarkLogoType'
        }),
        lightEmpty() {
            return this.logoLight === ""
        },
        darkEmpty() {
            return this.logoDark === ""
        }
    }
}
</script>

<style scoped>
#background {
    border-bottom-style: solid;
    border-bottom-width: 1px;
    border-color: var(--dark-grey);
    background-color: var(--elsa-blue-lighter);
    background-size: cover;
    align-content: center;
}

.responsive-img {
    height: 2em;
    width: auto;
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
