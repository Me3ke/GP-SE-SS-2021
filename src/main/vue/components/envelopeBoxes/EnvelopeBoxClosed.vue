<template>
    <b-container fluid >
        <b-row>
            <b-col cols="11">
                <b-container fluid class="card" style="padding:0.5vh; margin:0">
                    <div class="media" style="padding-right: 0; margin: 0" @click="$router.push({name: 'envelope', params: {envId: env.id}})">
                        <b-icon icon="envelope" class="my-icon"></b-icon>
                        <b-col class="media-body" style="padding-right: 0; margin-right: 0">
                            <b-container fluid>
                                <b-row align-h="start">
                                    <b-col class="col-auto" style="width: 100%">
                                        <b-row align-h="between">
                                            <div class="col-auto">
                                                <h4>
                                                    {{this.env.name}}
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
                                        <b-row align-h="between">
                                            <div class="col-auto">
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
                                    </b-col>
                                </b-row>
                            </b-container>
                        </b-col>
                    </div>
                </b-container>
            </b-col>

            <b-col cols="1">
                <settings-button @click.native="$router.push({name: 'settings', params: {envId: env.id}})"></settings-button>
            </b-col>
        </b-row>

    </b-container>

</template>

<script>
import settingsButton from "@/main/vue/components/envSettingsButton";

export default {
    name: 'EnvelopeBox',
    components: {settingsButton},
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
