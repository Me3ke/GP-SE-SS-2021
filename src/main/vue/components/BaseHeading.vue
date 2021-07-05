<template>
    <div>
        <b-container fluid id="heading">
            <b-row>
                <h3>
                    <b-icon icon="arrow-left" style="fill: var(--dark-grey); font-weight: bolder; margin-right: 0.25em"
                            @click="historyThere()  ? $router.go(-1) : $router.push('/')">
                    </b-icon>
                    <span v-if="translate">{{ $t(name) }}</span>
                    <span v-else>{{ name }}</span>
                </h3>
            </b-row>
        </b-container>
    </div>
</template>

<script>
export default {
    name: "BaseHeading",
    props: {
        name: {
            type: String,
            default: "Insert Heading"
        },
        translate: {
            type: Boolean,
            default: true
        }
    },
    methods: {
        historyThere() {
            return window.history.length > 2
        }
    }
}
</script>

<style scoped>
h3 {
    margin-left: 0.75em;
    height: fit-content;
    margin-bottom: 1em;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {

    h3 {
        font-size: 1em;
        margin-top: 3em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {

    h3 {
        font-size: 1.1em;
        margin-top: 3em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {

    h3 {
        font-size: 1.15em;
        margin-top: 3em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h3 {
        margin-top: 3em;
    }
}

@media (min-width: 992px) {
    h3 {
        margin-top: 3em;
    }
}

</style>
