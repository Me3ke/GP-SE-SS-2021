<template>
    <b-list-group-item style="height: 2.5em; padding: 0.5em" :class="{signed: signed, toSign: !signed}">
        <b-container fluid>
            <b-row align-h="between" no-gutters >
                <b-col cols="auto" >
                    {{ this.signatory.email }}
                </b-col>
                <b-col cols="auto" v-if="this.signatory.status">
                    bereits signiert [EN/DE!!]
                </b-col>
                <b-col cols="auto" v-if="!this.signatory.status & this.signatory.signatureType === 1">
                    {{$t('UploadDoc.signatureType')}}: {{$t('UploadDoc.simple')}}
                </b-col>
                <b-col cols="auto" v-if="!this.signatory.status & this.signatory.signatureType === 2">
                    {{$t('UploadDoc.signatureType')}}: {{$t('UploadDoc.advanced')}}
                </b-col>
            </b-row>
        </b-container>
    </b-list-group-item>
</template>

<script>
export default {
    name: "SignatoryListItem",
    props: {
        signatory: Object
    },
    data() {
        return{signed: this.signatory.status}
    }
}
</script>

<style scoped>
.signed {
    background-color: var(--closed-doc);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
}

.toSign {
}
</style>
