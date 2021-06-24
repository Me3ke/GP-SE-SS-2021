<template>
<div v-if="!stateClosed">
    <div
        style="padding-bottom: 1em;"
        :id="'popover-' + docId"
        @click="clicked"
    >
            <b-progress :max="max" :id="`popover-1-${docId}`">
                <b-progress-bar
                    :value="typeof env === 'undefined' ? docPercentage : envPercentage"
                    variant="success"
                    :label="typeof env === 'undefined' ? docPercentage + '%' : envPercentage"
                ></b-progress-bar>
            </b-progress>


        <b-container style="max-width: 100%" v-if="isOpen && state !== 'CLOSED' && typeof this.env === 'undefined'" id="collapseExample" >

                <b-row>
                    <b-col v-if="needToSign.length > 0">
                        <span style="color: red">Need to Sign</span>
                        <b-col>
                            <b-container v-for="(signatory,index) in needToSign" :key="index">
                                <b-col>{{signatory.user.email}}</b-col>
                            </b-container>
                        </b-col>

                        <b-col v-if="alreadySigned.length > 0" style="margin-top: 1em">
                            <span style="color: green">Already Signed</span>
                            <b-col>
                                <b-container  v-for="(signatory,index) in alreadySigned" :key="index">
                                    <b-col>{{signatory.user.email}}</b-col>
                                </b-container>

                            </b-col>
                        </b-col>

                    </b-col>


                    <!---Reader if available --->
                    <b-col v-if="readers.length > 0">
                        <span style="color: red">Need to Read</span>
                    <b-col>
                        <b-container v-for="(signatory,index) in needToRead" :key="index">
                            <b-col>{{signatory.user.email}}</b-col>
                        </b-container>
                    </b-col>

                    <b-col v-if="alreadyRead.length > 0" style="margin-top: 1em">
                        <span style="color: green">Already Signed</span>
                        <b-col>
                            <b-container  v-for="(signatory,index) in alreadyRead" :key="index">
                                <b-col>{{signatory.user.email}}</b-col>
                            </b-container>

                        </b-col>
                    </b-col>
                    </b-col>
                </b-row>

        </b-container>

        <b-container v-if="isOpen && env">
            <b-row>
                <b-col> <span style="color: red">Not Finished</span>
                    <b-col>
                        <b-container v-for="(documentTitle, index) in notFinishedEnvelope" :key="index">
                            <b-col>
                                {{ documentTitle }}
                            </b-col>
                        </b-container>
                    </b-col>

                    <b-col style="margin-top: 1em" v-if="finishedEnvelope.length > 0">
                        <span style="color: green">Finished</span>

                        <b-col>
                            <b-container v-for="(documentTitle, index) in finishedEnvelope" :key="index">
                                <b-col>
                                    {{ documentTitle }}
                                </b-col>
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
    props: ['documentProgress', 'docId', 'state', 'env', 'envelope'],

    data() {
        return {
            max: 100,
            zero: 0,
            isOpen: false,
            stateClosed: false
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
            return this.compareArrays(this.signatories, this.alreadySigned)

        },

        needToRead() {
            return this.readers.filter(x => !this.alreadyRead.includes(x))
        },

        finishedEnvelope() {
           const finishedDocuments = []

            this.env.forEach(document => {
                if((((document.data.alreadySigned.length + document.data.alreadyRead.length) / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) >= 100.00) {
                    for (let a = 0; a < this.envelope.documents.length; a++) {
                        if (this.envelope.documents[a].id === document.docId) {
                            finishedDocuments.push(this.envelope.documents[a].title)
                        }
                    }
                }
            })
            return finishedDocuments
        },

        notFinishedEnvelope() {
            const notFinished = []
            this.env.forEach(document => {
                if((((document.data.alreadySigned.length + document.data.alreadyRead.length) / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) < 100.00) {
                    for (let a = 0; a < this.envelope.documents.length; a++) {
                        if(this.envelope.documents[a].id === document.docId) {
                            notFinished.push(this.envelope.documents[a].title)

                        }

                    }
                }
            })
            return notFinished
        },



        // optional ?
        envPercentage() {
            let array = [];

            (this.env.forEach(i => {
                array.push((((i.data.alreadySigned.length + i.data.alreadyRead.length) / (i.data.signatories.length + i.data.readers.length)) * 100).toFixed(2))
            }))
            let percentage = ((array.reduce((a, b) => parseFloat(a) + parseFloat(b), 0) / (this.env.length * 100)) * 100)
                .toFixed(2)

            this.checkFinished(percentage)


            return percentage !== 'NaN' && percentage !== '0.00'  ? percentage + '%' : ''

        },

        // calculate th percentage of the document progress (signatories with reader)
        docPercentage() {
            this.checkFinished(((this.alreadySigned.length + this.alreadyRead.length )
                / (this.signatories.length + this.readers.length) * 100).toFixed(2))
            return((this.alreadySigned.length + this.alreadyRead.length )
                / (this.signatories.length + this.readers.length) * 100).toFixed(2)
        },

    },

    methods: {
        clicked() {
            this.isOpen = !this.isOpen
        },

        checkFinished(value) {
            this.stateClosed = value >= 100.00;
        },


        // returning the array with only signatories who needs to sign
        compareArrays(arr1, arr2) {
            if(arr2.length === 0) {
                return arr1
            } else {

                return arr1.filter((element) => {
                    return arr2.some((x) => {
                        return x.user.email !== element.user.email;
                    });
                });
            }
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
