<template>
    <div>
        <!--
          DO NOT USE BOTH
            :env -> for envelopes progressbar
            :doc -> progress bar for each documents
        --->
        <div style="padding-top: 5em ">
            <h2>Envelope:</h2>
            <ProgressBar style="padding-bottom: 2em" :env="documents"></ProgressBar>

        </div>

        <div v-for="(doc,i) in documents" :key="i" class="mt-3">
            <DocumentBox :doc="doc"></DocumentBox>
             <ProgressBar :document="doc"></ProgressBar >
        </div>


    </div>
</template>

<script>
import ProgressBar from "@/main/vue/components/ProgressBar";
import DocumentBox from "@/main/vue/components/DocumentBox";
export default {
    name: "ProgressbarTestPage",
    components: {DocumentBox, ProgressBar},
    data() {
        return {
            documents: [
                {
                    id: 1,
                    title: "Essensplan KW 25",
                    creationDate: "01.05.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    state: "open",
                    endDate: "15.06.2021",
                    dataType: "PDF",
                    signatureType: "simple",
                    signatory: true,

                    // need for the Progress bar the length of the signatories and
                    // also the length of the already signed signatories
                    sign: ["max", "alex", "muster"],
                    alreadySign: ["max", "alex", 'muster'],

                    reader: false,
                    signed: false,
                    read: false
                },
                {
                    id: 2,
                    title: "Essensplan KW 26",
                    creationDate: "01.05.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    state: "open",
                    endDate: "22.06.2021",
                    dataType: "PDF",
                    signatureType: "simple",
                    signatory: true,
                    sign: ["max", "alex", "muster"],
                    alreadySign: [],
                    reader: false,
                    signed: false,
                    read: false
                },
                {
                    id: 3,
                    title: "Essensplan KW 27",
                    creationDate: "01.05.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    state: "open",
                    endDate: "29.06.2021",
                    dataType: "PDF",
                    signatureType: "simple",
                    signatory: true,
                    sign: ["max", "alex", "muster"],
                    alreadySign: ["max", "alex"],
                    reader: false,
                    signed: false,
                    read: false
                },
                {
                    id: 4,
                    title: "Essensplan KW 28",
                    creationDate: "02.05.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
                    },
                    state: "open",
                    endDate: "04.07.2021",
                    dataType: "PDF",
                    signatureType: "simple",
                    signatory: true,
                    sign: ["max", "alex", "muster"],
                    alreadySign: ["max"],
                    reader: false,
                    signed: false,
                    read: false
                }
            ]
        }
    },
}
</script>

<style scoped>

</style>
