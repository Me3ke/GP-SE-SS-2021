<template>
<div>
    <div style="padding-bottom: 1em;">
        <!-- (Parent Component)
          DO NOT USE BOTH
            :env -> for envelopes progressbar
            :doc -> progress bar for each documents


           NEED (for now):
           - array of all Signatories
           - array of signatories who already signed
        --->

        <b-progress :max="max">
            <b-progress-bar
                :value="env == null && document !=='' ? docPercentage : envPercentage"
                :label=" env == null && document !=='' ? docPercentage + '%' : envPercentage + '%'" variant="success"
            ></b-progress-bar>

        </b-progress>

        <h4> DOC : {{getDocumentProgress}}</h4>




    </div>




</div>
</template>

<script>

export default {
    name: "ProgressBar",
    props: ['document', 'env', 'envID', 'docID'],



    /*
   {
    "signatories": [
        {
            "id": 1,
            "user": {
                "email": "hans.schneider@mail.de",
                "firstname": "Hans",
                "lastname": "Schneider",
                "publicKey": null,
                "personalData": {
                    "street": "Berliner Straße",
                    "houseNumber": 2,
                    "postCode": 12312,
                    "phoneNumber": "3213145",
                    "homeTown": "Liebefeld",
                    "country": "Deutschland",
                    "birthday": "2021-06-04"
                },
                "securitySettings": {
                    "id": 1,
                    "publicKey": null,
                    "secret": null
                },
                "adminValidated": true
            },
            "status": false,
            "signedOn": null,
            "signatureType": "REVIEW"
        }
    ],
    "alreadySigned": [],
    "readers": [
        {
            "id": 1,
            "user": {
                "email": "hans.schneider@mail.de",
                "firstname": "Hans",
                "lastname": "Schneider",
                "publicKey": null,
                "personalData": {
                    "street": "Berliner Straße",
                    "houseNumber": 2,
                    "postCode": 12312,
                    "phoneNumber": "3213145",
                    "homeTown": "Liebefeld",
                    "country": "Deutschland",
                    "birthday": "2021-06-04"
                },
                "securitySettings": {
                    "id": 1,
                    "publicKey": null,
                    "secret": null
                },
                "adminValidated": true
            },
            "status": false,
            "signedOn": null,
            "signatureType": "REVIEW"
        }
    ],
    "alreadyRead": [],
    "endDate": null
    }


     */
    data() {
        return {
            max: 100,

            response: {              // <---  the progress response of the specific document
                signatories: [],
                alreadySigned: [],

                readers: [],
                alreadyRead: [],
                endDate: null
            }

        }
    },
    computed: {

        envPercentage() {
            let array = [];
            (this.env.forEach(i => {
                array.push(((i.alreadySign.length / i.sign.length) * 100).toFixed(2))
            }))
            return ((array.reduce((a, b) => parseFloat(a) + parseFloat(b), 0) / (this.env.length * 100)) * 100)
                .toFixed(2)

        },

        // calculate the percentage of the document progress (signatories with reader)
        docPercentage() {
            return ((this.response.alreadySigned.length + this.response.alreadyRead.length )
                / (this.response.signatories.length + this.response.readers.length) * 100).toFixed(2)
        },

        getDocumentProgress() {
             return this.$store.getters.getDocumentProgress
         },

    },
    methods: {


    },

    created() {
        this.$store.dispatch('document/getDocumentProgress', {envId: this.envID, docId: this.docID})
    }
}
</script>

<style scoped>


</style>
