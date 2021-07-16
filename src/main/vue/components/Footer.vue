<template>
    <div id="container">
        <span id="text" @click="goToAboutPage()">
            {{
                $t('AboutPage.name')
            }}
        </span>
    </div>
</template>

<script>
export default {
    name: "Footer",
    methods: {
        goToAboutPage() {
            this.$router.push({name: 'impressum'})
        }
    }
}
</script>

<style scoped>
#container {
    background-color: var(--elsa-blue-transparent);
    width: 100%;
    padding-top: 0;
    margin-left: 0;
    position: fixed;
    bottom: 0;
    font-size: 1.2em;
    border: solid 1px var(--dark-grey);
}

#text {
    color: var(--dark-grey);
}

#text:hover {
    text-decoration: underline var(--elsa-blue);
    color: var(--elsa-blue);
    cursor: pointer;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    span {
        font-size: 0.6em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    span {
        font-size: 0.7em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    span {
        font-size: 0.7em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    span {
        font-size: 0.7em;
    }
}

</style>
