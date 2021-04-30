<template>
    <div>
        <Header></Header>
        <!-- To use translation for props, type in the string which would be inside of $t() as a prop -->
        <BaseHeading name="TestPage.heading"></BaseHeading>
        <div style="position: relative; margin-top: 7vw;">
            {{ $t('TestPage.message') }}
            <p>
                <BaseIcon name="book" color="red" stroke-width="1"></BaseIcon>
            </p>
            <b-button class="dark-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="light-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="elsa-blue-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="elsa-blue-btn" disabled>{{ $t('TestPage.btn') }}</b-button>
            <b-button class="green-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="active-filter">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="inactive-filter">{{ $t('TestPage.btn') }}</b-button>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/Header";

export default {
    name: "TestPage",
    components: {Header, Footer}
}
</script>

<style scoped>
</style>
.
