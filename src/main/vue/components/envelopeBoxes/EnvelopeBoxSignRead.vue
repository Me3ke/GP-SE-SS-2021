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
                                        {{ this.env.name }}
                                    </h4>
                                </div>
                                <div class="col-auto">
                                    <!-- Documents to sign and read -->
                                    <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                         v-if="signatory && reader">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{ $t('OverviewPage.envReadSign') }}
                                        </h6>
                                    </div>

                                    <!-- Documents to sign -->
                                    <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                         v-if="signatory && !reader">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{ $t('OverviewPage.envSign') }}
                                        </h6>
                                    </div>

                                    <!-- Documents to read -->
                                    <div style="text-align: right; margin-left: 1vw; color: var(--red)"
                                         v-if="reader && !signatory">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{ $t('OverviewPage.envRead') }}
                                        </h6>
                                    </div>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6>
                                        {{ $t('Document.owner') }}: {{ this.env.owner.firstname }}
                                        {{ this.env.owner.lastname }}
                                    </h6>
                                </div>
                                <div class="col-auto">
                                    <h6>
                                        {{ $t('Document.date') }}: {{ this.env.creationDate }}
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
        let open = false;
        let toSign = false;
        let toRead = false;
        let i;
        for (i = 0; i < this.env.documents.length; i++) {
            if (this.env.documents[i].state === "open") {
                open = true;
            }
            if (this.env.documents[i].signatory === true && this.env.documents[i].signed === false) {
                toSign = true
            }
            if (this.env.documents[i].reader === true && this.env.documents[i].read === false) {
                toRead = true
            }
        }
        return {open: open, signatory: toSign, reader: toRead};
    }
}
</script>

<style scoped>

.bi-envelope {
    margin: 0 0.7vw;
}

.my-icon {
    fill: var(--red);
    height: 2em;
    width: auto;
}

.card {
    background-color: var(--sign-doc);
    border-color: var(--dark-grey);
}

.card:hover {
    background-color: var(--sign-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}
</style>
