<template>
    <div>
        <base-heading></base-heading>
        <upload-new-version-button
            :document="documents[0]"
            :new-document="newUploadedFile"
            v-on:update-document="updateDoc"
        ></upload-new-version-button>

        <DocumentBox :doc="documents[0]"></DocumentBox>

        <p> Data: {{documents[0]}}</p>

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
            /// New File to get replaced
            /*
            TODO add the component for the document settings (title,state=open, endDate,
             signatureType, reader, signed=false, read)
            */
            newUploadedFile: {
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
                read: false,
            }
        }
    },

    computed: {
        ...mapGetters({
            documents: 'getDocuments'
        })
    },
    methods: {
        updateDoc() {
           // debugger; // eslint-disable-line no-debugger
            this.$store.dispatch('editDocument', this.newUploadedFile)
        }
    }
}
</script>

<style scoped>

</style>
