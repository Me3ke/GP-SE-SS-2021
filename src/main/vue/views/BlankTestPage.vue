<template>
    <div>
        <ImpressumEditor></ImpressumEditor>
    </div>
</template>

<script>


import {mapGetters} from "vuex";
import ImpressumEditor from "@/main/vue/components/corporateDesign/ImpressumEditor";

export default {
    name: "BlankTestPage",
    components: {ImpressumEditor},
    data() {
        return {
            text: ''
        }
    },

    computed: {
        ...mapGetters({
            document: 'document/getDocument'
        })
    },
    methods: {
    }
}
</script>

<style scoped>

</style>
