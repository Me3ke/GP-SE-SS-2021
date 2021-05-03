<template>
    <div class="document">
        <div style="text-align: left;">
            <span>
                {{this.doc.name}}
            </span>
        </div>
        <div style="text-align: left; font-size: medium">
            <span>
                Besitzer: {{this.doc.owner.name}}
            </span>
            <span style="margin-left: 3vw">
                Erstellungsdatum: {{this.doc.dateCreated}}
            </span>
        </div>
    </div>
</template>

<script>
export default {
    name: 'DocumentBox',
    props: {
        doc:Object
    }
}
</script>

<style scoped>

</style>
