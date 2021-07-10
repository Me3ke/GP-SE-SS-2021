<template>
    <span v-if="showBubble" class="rounded-circle name-bubble"> {{ abbreviation }} </span>
</template>

<script>
export default {
    name: "NameBubble",
    props: {
        name: String
    },
    data() {
        return {
            showBubble: true
        }
    },
    beforeMount() {
        this.show()
    },
    methods: {
        show() {
            const first = this.name.split(' ')[0][0];
            const second = this.name.split(' ')[1][0];
            if (first === undefined && second === undefined) {
                this.showBubble = false
            }
        }
    },
    computed: {
        abbreviation() {
            const first = this.name.split(' ')[0][0];
            const second = this.name.split(' ')[1][0];
            if (second === undefined) {
                return first + ' '
            } else {
                return first + second
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
