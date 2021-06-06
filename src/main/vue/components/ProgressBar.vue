<template>
<div>
    <div
        style="padding-bottom: 1em;">
        <!-- (Parent Component)
            :documentProgress -> progress bar for each documents // needs the documentProgressArray (state) with the id -1 because of the iD
        --->


        <b-progress :max="max">
            <b-progress-bar
                :value="docPercentage"
                variant="success"
                :label="docPercentage + '%'"
            ></b-progress-bar>

        </b-progress>
    </div>




</div>
</template>

<script>



export default {
    name: "ProgressBar",
    props: ['documentProgress'],

    data() {
        return {
            max: 100,
            zero: 0,
        }
    },
    computed: {

        readers() {
            return this.documentProgress.readers
        },
        signatories() {
            return this.documentProgress.signatories
        },
        alreadySigned() {
            return this.documentProgress.alreadySigned
        },
        alreadyRead() {
            return this.documentProgress.alreadyRead
        },
        // optional ?
        envPercentage() {
            let array = [];

            (this.env.forEach(i => {
                array.push(((i.alreadySign.length / i.sign.length) * 100).toFixed(2))
            }))
            return ((array.reduce((a, b) => parseFloat(a) + parseFloat(b), 0) / (this.env.length * 100)) * 100)
                .toFixed(2)

        },

        // calculate th percentage of the document progress (signatories with reader)
        docPercentage() {
            return ((this.alreadySigned.length + this.alreadyRead.length )
                / (this.signatories.length + this.readers.length) * 100).toFixed(2)

        },

    },


}
</script>

<style scoped>


</style>
