<template>
    <div>
        <b-container fluid id="heading">
            <b-row>
                <h3>
                    <b-icon icon="arrow-left" style="fill: var(--dark-grey); font-weight: bolder;"
                            @click="historyThere()  ? $router.go(-1) : $router.push('/')">
                    </b-icon>
                    {{ $t(name) }}
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
#heading {
    margin-left: calc((2vh + 1vw) / 2);
    height: calc((2vh + 1vw) / 2);
    margin-top: calc((2vh + 1vw) / 2);
    margin-bottom: calc((2vh + 1vw) / 1.5);
}

/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h3 {
        font-size: 1.1em;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    h3 {
        font-size: 1.1em;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h3 {
        font-size: 1.1em;
    }
}
</style>
