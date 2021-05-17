<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <img :src="getFlag()" class="responsive-img" :alt="$t('Header.LanguageSwitcher.language')">
        </template>
        <b-dropdown-item @click.prevent="setLanguage('de')" class="my-dropdown-item">
            <img :src="germanFlag" class="responsive-img" :alt="$t('Header.LanguageSwitcher.de')">
            <span class="letters">DE</span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item @click.prevent="setLanguage('en')" class="my-dropdown-item">
            <img :src="englishFlag" class="responsive-img" :alt="$t('Header.LanguageSwitcher.en')">
            <span class="letters">EN</span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>

export default {
    name: "LanguageSwitcher",
    data() {
        return {
            englishFlag: require('../../assets/flags/gb.svg'),
            germanFlag: require('../../assets/flags/de.svg')
        }
    },
    methods: {
        getFlag() {
            if (this.$i18n.locale === 'de') {
                return this.germanFlag
            } else {
                return this.englishFlag
            }
        },
        setLanguage(lang) {
            if (this.$i18n.locale !== lang) {
                this.$i18n.locale = lang
                this.$router.push({
                    params: {lang: lang}
                })
            }
        }
    }
}
</script>

<style scoped>
.letters {
    margin-left: 0.75vw;
    position: relative;
    bottom: 0.1vw;
}

.responsive-img {
    height: 2em;
    width: auto;
}

.my-dropdown-menu >>> .dropdown-menu {
    color: var(--dark-grey);
    padding-top: 0.1vw;
    padding-bottom: 0.1vw;
    margin: 0;
    border-color: var(--elsa-blue);
    min-width: 0;
}

.my-divider >>> .dropdown-divider {
    margin-top: 0;
    margin-bottom: 0;
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
    background-color: var(--whitesmoke);
}

.my-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}


/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .dropdown-menu > li {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}

@media (min-width: 992px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}
</style>
