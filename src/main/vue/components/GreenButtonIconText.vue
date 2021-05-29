<template>
    <b-button class="green-btn" id="my-btn">
        <b-icon id="my-icon" :icon="icon"></b-icon>
        <span id="my-txt"> {{ $t(text) }} </span>
    </b-button>
</template>

<script>
export default {
    name: "GreenButtonIconText",
    props: {
        icon: String,
        text: String
    }
}
</script>

<style scoped>
#my-icon {
    fill: var(--whitesmoke);
    position: relative;
}

.green-btn {
    padding: 0.25em 0.25em 0;
    border: 0.03em solid var(--dark-grey);
    width: fit-content;
}

.green-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}

#my-txt {
    margin-left: 0.5em;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) and (min-height: 370px) {
    .green-btn {
        font-size: 0.3em;
    }
}

@media (max-width: 575.98px) and (max-height: 369.98px) {
    .green-btn {
        font-size: 0.4em;
    }
}


@media (min-width: 576px) and (max-width: 767.98px) {
    .green-btn {
        font-size: 0.45em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .green-btn {
        font-size: 0.5em;
    }
}
</style>
