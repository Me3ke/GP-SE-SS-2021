<template>
    <b-button class="light-btn" id="my-btn">
        <b-icon id="my-icon" :icon="icon"></b-icon>
    </b-button>
</template>


<script>
export default {
    name: "PDFViewerButton",
    props: {
        icon: String
    }
}
</script>

<style scoped>
#my-icon {
    fill: var(--dark-grey);
    height: 2em;
    width: auto;
    padding: 0.3em;
    align-self: center;
    font-size: 0.85em;
}

#my-btn {
    padding: 0;
    height: 2em;
    width: 2em;
    text-align: center;
    vertical-align: center;
    box-shadow: none;
    font-size: 0.85em;
}


</style>
