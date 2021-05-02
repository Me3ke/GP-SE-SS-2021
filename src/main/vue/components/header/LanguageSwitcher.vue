<!-- To-Do: Make images work -->
<template>
    <b-dropdown size="sm" class="m-5" right no-flip variant="primary">
        <template #button-content>
            <img :src="getFlag()" width="50" height="50" :alt="$t('Header.LanguageSwitcher.language')">
        </template>
        <b-dropdown-item @click.prevent="setLanguage('de')" class="p-0">
            <img :src="germanFlag" width="50" height="50" :alt="$t('Header.LanguageSwitcher.de')">
            <span class="letters">DE</span>
        </b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item @click.prevent="setLanguage('en')">
            <img :src="englishFlag" width="50" height="50" :alt="$t('Header.LanguageSwitcher.en')">
            <span class="letters">EN</span>
        </b-dropdown-item>
    </b-dropdown>
</template>

<script>

export default {
    name: "LanguageSwitcher",
    data() {
        return {
            englishFlag: require('../../assets/gb.svg'),
            germanFlag: require('../../assets/de.svg')
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
            this.$i18n.locale = lang
            this.$router.push({
                params: {lang: lang}
            })
        }
    }
}
</script>

<style scoped>

.letters{
    margin-left: 0.75vw;
}


</style>
