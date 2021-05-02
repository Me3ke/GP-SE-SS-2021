<!-- To-Do: Make images work -->
<template>
    <b-dropdown id="dropdown-1" class="m-md-2">
        <template #button-content>
            {{ $t('Header.LanguageSwitcher.language') }}
        </template>
        <b-dropdown-item @click.prevent="setLanguage('de')">
            {{ $t('Header.LanguageSwitcher.de') }}
        </b-dropdown-item>
        <b-dropdown-item @click.prevent="setLanguage('en')">
            {{ $t('Header.LanguageSwitcher.en') }}
        </b-dropdown-item>
    </b-dropdown>
</template>

<script>
export default {
    name: "LanguageSwitcher",
    methods: {
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

</style>
