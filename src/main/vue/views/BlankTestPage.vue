<template>
    <div>
        <base-heading></base-heading>
        <upload-new-version-button
            :document="actualFile"
            :new-document="newUploadedFile"
            v-on:update-document="updateDoc"
        ></upload-new-version-button>
        <DocumentBox :doc="actualFile"></DocumentBox>
    </div>
</template>

<script>

import UploadNewVersionButton from "@/main/vue/components/uploadNewVersionButton";
import DocumentBox from "@/main/vue/components/documentBoxes/DocumentBox";
import {mapGetters} from "vuex";

export default {
    name: "BlankTestPage",
    components: {DocumentBox, UploadNewVersionButton},
    data() {
        return {
            /*
            TODO add the component for the document settings

            Upload File will also be an axios call
            (
             title,
             state=open  ,
             endDate // changeable,
             signatureType // changeable,
              reader,
              signed=false // will be reseted,
              read // optionally be changed but can also stay on true
              )
            */

            actualFile: {
                id: 1,
                title: "Essenplan KW 25",
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
                reader: false,
                signed: false,
                read: true,
            },


            newUploadedFile: {
                id: 1,
                title: "Essenplan KW 25 #2",
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
                reader: false,
                signed: true,
                read: true,
            }
        }
    },

    computed: {
        ...mapGetters({
            document: 'getDocument'
        })
    },
    methods: {
        updateDoc() {
           // debugger; // eslint-disable-line no-debugger
           // this.$store.dispatch('editDocument', this.newUploadedFile)
            this.actualFile = this.newUploadedFile

        }
    }
}
</script>

<style scoped>

</style>
