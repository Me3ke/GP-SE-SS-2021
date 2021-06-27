<template>
    <span class="rounded-circle name-bubble"> {{ abbreviation }} </span>
</template>

<script>
export default {
    name: "NameBubble",
    props: {
        name: String
    },
    computed: {
        abbreviation() {
            const first = this.name.split(' ')[0][0];
            const second = this.name.split(' ')[1][0];
            return first + second
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
