<template>
    <div>
        <div
            style="padding-bottom: 1em;"
            @click="clicked"
        >
            <b-progress max="100" style="background-color: var(--progress-background); cursor: pointer">
                <b-progress-bar
                    :value="percentage"
                    :variant="percentage >= 100.00 ? 'darkgrey' : 'elsaBlue'"
                    :label="percentage + '%'"
                ></b-progress-bar>
            </b-progress>



            <b-container
                v-if="isOpen && envelope !== undefined"
                style="max-width: 100%; margin-top: 1px ; border: .03vw solid var(--dark-grey); border-radius: .33vw"
            >
                <b-row>
                    <b-col v-if="notFinishedEnvelope.length > 0" style="margin-top: .75em; margin-bottom: .4em">
                        <span style="color: var(--not-done)">{{ $t('ProgressBar.envelope.notFinished') }}</span>
                        <b-col>
                            <b-container v-for="(documentTitle, index) in notFinishedEnvelope" :key="index">
                                <b-col>
                                    {{ documentTitle }}
                                </b-col>
                            </b-container>
                        </b-col>

                        <b-col style="margin-top: 1em; margin-bottom: .75em" v-if="finishedEnvelope.length > 0">
                            <span style="color: var(--done)">{{ $t('ProgressBar.envelope.finished') }}</span>

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

        calculateFinishedDocuments() {
            let arr = []
            this.envelope.forEach(document => {

                if((((document.data.alreadySigned.length + document.data.alreadyRead.length)
                    / (document.data.signatories.length + document.data.readers.length))* 100).toFixed(2) >= 100.00) {
                    const docTitle = this.findFinishedDocTitle(this.env,document)
                    arr.push(docTitle)
                }

            })
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
    background-color: var(--closed-doc) !important;
    color: var(--whitesmoke);
}

.bg-elsaBlue {
    background-color: var(--elsa-blue) !important;
    color: var(--whitesmoke);

}

</style>
