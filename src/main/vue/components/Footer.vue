<template>
    <div id="container">
        <!-- TO-Do: Add real route to about page -->
        <router-link :to="`/`" id="text">{{ $t('AboutPage.name') }}</router-link>
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
    padding-top: 0;
    margin-left: 0;
    position: fixed;
    bottom: 0;
    font-size: 0.75em;
}

#text {
    color: whitesmoke;
    font-size: 0.75em;
}


</style>
