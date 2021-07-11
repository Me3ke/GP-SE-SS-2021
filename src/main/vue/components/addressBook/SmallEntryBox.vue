<template>
    <div class="small-entry-box">
        <b-container fluid>
            <b-row style="margin-bottom: 1rem; margin-top: 0.5rem">
                <b-col cols="10">
                    <span style="display: block">{{ entry.email }}</span>
                </b-col>
                <b-col cols="2" style="text-align: right">
                    <b-icon v-if="favorite" icon="bookmark-star-fill" class="my-icon" ></b-icon>
                    <b-icon v-else icon="bookmark-star" class="my-icon"></b-icon>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "SmallEntryBox",
    props: {
        entry: Object,
        favorite: Boolean
    },
    computed: {
        ...mapGetters({
            allUser: 'userManagement/getFilteredPagesUsers'
        })
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
