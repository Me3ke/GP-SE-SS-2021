<template>
    <div class="container">
        <BaseIcon name="arrow-left" color="var(--dark-grey)" width="30" height="30" stroke-width="3"
                  @click.native="historyThere()  ? $router.go(-1) : $router.push('/')"></BaseIcon>
        <h4 class="heading">{{ $t(name) }}</h4>
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
@import '~@/main/vue/assets/css/baseHeading.css';
</style>
