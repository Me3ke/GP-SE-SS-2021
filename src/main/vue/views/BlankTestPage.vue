<template>
    <div>
        <base-heading></base-heading>
        <upload-new-version-button
            :document="actualFile"
            v-on:update-document="updateDoc"
        ></upload-new-version-button>
        <DocumentBox style="padding-top: 3em" :doc="actualFile"></DocumentBox>
    </div>
</template>

<script>

import UploadNewVersionButton from "@/main/vue/components/uploadNewVersionButton";
import DocumentBox from "@/main/vue/components/overviewPage/DocumentBox";
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
            doc: {},

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
                signatoriesId: [
                    {
                        eMail: "sehrTolle@email.com",
                        firstname: "Test1",
                        lastname: "Wehner"
                    },
                    {
                        eMail: "sehrTolle@email.com",
                        firstname: "Test2",
                        lastname: "Wehner"
                    },
                ],
                readers: [
                    {
                        eMail: "sehrTolle@email.com",
                        firstname: "Test2",
                        lastname: "Wehner"
                    }
                ],
                endDate: "15.06.2021",
                dataType: "PDF",
                signatureType: "simple",
                signatory: true,
                reader: false,
                signed: false,
                read: true,
            }
        }
    },

    computed: {
        ...mapGetters({
            document: 'document/getDocument'
        })
    },
    methods: {

        // todo implement the dispatch method to update the selected document
        updateDoc(newDoc) {
           // debugger; // eslint-disable-line no-debugger
           // this.$store.dispatch('editDocument', this.newUploadedFile)
            this.actualFile = newDoc
        }
    }
}
</script>

<style scoped>

</style>
