<template>
    <div>
        <b-container fluid class="card" style="padding:0.5vh">
            <div class="media">
                <b-icon icon="file-earmark-text" class="my-icon"></b-icon>
                <div class="media-body">
                    <b-container fluid style="overflow:hidden">
                        <b-row align-h="start">
                            <div class="col-auto">
                                <b-row align-h="between">
                                    <div class="col-auto">
                                        <h4>
                                            {{this.doc.title}}
                                        </h4>
                                    </div>
                                    <div class="col-auto">
                                        <div style="text-align: right; margin-left: 1vw">
                                            <h6>
                                                <!-- Time until document needs to be signed TODO -->
                                                {{$t('Document.closed')}}
                                            </h6>
                                        </div>
                                    </div>
                                </b-row>
                                <b-row align-h="start">
                                    <div class="col-auto">
                                        <h6>
                                            {{$t('Document.owner')}}: {{this.doc.owner.firstname}} {{this.doc.owner.lastname}}
                                        </h6>
                                    </div>
                                    <div class="col-auto" style="margin-left: 1vw">
                                        <h6>
                                            {{$t('Document.date')}}: {{this.doc.creationDate}}
                                        </h6>
                                    </div>
                                </b-row>
                            </div>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </b-container>
    </div>
</template>

<script>
export default {
    name: 'DocumentBox',
    props: {
        doc: {
            id: Object,
            title: String,
            creationDate: Date,
            owner: {
                id: Object,
                eMail: String,
                firstname: String,
                lastname: String
            },
            state: String,
            endDate: Date,
            dataType: String,
            signatureType: String,
            signatory: Boolean,
            reader: Boolean,
            signed: Boolean,
            read: Boolean
        }
    }
}
</script>

<style scoped>
.bi-file-earmark-text {
    margin: 0 0.7vw;
}

.my-icon {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
}

.card {
    background-color: var(--closed-doc);
    border-color: var(--dark-grey);
}

.card:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}
</style>
