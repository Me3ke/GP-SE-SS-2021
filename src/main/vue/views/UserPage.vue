<template>
    <div class="background" style="background-color: var(--whitesmoke);">
        <div>
            <Header></Header>
        </div>

        <BaseHeading name="UserPage.content"> </BaseHeading>

        <div>
            <UserInfoBox :user-information="user"></UserInfoBox>
        </div>

    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import UserInfoBox from "@/main/vue/components/UserInfoBox";
import {mapState} from "vuex"

export default {
    name: "UserPage",
    components: {Header, UserInfoBox},
    computed: mapState({
        user: state => state.user
    }),
    /*
    methods: {
      ...mapActions([
        'requestUser' //<1>
      ]),
    },
    created() {
      this.id = 'hans.schneider@mail.de'
      this.requestUser(this.id) //<2>
    }
     */
}
</script>

<style scoped>

.background {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-image: linear-gradient(to bottom, var(--background-fade-one) 0%, var(--background-fade-two) 30%, var(--background-fade-three) 100%), url(../assets/background.png);
    background-repeat: no-repeat;
    background-size: 100% auto;
}

@media screen and (min-width: 1200px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (min-width: 1191px) and (max-width: 1199px) {
    #top-text {
        font-size: 1.3em;
    }
}


@media screen and (min-width: 992px) and (max-width: 1190px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (min-width: 801px) and (max-width: 991px) {
    #top-text {
        font-size: 1.3em;
    }
}


@media screen and (min-width: 768px) and (max-width: 800px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 767px) and (min-width: 501px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 500px) and (min-width: 459px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (min-width: 410px) and (max-width: 458px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 409px) and (min-width: 355px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 354px) and (min-width: 330px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 329px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 304px) and (min-width: 294px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 293px) and (min-width: 289px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 288px) and (min-width: 240px) {
    #top-text {
        font-size: 1.3em;
    }
}

@media screen and (max-width: 239px) {
    #top-text {
        font-size: 1.3em;
    }
}

</style>
