<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">
            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="var(--elsa-blue)" class="bi bi-envelope" viewBox="0 0 16 16">
                <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z"/>
            </svg>
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
                                    <div v-if="!this.open" style="text-align: right; margin-left: 1vw">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{$t('Document.closed')}}
                                        </h6>
                                    </div>
                                    <div v-if="this.open" style="text-align: right; margin-left: 1vw">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{$t('Document.open')}}
                                        </h6>
                                    </div>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6>
                                        {{$t('Document.owner')}}: {{this.env.owner.name}}
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


.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
