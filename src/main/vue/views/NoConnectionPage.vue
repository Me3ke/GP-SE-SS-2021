<template>
    <div id="my-container">

        <Header></Header>
        <div class="d-flex align-items-center" style="height: 80vh">
            <b-container>
                <b-row cols="12">

                    <b-col cols="4">
                        <img :src="turtle" class="responsive-img" alt="turtle">
                    </b-col>

                    <b-col cols="8">
                        <b-container id="container">
                            <b-row class="mb-2 mt-5 text-center" align-v="center" align-h="center" cols="1">
                                <b-col>
                                    <h1>{{ $t('NoConnectionPage.h1') }}</h1>
                                </b-col>
                                <b-col>
                                    <h3>{{ $t('NoConnectionPage.h3') }}</h3>
                                </b-col>
                            </b-row>

                            <b-row class="mt-3 text-center" align-v="center" align-h="center" cols="2">
                                <b-col cols="3" class="text">
                                    <div class="link" @click="historyThere()  ? $router.go(-1) : $router.push('/')">
                                        {{ $t('NoConnectionPage.back') }}
                                    </div>
                                </b-col>
                            </b-row>
                        </b-container>
                    </b-col>

                </b-row>
            </b-container>
        </div>

        <Footer></Footer>

    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "NoConnectionPage",
    components: {Footer, Header},
    data() {
        return {
            turtle: require('../assets/turtle.svg')
        }
    },
    methods: {
        historyThere() {
            return window.history.length > 2
        }
    }
}
</script>

<style scoped>
#my-container {
    padding: 0;
    margin: 0;
    height: 100vh;
    background-color: var(--whitesmoke);
}

.responsive-img {
    height: 15em;
    width: auto;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    h1 {
        font-size: 1.3em;
    }

    h3 {
        font-size: 0.6em;
    }

    h6, .text {
        font-size: 0.5em;
    }

    .responsive-img {
        font-size: 0.4em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    h1 {
        font-size: 1.5em;
    }

    h3 {
        font-size: 0.8em;
    }

    h6, .text {
        font-size: 0.7em;
    }

    .responsive-img {
        font-size: 0.6em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h1 {
        font-size: 1.6em;
    }

    h3 {
        font-size: 0.9em;
    }

    h6, .text {
        font-size: 0.8em;
    }


    .responsive-img {
        font-size: 0.9em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h1 {
        font-size: 1.6em;
    }

    h3 {
        font-size: 0.9em;
    }

    h6, .text {
        font-size: 0.8em;
    }

    .responsive-img {
        font-size: 0.9em;
    }
}
</style>
