<template>
<div v-if="getDocumentProgress !== undefined">
    <div
        style="padding-bottom: 1em;"
        @click="clicked"
    >

        {{getDocumentProgress !== undefined ? 'document progress defined' : 'document progress undefined'}}
            <b-progress max="100">
                <b-progress-bar
                    :value="docPercentage"
                    :variant="state === 'CLOSED' ? 'darkgrey' : 'elsaBlue'"
                    :label="docPercentage + '%'"
                ></b-progress-bar>
            </b-progress>



            <!--- Document Progress ---->
            <b-container style="max-width: 100%" v-if="isOpen && state !== 'CLOSED'
            && getDocumentProgress !== undefined" id="collapseExample" >
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
    },

    data() {
        return {
            zero: 0,
            isOpen: false,
            stateClosed: false,
            //percentage: this.getPercentage(),


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




        // calculate th percentage of the document progress (signatories with reader)
        docPercentage() {
            if(this.state === "CLOSED") {
                return 100.00
            } else {
                return ((this.getDocumentProgress.data.alreadySigned.length
                    + this.getDocumentProgress.data.alreadyRead.length)
                    / (this.getDocumentProgress.data.signatories.length
                        + this.getDocumentProgress.data.readers.length) * 100).toFixed(2)
            }
        },



    },

    methods: {
        clicked() {
            this.isOpen = !this.isOpen
        },

        checkFinishedDocument() {
            if(this.state === "CLOSED") {
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


        getPercentage() {
            /*if(this.state === 'CLOSED') {
                return 100.00
            } else {*/
                return ((this.getDocumentProgress.data.alreadySigned.length + this.getDocumentProgress.data.alreadyRead.length)
                    / (this.getDocumentProgress.data.signatories.length
                        + this.getDocumentProgress.data.readers.length) * 100).toFixed(2)
            }
        }
    //}
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
