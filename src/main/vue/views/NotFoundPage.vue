<template>
    <b-container fluid id="container">
        <b-row cols="1">
            <b-col>
                <Header></Header>
            </b-col>
        </b-row>

        <b-row class="mb-2 mt-5 text-center" align-v="center" align-h="center" cols="1">
            <b-col id="h1" class="text">
                <h1>{{ $t('NotFoundPage.h1') }}</h1>
            </b-col>
            <b-col class="text">
                <h3>{{ $t('NotFoundPage.h3') }}</h3>
            </b-col>
            <b-col class="text">
                <h6>{{ $t('NotFoundPage.errorCode') }}</h6>
            </b-col>
        </b-row>

        <b-row class="mt-5 text-center" align-v="center" align-h="center" cols="2">
            <b-col cols="3" class="text">
                <div class="link" @click="historyThere()  ? $router.go(-1) : $router.push('/')">
                    {{ $t('NotFoundPage.back') }}
                </div>
            </b-col>
            <b-col cols="3" class="text">
                <!-- To-Do: Add  real Route to Home -->
                <div class="link" @click="$router.push(`/`)">
                    {{ $t('NotFoundPage.start') }}
                </div>
            </b-col>
        </b-row>

        <b-row cols="1">
            <b-col>
                <Footer></Footer>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "NotFoundPage",
    components: {Footer, Header},
    methods: {
        historyThere() {
            return window.history.length > 2
        }
    }
}
</script>

<style scoped>
.text {
    margin-bottom: 1vw;
}

#container {
    padding-right: 0;
    padding-left: 0;
}

#h1 {
    margin-top: 7vw;
}

</style>
