<template>
<div>
    <div
        style="padding-bottom: 1em;"
        :id="'popover-' + docId"
        @click="clicked"

    >
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


        <b-container style="max-width: 100%" v-if="isOpen" id="collapseExample" >

                <b-row>
                    <b-col>Need to Sign
                        <b-col>
                            <b-container v-for="(signatory,index) in needToSign" :key="index">
                                <b-col>{{signatory.user.email}}</b-col>
                            </b-container>
                        </b-col>

                        <b-col v-if="alreadySigned.length > 0" style="margin-top: 1em">
                            Already Signed
                            <b-col>
                                <b-container  v-for="(signatory,index) in alreadySigned" :key="index">
                                    <b-col>{{signatory.user.email}} {{alreadySigned.length}}</b-col>
                                </b-container>

                            </b-col>
                        </b-col>

                    </b-col>


                    <!---Reader if available --->
                    <b-col v-if="readers.length > 0">Need to Read
                    <b-col>
                        <b-container v-for="(signatory,index) in needToRead" :key="index">
                            <b-col>{{signatory.user.email}}</b-col>
                        </b-container>
                    </b-col>

                    <b-col v-if="alreadyRead.length > 0" style="margin-top: 1em">
                        Already Signed
                        <b-col>
                            <b-container  v-for="(signatory,index) in alreadyRead" :key="index">
                                <b-col>{{signatory.user.email}} {{alreadySigned.length}}</b-col>
                            </b-container>

                        </b-col>
                    </b-col>
                    </b-col>
                </b-row>

        </b-container>





        <!---TODO Arrow of Popover is not Whitesmoke --->
        <!--
        <b-popover placement="left" :target="'popover-' + docId" ref="popover" triggers= "focus" custom-class="my-popover-class">
            <b-container class="bv-example-row">
                <b-row>
                    <b-col>Signatory</b-col>
                    <b-col>Reader</b-col>
                </b-row>
                <b-row>
                    <b-col v-for="(signatory,index) in signatories" :key="index">{{signatory.user.email}}</b-col>
                    <b-col>2 of 2</b-col>
                </b-row>
                <b-row>
                    <b-col>1 of 2</b-col>
                    <b-col>2 of 2</b-col>
                </b-row>
            </b-container>
        </b-popover>

        --->
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
            isOpen: false
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

        needToRead() {
            return this.readers.filter(x => !this.alreadyRead.includes(x))
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

    methods: {
        clicked() {
            //this.$root.$emit('bv::hide::popover')
            //this.$refs.popover.$emit('open')
            console.log("HALLOOOOOOOOOOOO")
            this.isOpen = !this.isOpen
        }
    }


}
</script>

<style scoped>

.my-popover-class {
    background-color: var(--whitesmoke);
    width: 15em;
}



@media screen and (max-width: 1200px) {



}

</style>
