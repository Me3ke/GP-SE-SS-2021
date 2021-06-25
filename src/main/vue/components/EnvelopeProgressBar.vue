<template>
    <div>
        <div
            style="padding-bottom: 1em;"
            @click="clicked"
        >
            <b-progress max="100">
                <b-progress-bar
                    :value="percentage"
                    :variant="percentage >= 100.00 ? 'darkgrey' : 'elsaBlue'"
                    :label="percentage + '%'"
                ></b-progress-bar>
            </b-progress>



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
        </div>
    </div>
</template>

<script>
export default {
    name: "EnvelopeProgressBar",
    props: {
        envelope: null,
        env: null
    },

    data() {
        return {
            zero: 0,
            isOpen: false,
            envClosed: false,
            percentage: this.getPercentage(),

        }
    },
    computed: {


        finishedEnvelope() {
            return this.calculateFinishedDocuments()
        },

        notFinishedEnvelope() {

            return this.calculateNotFinishedDocuments()
        }
    },

    methods: {
        clicked() {
            this.isOpen = !this.isOpen
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

        calculateFinishedDocuments() {
            let arr = []
            this.envelope.forEach(document => {
                if((((document.data.alreadySigned.length + document.data.alreadyRead.length)
                    / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) >= 100.00) {
                    const docTitle = this.findFinishedDocTitle(this.env,document)
                    console.log('TITLE: ' ,docTitle)
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
            for (let a = 0; a < envelope.documents.length; a++) {
                if (envelope.documents[a].id === doc.docId) {
                    return envelope.documents[a].title
                }
            }
        },

        getPercentage() {
                let documentPercentages = []
                this.envelope.forEach(progress => {
                    documentPercentages.push((((progress.data.alreadySigned.length + progress.data.alreadyRead.length)
                        / (progress.data.signatories.length + progress.data.readers.length)) * 100).toFixed(2))
                })
                return ((documentPercentages.reduce((a, b) => parseFloat(a) + parseFloat(b), 0) / (this.env.documents.length * 100)) * 100)
                    .toFixed(2)
        }

    }
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

</style>
