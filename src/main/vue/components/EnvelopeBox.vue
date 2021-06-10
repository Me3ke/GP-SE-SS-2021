<template>
    <b-container fluid style="padding: 0">
        <div :class="{envelopeOpen: !toSign && !toRead && open, envelopeClosed: !open, envelopeSignRead: (toSign || toRead) && open}">
            <div class="media">
                <b-icon icon="envelope" :class="{iconRed: (toSign || toRead) && open, iconBlue: (!toSign && !toRead) || !open}" style="margin:0.5em"></b-icon>
                <div class="media-body">
                    <b-container fluid>
                        <b-row align-h="start">
                            <b-col style="width: 100%; margin: 0">
                                <b-row align-h="between">
                                    <b-col cols="auto">
                                        <h4>
                                            {{this.envelope.name}}
                                        </h4>
                                    </b-col>
                                    <b-col cols="auto">
                                        <div v-if="!open">
                                            <h6>
                                                {{$t('Document.closed')}}
                                            </h6>
                                        </div>
                                        <div v-if="open && !toSign && !toRead">
                                            <h6>
                                                {{$t('Document.open')}}
                                            </h6>
                                        </div>
                                        <div v-if="open" style="color: var(--red)">
                                            <div v-if="toSign && toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.envReadSign') }}
                                                </h6>
                                            </div>
                                            <div v-if="toSign && !toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.envSign') }}
                                                </h6>
                                            </div>
                                            <div v-if="!toSign && toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.envRead') }}
                                                </h6>
                                            </div>
                                        </div>
                                    </b-col>
                                </b-row>
                                <b-row align-h="start">
                                    <b-col cols="auto">
                                        <h6>
                                            {{$t('Document.owner')}}: {{this.envelope.owner.firstname}} {{this.envelope.owner.lastname}}
                                        </h6>
                                    </b-col>
                                    <b-col cols="auto">
                                        <h6>
                                            {{$t('Document.date')}}: {{this.envelope.creationDate}}
                                        </h6>
                                    </b-col>
                                </b-row>
                            </b-col>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </div>
    </b-container>
</template>

<script>
export default {
    name: "EnvelopeBox",
    props: {
        envelope: Object
    },
    data() {
        let open = false;
        let toSign = false;
        let toRead = false;
        let i;
        for(i = 0; i < this.envelope.documents.length; i++) {
            if (this.envelope.documents[i].state === "OPEN" || this.envelope.documents[i].state === "READ") {
                open = true;
            }
            if (this.envelope.documents[i].signatory === true && this.envelope.documents[i].signed === false) {
                toSign = true;
            }
            if (this.envelope.documents[i].reader === true && this.envelope.documents[i].read === false) {
                toRead = true;
            }
        }
        return {open: open, toSign: toSign, toRead: toRead}
    }
}
</script>

<style scoped>
.envelopeOpen {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.envelopeOpen:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}

.envelopeClosed {
    background-color: var(--closed-doc);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.envelopeClosed:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}

.envelopeSignRead {
    background-color: var(--sign-doc);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.envelopeSignRead:hover {
    background-color: var(--sign-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}

.iconRed {
    fill: var(--red);
    height: 2em;
    width: auto;
}

.iconBlue {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
}
</style>
