<template>
    <b-container class="html-wrapper" v-html="htmlString"></b-container>
</template>

<script>
export default {
    name: "EmailTemplatePreview",
    props: {
        htmlString: String
    }
}
</script>

<style scoped>

.html-wrapper >>> p {
    margin-bottom: 0;
}

.html-wrapper {
    overflow:scroll;
    overflow-x:hidden;
    height:300px;
    width: auto
}

</style>
