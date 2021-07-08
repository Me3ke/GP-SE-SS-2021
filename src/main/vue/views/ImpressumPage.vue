<template>
    <div style="background-color: var(--whitesmoke); height: 100vh;">
        <Header></Header>
        <BaseHeading name="Impressum"></BaseHeading>
        <b-container class="full-container">
            <b-container>
                <b-row>
                    <b-col class="col-1" cols="7">
                        <b-container class="impressum-content" v-if="impressumMessage" v-html="impressumMessage" style="margin-top: 3em"></b-container>

                    </b-col>
                    <b-col class="col-2">
                        <img src="../assets/logos/ELSA_medium.svg"  class="img-fluid" alt="Responsive image" >                   </b-col>
                </b-row>
            </b-container>
        </b-container>
    </div>

</template>

<script>
import Header from "@/main/vue/components/header/Header";
import {mapGetters} from "vuex";
export default {
    name: "Impressum",
    components: {Header},
    computed: {
        ...mapGetters({
            impressumMessage: 'impressum/getImpressumResponse',
        })
    },

    mounted() {
        this.$store.dispatch('impressum/fetchImpressum')

        console.log(this.impressumMessage)
    }
}
</script>

<style scoped>

/deep/ .impressum-content p {
    margin-bottom: 0;
    text-align: left;
    padding-left: 2em;
    color: var(--dark-grey);
}

/deep/ .impressum-content {
    border: 1px solid var(--dark-grey);
}



@media (max-width: 575.98px) {
    /deep/ .impressum-content p {
        font-size: 1em;
        padding-left: 1em;
        text-align: center;
    }

    .col-1 {
        background-color: red;

    }

}

@media (min-width: 576px) and (max-width: 767.98px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.75em;
    }
}

@media (min-width: 992px) {
    .navbar-brand, .collapse, .navbar-toggler {
        font-size: 0.75em;
    }
}

</style>
