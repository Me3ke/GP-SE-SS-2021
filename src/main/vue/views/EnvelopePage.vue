<template>
    <div>
        <Header></Header>
        <BaseHeading :name="getEnv(envId).name" style="position: fixed"></BaseHeading>

        <!-- Documents -->
        <div class="container-fluid">
            <div style="margin-top:7.5vh">
                <div class="overflow-auto" style="height: 83.25vh">
                    <div v-for="document in getEnv(envId).documents" :key="document.id"
                         style="position: static; margin-top: 1vh; margin-left: 0.5vw;">
                        <!-- Different styles for open/closed documents TODO-->
                        <div>
                            <DocumentBox
                                @click.native="$router.push({name: 'document', params: {docId: document.id, envId: envId, userId: document.owner.id}})"
                                :doc="document">
                            </DocumentBox>
                        </div>
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
import DocumentBox from "@/main/vue/components/DocumentBox";

export default {
    name: "EnvelopePage",
    props: {
        envId: [Number, String]
    },
    components: {Footer, Header, DocumentBox},
    computed: {
        getEnv() {
            return this.$store.getters.getEnvelope
        }
    }
}
</script>

<style scoped>

</style>
