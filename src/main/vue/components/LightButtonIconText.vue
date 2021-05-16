<template>
    <b-button class="light-btn" id="my-btn">
        <b-icon id="my-icon" :icon="icon"></b-icon>
        <span id="my-txt"> {{ $t(text) }} </span>
    </b-button>
</template>

<script>
export default {
    name: "LightButtonIconText",
    props: {
        icon: String,
        text: String
    }
}
</script>

<style scoped>
#my-icon {
    fill: var(--dark-grey);
    position: relative;
}

#my-btn {
    padding: 0.6em;
    text-align: center;
    vertical-align: center;
    width: fit-content;
    font-size: 0.75em;
}

#my-txt {
    margin-left: 0.5em;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) and (min-height: 370px) {
    #my-btn {
        font-size: 0.3em;
    }
}

@media (max-width: 575.98px) and (max-height: 369.98px) {
    #my-btn {
        font-size: 0.4em;
    }
}


@media (min-width: 576px) and (max-width: 767.98px) {
    #my-btn {
        font-size: 0.45em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    #my-btn {
        font-size: 0.5em;
    }
}
</style>
