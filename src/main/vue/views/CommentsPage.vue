<template>
    <div>

        <Header></Header>

        <BaseHeading name="CommentsPage.title"></BaseHeading>

        <!-- Restricted Comments -->
        <div v-if="!isPublic && !isOwner && loaded && !hasError" class="container-fluid bigSpan" id="hint">
            <span> {{ $t('CommentsPage.hint') }} </span>
        </div>

        <!-- Comment Number and Sorting -->
        <div class="container-fluid" style="display: flex" v-if="loaded">
            <span class="bigSpan" v-if=" comments.length === 1"> {{ commentsAmount }} {{
                    $t('CommentsPage.comment')
                }}</span>
            <span class="bigSpan" v-else> {{ commentsAmount }}  {{ $t('CommentsPage.comments') }}</span>

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

        <!-- Comments did not load? -->
        <div v-if="hasError" class="container-fluid">
            <b-alert :show="hasError"
                     style="margin-bottom: 1em">
                <div class="my-icon-small">
                    {{ $t('CommentsPage.serverErrorThree') }}
                </div>
                <div class="my-icon-small">
                    {{ $t('CommentsPage.serverErrorTwo') }}
                </div>
            </b-alert>
        </div>

        <div v-else>
            <!-- No comments -->
            <div v-if="comments.length === 0 && loaded" class="container-fluid bigSpan">
                <span> {{ $t('CommentsPage.empty') }} </span>
            </div>

            <!-- All comments -->
            <AllCommentsBox v-if="loaded" :comments="comments"></AllCommentsBox>
        </div>

        <Footer></Footer>

    </div>
</template>

<script>
import Footer from "@/main/vue/components/Footer";
import Header from "@/main/vue/components/header/Header";
import WriteComment from "@/main/vue/components/commentsPage/WriteComment";
import AllCommentsBox from "@/main/vue/components/commentsPage/AllCommentsBox";

import {mapGetters} from "vuex";
import _ from "lodash";


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
            loaded: false
        }
    },
    async mounted() {
        await this.$store.dispatch('document/fetchDocument', {
            envId: this.$route.params.envId,
            docId: this.$route.params.docId
        })
        await this.$store.dispatch('comments/fetchComments', this.$route.params.docId)
        this.loaded = true
    },
    beforeDestroy() {
        this.$store.dispatch('comments/resetComments')
    },
    computed: {
        ...mapGetters({
            commentsOld: 'comments/getCommentsOldest',
            commentsNew: 'comments/getCommentsNewest',
            commentsError: 'comments/getFetchCommentsError',

            isPublic: 'comments/getPublic',
            document: 'document/getDocument'
        }),

        // gives back comments array depending on sorting option
        comments() {
            if (this.sortNew) {
                return this.commentsNew
            } else {
                return this.commentsOld
            }
        },

        // returns the number of comments (including comments that are answers)
        commentsAmount() {
            if (this.loaded) {
                let answerAmount = 0

                for (let i = 0; i < this.comments.length; i++) {
                    answerAmount += this.comments[i].answers.length
                }

                return this.comments.length + answerAmount
            } else {
                return -1
            }

        },

        // returns if user owner of current document
        isOwner() {
            if (this.document.owner) {
                return this.document.owner.email === this.$store.state.auth.username
            }
            return false
        },

        hasError() {
            return !_.isEmpty(this.commentsError);
        }
    }
}
</script>

<style scoped src="../assets/css/commentsPage.css">
</style>
