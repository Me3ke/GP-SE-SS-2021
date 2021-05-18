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
                                        <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                             v-if="doc.signatory === true && doc.signed === false && (doc.reader === false || doc.read === true)">
                                            <h6>
                                                <!-- Time until document needs to be signed TODO -->
                                                {{$t('OverviewPage.toSign1')}} {{this.doc.endDate}} {{$t('OverviewPage.toSign2')}}
                                            </h6>
                                        </div>
                                        <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                             v-if="doc.reader === true && doc.read === false && (doc.signatory === false || doc.signed === true)">
                                            <h6>
                                                <!-- Time until document needs to be signed TODO -->
                                                {{$t('OverviewPage.toRead1')}} {{this.doc.endDate}} {{$t('OverviewPage.toRead2')}}
                                            </h6>
                                        </div>
                                        <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                             v-if="doc.reader === true && doc.read === false && doc.signatory === true && doc.signed === false">
                                            <h6>
                                                <!-- Time until document needs to be signed TODO -->
                                                {{$t('OverviewPage.toSignRead1')}} {{this.doc.endDate}} {{$t('OverviewPage.toSignRead2')}}
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
    fill: var(--red);
    height: 2em;
    width: auto;
}

.card {
    background-color: var(--sign-doc)
}

.card:hover {
    background-color: var(--sign-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}
</style>
