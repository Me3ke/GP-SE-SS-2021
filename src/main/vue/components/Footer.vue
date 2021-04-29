<template>
    <div class="container">
        <router-link :to="`/${$i18n.locale}/about`" class="text">{{ $t('AboutPage.name') }}</router-link>
    </div>
</template>

<script>
export default {
    name: "Footer"
}
</script>

<style scoped>
.container {
    background-color: var(--dark-grey);
    opacity: 65%;
    box-sizing: border-box;
    width: 97vw;
    max-width: 97%;
    display: block;
}

.text {
    color: whitesmoke;
}
</style>

