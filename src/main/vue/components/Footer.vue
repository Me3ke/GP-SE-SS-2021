<template>
    <div id="container">
        <router-link :to="$i18n.locale === 'de' ? '/de/impressum' : '/en/impressum' " id="text">{{ $t('AboutPage.name') }}</router-link>
    </div>
</template>

<script>
export default {
    name: "Footer"
}
</script>

<style scoped>
#container {
    background-color: var(--dark-grey);
    opacity: 65%;
    width: 100%;
    margin-left: 0;
    padding-top: 0.2vw;
    position: fixed;
    bottom: 0;
}

#text {
    color: whitesmoke;
}
</style>

