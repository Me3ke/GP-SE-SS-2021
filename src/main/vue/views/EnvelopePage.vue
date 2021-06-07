<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>

        <BaseHeading :name="this.envelope(envId).name" :translate="false"></BaseHeading>


        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in this.envelope(envId).documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <DocumentCard :document="document" :envelopeId="envId">
                        </DocumentCard>
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
import DocumentCard from "@/main/vue/components/DocumentCard";

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    components: {Footer, Header, DocumentCard},
    computed: {
        ...mapGetters({
            envelope: 'envelopes/getEnvelope',
        })
    }
}
</script>

<style scoped>

</style>
