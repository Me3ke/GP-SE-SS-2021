<template>
    <b-container fluid style="padding: 0">
        <div
            :class="{envelopeOpen: (!toSign && !toRead && open) && !draft, envelopeClosed: !open, envelopeSignRead: ((toSign || toRead) && open) && !draft, envelopeDraft: open && draft}">
            <div class="media">
                <b-icon icon="envelope"
                        :class="{iconRed: (toSign || toRead) && open && !draft, iconBlue: (!toSign && !toRead && !draft) || !open, icon: draft && open}"
                        style="margin:0.5em"></b-icon>
                <div class="media-body">
                    <b-container fluid>
                        <b-row align-h="start">
                            <b-col style="width: 100%; margin: 0">
                                <b-row align-h="between">
                                    <b-col cols="auto">
                                        <h4>
                                            {{ this.envelope.name }}
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
                                        <div v-if="open && draft">
                                            {{ $t('Document.draft') }}
                                        </div>
                                    </b-col>
                                </b-row>
                                <b-row align-h="between">
                                    <b-col cols="auto">
                                        <h6>
                                            {{ $t('Document.owner') }}: {{ this.envelope.owner.firstname }}
                                            {{ this.envelope.owner.lastname }}
                                        </h6>
                                    </b-col>
                                    <b-col cols="auto">
                                        <h6>
                                            {{ $t('Document.date') }}: {{ this.envelope.creationDate }}
                                        </h6>
                                    </b-col>
                                    <b-col style="text-align: right; margin-bottom: 0.5rem" cols="auto">
                                        <settingsButton
                                            v-if="envelope.owner.username === this.$store.state.auth.username"
                                            @click.native.stop="settings()"></settingsButton>
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
import settingsButton from "@/main/vue/components/overviewPage/envSettingsButton";

export default {
    name: "EnvelopeBox",
    props: {
        envelope: Object
    },
    components: {settingsButton},
    data() {
        let open = false;
        let toSign = false;
        let toRead = false;
        let draft = false;
        let i;
        for (i = 0; i < this.envelope.documents.length; i++) {
            let document = this.envelope.documents[i];
            if (document.state === "REVIEW" || document.state === "SIGN") {
                open = true;
            }
            if (document.turnToSign && document.signatory && !document.signed) {
                toSign = true;
            }
            if (document.turnToReview && document.reader && !document.read) {
                toRead = true;
            }
            if (document.draft) {
                draft = true;
            }
        }
        return {open: open, toSign: toSign, toRead: toRead, draft: draft}
    },
    methods: {
        settings() {
            let envelopeId = this.envelope.id;
            this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: envelopeId}).then(() => this.$router.push({
                name: 'settings',
                params: {envId: this.envelope.id}
            }));
        }
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
    color: var(--dark-grey);
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
    color: var(--dark-grey);
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
