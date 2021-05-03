<template>
    <b-nav-item-dropdown right class="my-dropdown-menu">
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
    margin-right: 0;
}

.responsive-img {
    width: 4vw;
    height: auto;
}

.my-dropdown-menu >>> .dropdown-menu {
    color: var(--dark-grey);
    padding-top: 0.1vw;
    padding-bottom: 0.1vw;
    margin: 0;
    border-color: var(--elsa-blue);
}

.my-divider >>> .dropdown-divider {
    margin-top: 0.1vw;
    margin-bottom: 0.1vw;
    background-color: var(--elsa-blue);
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
}

.my-dropdown-item:hover >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
    opacity: 80%;
    transition-duration: 0.4s;
}

.my-dropdown-item:active >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
}

</style>
