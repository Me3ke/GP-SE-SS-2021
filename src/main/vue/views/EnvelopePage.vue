<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>

        <BaseHeading v-if="loaded" :name="envelope(envId).name" :translate="false"></BaseHeading>

        <b-row align-h="center">
            <b-col>
                <button :class="{inactive: page === 0, active: page > 0}" @click="prev()" style="padding: 0; height:2em; width:2em">
                    <b-icon class="icon" icon="arrow-left-short"></b-icon>
                </button>
            </b-col>
            <b-col>
                <h4 v-if="page === 0">
                    {{ $t('OverviewPage.heading') }}
                </h4>
                <h4 v-if="page > 0 && page <= this.envelope(this.envId).documents.length">
                    {{this.envelope(this.envId).documents[this.page - 1].title}}
                </h4>
                <h4 v-if="page > this.envelope(this.envId).documents.length">
                    {{$t('EnvelopePage.readAndSign')}}
                </h4>
            </b-col>
            <b-col>
                <button :class="{inactive: page > this.envelope(this.envId).documents.length, active: page <= this.envelope(this.envId).documents.length}" @click="next()" style="padding: 0; height:2em; width:2em">
                    <b-icon class="icon" icon="arrow-right-short"></b-icon>
                </button>
            </b-col>
        </b-row>

        <!-- Documents -->
        <div class="container-fluid" v-if="loaded">
            <div style="margin-top:5.5vh">
                <div class="overflow-auto" style="height: 85.25vh">
                    <div v-if="page === 0">
                        <div v-for="document in this.envelope(envId).documents" :key="document.id"
                             style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                            <DocumentCard :document="document" :envelopeId="envId" :show-progress="true"></DocumentCard>
                        </div>
                    </div>
                    <div v-if="page > 0 && page <= this.envelope(this.envId).documents.length">
                        <DocumentPageReduced :key="page" :envId="envId" :docId="this.envelope(this.envId).documents[this.page - 1].id"></DocumentPageReduced>
                    </div>
                    <div v-if="page > this.envelope(this.envId).documents.length">
                        <button class="elsa-blue-btn">
                            <h5>
                                {{$t('EnvelopePage.readAll')}}
                            </h5>
                        </button>
                        <button class="elsa-blue-btn">
                            <h5>
                                {{$t('EnvelopePage.signAll')}}
                            </h5>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import {mapGetters} from "vuex";
import DocumentCard from "@/main/vue/components/overviewPage/DocumentCard";
import DocumentPageReduced from "@/main/vue/components/envelopePage/DocumentPageReduced";

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, DocumentCard, DocumentPageReduced},
    data() {
        return {
            page: 0,
            loaded: false
        }
    },
    methods: {
        next() {
            if(this.page <= this.envelope(this.envId).documents.length) {
                this.page = this.page + 1;
            }
        },
        prev() {
            if (this.page > 0) {
                this.page = this.page - 1;
            }
        }
    },
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope',
        }),
        envId() {
            return this.$route.params.envId;
        }
    },
    async mounted() {
        await this.$store.dispatch('envelopes/fetchEnvelopes')
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('document/resetState')
        this.loaded = false
    }


}
</script>

<style scoped>
.light-btn, .elsa-blue-btn {
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
}

.light-btn:focus, .elsa-blue-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}

.icon {
    height: 2em;
    width: auto;
}

.active {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
}

.inactive {
    background-color: var(--light-grey);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.33vw;
    cursor: auto;
}

.active:hover {
    background-color: var(--dark-grey);
    color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
