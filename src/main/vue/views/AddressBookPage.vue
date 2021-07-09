<template>
<div>
    <Header></Header>

    <BaseHeading name="AddressBook.title"></BaseHeading>

    <Footer></Footer>
</div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "AddressBookPage",
    components:{
        Footer,
        Header
    }
}
</script>

<style scoped src="../assets/css/userManagement.css">
</style>
