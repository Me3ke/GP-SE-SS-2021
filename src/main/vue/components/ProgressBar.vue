<template>
<div>
    <div
        style="padding-bottom: 1em;">
        <!-- (Parent Component)
            :documentProgress -> progress bar for each documents // needs the documentProgressArray (state) with the id -1 because of the iD
        --->


        <b-progress :max="max" :id="`popover-1-${docId}`">
            <b-progress-bar
                :value="docPercentage"
                variant="success"
                :label="docPercentage + '%'"
            ></b-progress-bar>
        </b-progress>

        <b-popover
            class="popover-body"
            :target="`popover-1-${docId}`"
            triggers="hover focus"
            placement="bottomright"


        >
            <div>

                <h5 style="margin-bottom: 0" class="popover-content-missing" >Müssen noch Signieren:</h5>

                <div style="padding-bottom: -1em" class="d-flex flex-column" v-for="(user,index) in needToSign" :key="index">
                    <div>{{user.user.firstname}} {{user.user.lastname}}</div>

                </div>

                <hr>

                <h5 class="popover-content-notMissing" >Schon unterschrieben:</h5>

                 <div class="d-flex flex-column" v-for="(user,index) in alreadySigned" :key="index">
                    <div class="p-2">{{user.user.firstname}} {{user.user.lastname}}</div>

                 </div>

            </div>

        </b-popover>
    </div>




</div>
</template>

<script>



export default {
    name: "ProgressBar",
    props: ['documentProgress', 'docId'],

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

        needToSign() {
            console.log(this.signatories.filter(x => !this.alreadySigned.includes(x)))
            return this.signatories.filter(x => !this.alreadySigned.includes(x))
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

.popover {
    max-width: 100%;
}
.popover-content-missing {
    font-size: 1em;
    color: red;
}
.popover-content-notMissing {
    font-size: 1em;
    color: green;
}

.popover-body {
    background-color: var(--whitesmoke);
}

@media screen and (max-width: 1200px) {
    .popover {max-width: 70%}

    .popover-content-missing {
        font-size: 1em;
        color: red;
    }
    .popover-content-notMissing {
        font-size: 1em;
        color: green;
    }
}

</style>
