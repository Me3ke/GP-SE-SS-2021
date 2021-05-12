<template>
    <b-button class="elsa-blue-btn" style="padding: 0.5vh 1vw 0">
        <h4>
        <b-icon icon="plus-circle" class="my-icon"></b-icon>
            {{this.text}}
        </h4>
    </b-button>
</template>

<script>
export default {
    name: 'UploadButton',
    props: {
        text: String
    }
}
</script>

<style scoped>
</style>
