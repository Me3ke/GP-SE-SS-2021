<template>
<div v-if="getDocumentProgress !== undefined && docPercentage < 100.00">
    <div
        style="padding-bottom: 1em;"
        @click="clicked"
    >

            <b-progress max="100" style="background-color: var(--progress-background); cursor: pointer">
                <b-progress-bar
                    :value="docPercentage"
                    :variant="state === 'CLOSED' ? 'darkgrey' : 'elsaBlue'"
                    :label="docPercentage + '%'"
                ></b-progress-bar>
            </b-progress>



            <!--- Document Reader Progress ---->
            <b-container style="max-width: 100%; margin-top: 1px ; border: .03vw solid var(--dark-grey); border-radius: .33vw" v-if="isOpen && state !== 'CLOSED'
            && getDocumentProgress !== undefined" id="collapseExample" >
                    <b-row>
                        <b-col style="margin-top: .75em; margin-bottom: .4em" v-if="readers.length > 0" >
                            <b-col style="padding-bottom: .4em" v-if="readers.length !== alreadyRead.length ">
                                <span style="color: var(--not-done)" v-if="needToRead.length > 0">{{ $t('ProgressBar.document.needToRead') }}</span>

                                <b-container v-for="(signatory,index) in needToRead" :key="index">
                                    <b-col>{{signatory.email}}</b-col>
                                </b-container>
                            </b-col>

                            <b-col v-if="alreadyRead.length > 0">
                                <span style="color: var(--done)">{{ $t('ProgressBar.document.alreadyRead') }}</span>
                                <b-col>
                                    <b-container  v-for="(signatory,index) in alreadyRead" :key="index">
                                        <b-col>{{signatory.email}}</b-col>
                                    </b-container>

                                </b-col>
                            </b-col>
                        </b-col>




                        <!--- Document Signature Progress ---->

                        <b-col
                            v-if="signatories.length > 0"
                            style="margin-top: .75em; margin-bottom: .4em">
                            <b-col style="padding-bottom: .4em">
                                <span style="color: var(--not-done)" v-if="needToSign.length > 0">{{ $t('ProgressBar.document.needToSign') }}</span>
                                <b-container v-for="(signatory,index) in needToSign" :key="index">
                                    <b-col>{{signatory.email}}</b-col>
                                </b-container>
                            </b-col>

                            <b-col v-if="alreadySigned.length > 0">
                                <b-col>
                                    <span style="color: var(--done)">{{ $t('ProgressBar.document.alreadySigned') }}</span>
                                    <b-container  v-for="(signatory,index) in alreadySigned" :key="index">
                                        <b-col>{{signatory.email}}</b-col>
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
    name: "DocumentProgressBar",
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
            return this.compareArrays(this.readers, this.alreadyRead)
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

        // returning the array with only signatories who needs to sign
        compareArrays(arr1, arr2) {
            if(arr2.length === 0) {
                return arr1
            } else {

                return arr1.filter((element) => {
                    return arr2.some((x) => {
                        console.log(x.email !== element.email)
                        return x.email !== element.email;
                    });
                });
            }
        },


        getPercentage() {
                return ((this.getDocumentProgress.data.alreadySigned.length + this.getDocumentProgress.data.alreadyRead.length)
                    / (this.getDocumentProgress.data.signatories.length
                        + this.getDocumentProgress.data.readers.length) * 100).toFixed(2)
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
