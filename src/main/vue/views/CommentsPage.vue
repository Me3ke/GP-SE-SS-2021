<template>
    <div>

        <Header></Header>

        <BaseHeading name="CommentsPage.title"></BaseHeading>

        <!-- Comment Number and Sorting -->
        <div class="container-fluid" style="display: flex">
            <span class="bigSpan" v-if=" this.comments.length === 0"> {{ $t('CommentsPage.comment') }}</span>
            <span class="bigSpan" v-else> {{ this.comments.length }} {{ $t('CommentsPage.comments') }}</span>

            <b-dropdown class="my-dropdown-menu my-dropdown-toggle" style="margin-left: 2em" no-caret>
                <template #button-content>
                    <b-icon icon="bar-chart-fill" rotate="-90"
                            style="transform: rotateY(180deg);"
                            class="my-icon"></b-icon>
                </template>
                <b-dropdown-item class="my-dropdown-item" @click="sortNew = true"> {{
                        $t('CommentsPage.newest')
                    }}
                </b-dropdown-item>
                <b-dropdown-divider class="my-divider"></b-dropdown-divider>
                <b-dropdown-item class="my-dropdown-item" @click="sortNew = false">{{
                        $t('CommentsPage.oldest')
                    }}
                </b-dropdown-item>
            </b-dropdown>
            <span class="bigSpan" style="margin-bottom: 1.35em"> {{ $t('CommentsPage.sort') }} </span>
        </div>

        <!-- Writing comment -->
        <div class="container-fluid write-container">
            <WriteComment></WriteComment>
        </div>

        <!-- All comments -->
        <AllCommentsBox :comments="comments"></AllCommentsBox>


        <Footer></Footer>

    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import WriteComment from "@/main/vue/components/commentsPage/WriteComment";
import AllCommentsBox from "@/main/vue/components/commentsPage/AllCommentsBox";

import {mapGetters} from "vuex";


export default {
    name: "CommentsPage",
    components: {
        AllCommentsBox,
        WriteComment,
        Footer,
        Header
    },
    data() {
        return {
            sortNew: true,
        }
    },
    computed: {
        ...mapGetters({
            commentsOld: 'comments/getCommentsOldest',
            commentsNew: 'comments/getCommentsNewest',
        }),

        // gives back comments array depending on sorting option
        comments() {
            if (this.sortNew) {
                return this.commentsNew
            } else {
                return this.commentsOld
            }
        }
    }
}
</script>

<style scoped src="../assets/css/commentsPage.css">
</style>
