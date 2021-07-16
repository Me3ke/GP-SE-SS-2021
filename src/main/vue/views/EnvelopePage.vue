<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>

        <BaseHeading v-if="loaded" :name="envelope(envId).name" :translate="false"></BaseHeading>


        <!-- Documents -->
        <div class="container-fluid" v-if="loaded">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in envelope(envId).documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">

                        <!-- Default -->
                        <DocumentCard :document="document" :envelopeId="envId" :show-progress="true"></DocumentCard>
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

export default {
    name: "EnvelopePage",
    components: {Footer, Header, DocumentCard},
    data() {
        return {
            loaded: false
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

</style>
