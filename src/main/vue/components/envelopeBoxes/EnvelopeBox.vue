<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">
            <b-icon icon="envelope" class="my-icon"></b-icon>
            <div class="media-body">
                <b-container fluid>
                    <b-row align-h="start">
                        <div class="col-auto">
                            <b-row align-h="between">
                                <div class="col-auto">
                                    <h4>
                                        {{this.env.name}}
                                    </h4>
                                </div>
                                <div class="col-auto">
                                    <div style="text-align: right; margin-left: 1vw">
                                        <h6>
                                            {{$t('Document.open')}}
                                        </h6>
                                    </div>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6>
                                        {{$t('Document.owner')}}: {{this.env.owner.firstname}} {{this.env.owner.lastname}}
                                    </h6>
                                </div>
                                <div class="col-auto">
                                    <h6>
                                        {{$t('Document.date')}}: {{this.env.creationDate}}
                                    </h6>
                                </div>
                            </b-row>
                        </div>
                    </b-row>
                </b-container>
            </div>
        </div>
    </b-container>
</template>

<script>
export default {
    name: 'EnvelopeBox',
    props: {
        env: {
            id: Object,
            name: String,
            owner: {
                id: Object,
                eMail: String,
                firstname: String,
                lastname: String
            },
            creationDate: Date,
            documents: [
                {
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
            ]
        }
    },
    data() {
        let i;
        for(i = 0; i < this.env.documents.length; i++) {
            if (this.env.documents[i].state === "open") {
                return {open: true}
            }
        }
        return {open: false};
    }
}
</script>

<style scoped>

.bi-envelope {
    margin: 0 0.7vw;
}

.my-icon {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
}

.card{
    background-color: var(--whitesmoke);
    border-color: var(--dark-grey);
}

.card:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
}
</style>
