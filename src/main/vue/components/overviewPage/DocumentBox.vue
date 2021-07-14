<template>
    <b-container fluid style="padding: 0">
        <div
            :class="{envelopeOpen: (!toSign && !toRead && open) && !draft, envelopeClosed: !open, envelopeSignRead: ((toSign || toRead) && open) && !draft, envelopeDraft: open && draft}">
            <div class="media">
                <b-icon icon="file-earmark-text"
                        :class="{iconRed: (toSign || toRead) && open && !draft, iconBlue: (!toSign && !toRead && !draft) || !open, icon: draft && open}"
                        style="margin:0.5em"></b-icon>
                <div class="media-body">
                    <b-container fluid>
                        <b-row align-h="start">
                            <b-col style="width: 100%; margin: 0">
                                <b-row align-h="between">
                                    <b-col cols="auto">
                                        <h4>
                                            {{ this.document.title }}
                                        </h4>
                                    </b-col>
                                    <b-col cols="auto">
                                        <div v-if="!open">
                                            <h6>
                                                {{ $t('Document.closed') }}
                                            </h6>
                                        </div>
                                        <div v-if="open && !toSign && !toRead && !draft">
                                            <h6>
                                                {{ $t('Document.open') }}
                                            </h6>
                                        </div>
                                        <div v-if="open && !draft" style="color: var(--red)">
                                            <div v-if="toSign && toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.toSignRead1') }} {{ this.document.endDate }}
                                                    {{ $t('OverviewPage.toSignRead2') }}
                                                </h6>
                                            </div>
                                            <div v-if="toSign && !toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.toSign1') }} {{ this.document.endDate }}
                                                    {{ $t('OverviewPage.toSign2') }}
                                                </h6>
                                            </div>
                                            <div v-if="!toSign && toRead">
                                                <h6>
                                                    {{ $t('OverviewPage.toRead1') }} {{ this.document.endDate }}
                                                    {{ $t('OverviewPage.toRead2') }}
                                                </h6>
                                            </div>
                                        </div>
                                        <div v-if="open && draft">
                                            {{ $t('Document.draft') }}
                                        </div>
                                    </b-col>
                                </b-row>
                                <b-row align-h="start">
                                    <b-col cols="auto">
                                        <h6>
                                            {{ $t('Document.owner') }}: {{ this.document.owner.firstname }}
                                            {{ this.document.owner.lastname }}
                                        </h6>
                                    </b-col>
                                    <b-col cols="auto">
                                        <h6>
                                            {{ $t('Document.date') }}: {{ this.document.creationDate }}
                                        </h6>
                                    </b-col>
                                    <b-col cols="auto">
                                        <h6>
                                            {{ $t('Document.envelope') }}: {{ this.envelope(this.envelopeId).name }}
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
import {mapGetters} from "vuex";

export default {
    name: "DocumentBox",
    props: {
        envelopeId: [Number, String],
        document: Object
    },
    data() {
        let open = false;
        let toSign = false;
        let toRead = false;
        if(this.document.state === "REVIEW" || this.document.state === "SIGN") {
            open = true;
        }
        if(this.document.turnToSign && this.document.signatory && !this.document.signed) {
            toSign = true;
        }
        if(this.document.turnToReview && this.document.reader && !this.document.read) {
            toRead = true;
        }
        return {open: open, toSign: toSign, toRead: toRead, draft: this.document.draft};
    },
    async mounted() {
        await this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope'
        })
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

.envelopeDraft {
    background-color: var(--draft-doc);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.envelopeDraft:hover {
    background-color: var(--draft-doc-hover);
    border: 0.03vw solid var(--dark-grey);
    color: var(--dark-grey);
    border-radius: 0.33vw;
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

.icon {
    height: 2em;
    width: auto;
}
</style>
