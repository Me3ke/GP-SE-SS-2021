<template>
    <div>

        <Header></Header>

        <BaseHeading name="CommentsPage.title"></BaseHeading>

        <Footer></Footer>

    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";

export default {
    name: "CommentsPage",
    components: {
        Footer,
        Header
    },
}
</script>

<style scoped>

</style>
