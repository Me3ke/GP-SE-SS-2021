<template>
<div>
    <div
        style="padding-bottom: 1em;"
        @click="clicked"
    >
        {{envelope === undefined? "undef" : 'def'}}
        {{getDocumentProgress === undefined ? 'doc progress undef': 'doc progress def'}}

        <b-progress :max="max">
                <b-progress-bar
                    :value="envelope !== undefined ? envPercentage : docPercentage"
                    :variant="state === 'CLOSED' ? 'darkgrey' : 'elsaBlue'"
                    :label="envelope !== undefined ? envPercentage + '%' : docPercentage + '%'"
                ></b-progress-bar>
            </b-progress>


        <b-container style="max-width: 100%" v-if="isOpen && state !== 'CLOSED' &&  envelope === undefined" id="collapseExample" >

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

        <b-container v-if="isOpen && envelope !== undefined">
            <b-row>
                <b-col> <span style="color: red" v-if="notFinishedEnvelope.length > 0">Not Finished</span>
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



        <!----


        -TODO Arrow of Popover is not Whitesmoke

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
    ---->

    </div>


</div>
</template>

<script>

export default {
    name: "ProgressBar",
    props: {
        getDocumentProgress: null,
        docId: null,
        state: null,
        envId: null,
        envelope: null,

        env: null
    },

    data() {
        return {
            max: 100,
            zero: 0,
            isOpen: false,
            stateClosed: false,
            envClosed: false,

            isDocument: this.docId !== undefined, // checks if prop is entered

        }
    },
    computed: {


        readers() {
            return this.getDocumentProgress.data.readers
        },
        signatories() {
            return this.getDocumentProgress.data.signatories
        },
        alreadySigned() {
            return this.getDocumentProgress.data.alreadySigned
        },
        alreadyRead() {
            return this.getDocumentProgress.data.alreadyRead
        },

        needToSign() {
            return this.compareArrays(this.signatories, this.alreadySigned)

        },

        needToRead() {
            return this.readers.filter(x => !this.alreadyRead.includes(x))
        },

        finishedEnvelope() {
            return this.calculateFinishedDocuments()
        },

        notFinishedEnvelope() {

            return this.calculateNotFinishedDocuments()
        },



        // optional ?
      envPercentage() {
            // TODO
          //let documentPercentages = []
          return 0
      },



        // calculate th percentage of the document progress (signatories with reader)
        docPercentage() {
            if(this.state === "CLOSED") {
                return 100.00
            } else {
                return ((this.alreadySigned.length + this.alreadyRead.length)
                    / (this.signatories.length + this.readers.length) * 100).toFixed(2)
            }
        },



    },

    methods: {
        clicked() {
            this.isOpen = !this.isOpen
        },

        checkFinishedDocument() {
            console.log(this.state)
            if(this.state === "CLOSED") {
                console.log("hallo")
                this.stateClosed = true;
            }
        },
        checkFinishedEnvelope() {
            let finished = []
            this.env.documents.forEach(document => {
                if(document.state === "CLOSED") {
                    finished.push(true)
                } else {
                    finished.push(false)
                }
            })

            this.envClosed = !finished.includes(false);
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
        },


        calculateFinishedDocuments() {
            let arr = []
            this.envelope.forEach(document => {
                if((((document.data.alreadySigned.length + document.data.alreadyRead.length)
                    / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) >= 100.00) {
                    const docTitle = this.findFinishedDocTitle(this.env,document)
                    arr.push(docTitle)
                    }

            })
            //this.checkFinishedEnvelope()
            return arr
        },

        calculateNotFinishedDocuments() {
            let arr = []
            this.envelope.forEach(document => {
                if((((document.data.alreadySigned.length + document.data.alreadyRead.length)
                    / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) < 100.00) {
                    const docTitle = this.findFinishedDocTitle(this.env,document)
                    arr.push(docTitle)
                }

            })
            //this.checkFinishedEnvelope()
            return arr
        },

        findFinishedDocTitle(envelope,doc) {
            console.log(envelope)
            for (let a = 0; a < envelope.documents.length; a++) {
                if (envelope.documents[a].id === doc.docId) {
                    return envelope.documents[a].title
                }
            }
        }
    },
    /*created() {
        this.$store.dispatch('document/resetState')
        console.log(this.documentProgressArray)

    },*/






}
</script>

<style scoped>

.bg-darkgrey {
    background-color: var(--light-grey) !important;
    color: var(--dark-grey);
}

.bg-elsaBlue {
    background-color: var(--elsa-blue) !important;
}



@media screen and (max-width: 1200px) {



}

</style>
