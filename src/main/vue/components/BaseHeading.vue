<template>
    <b-container fluid
                 style="margin-left: calc((2vh + 1vw) / 2); height: calc((2vh + 1vw) / 2);
                 margin-top: calc((2vh + 1vw) / 2); margin-bottom: calc((2vh + 1vw) / 1.5);">
        <b-row>
            <h2>
                <b-icon icon="arrow-left" style="fill: var(--dark-grey); font-weight: bolder;"
                        @click="historyThere()  ? $router.go(-1) : $router.push('/')">
                </b-icon>
                {{ $t(name) }}
            </h2>
        </b-row>
    </b-container>
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
</style>
