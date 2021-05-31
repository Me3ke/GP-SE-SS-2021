<template>
<div>
    <div style="padding-bottom: 2em">
        <!-- (Parent Component)
          DO NOT USE BOTH
            :env -> for envelopes progressbar
            :doc -> progress bar for each documents


           NEED (for now):
           - array of all Signatories
           - array of signatories who already signed
        --->


        <b-progress :max="max">
            <b-progress-bar
                :value="env == null && document !=='' ? docPercentage : envPercentage"
                :label=" env == null && document !=='' ? docPercentage + '%' : envPercentage + '%'" variant="success"
            ></b-progress-bar>

        </b-progress>


    </div>




</div>
</template>

<script>

export default {
    name: "ProgressBar",
    props: ['document', 'env'],
    data() {
        return {
            max: 100,
        }
    },
    computed: {

        envPercentage() {
            let array = [];
            (this.env.forEach(i => {
                array.push(((i.alreadySign.length / i.sign.length) * 100).toFixed(2))
            }))
            return ((array.reduce((a, b) => parseFloat(a) + parseFloat(b), 0) / (this.env.length * 100)) * 100)
                .toFixed(2)

        },
        docPercentage() {
            return((this.document.alreadySign.length / this.document.sign.length) * 100).toFixed(2)
        }

    },
}
</script>

<style scoped>


</style>
