<template>
    <div class="container-fluid">
        <div class="overflow-auto">
            <div v-for="comment in comments" :key="comment.commentID">
                <CommentBox :comment="comment"></CommentBox>

                <div class="justify-content-between answer-container show-answers"
                     style="display: flex; margin-bottom: 1.35em">

                    <!-- Show answers? -->
                    <span v-if="comment.answers.length > 0 && show[comment.commentID]"
                          @click="showAnswers(comment.commentID)" class="option">
                            {{ $t('CommentsPage.doNotAnswers') }}
                     </span>
                    <span v-else-if="comment.answers.length > 0" @click="showAnswers(comment.commentID)"
                          class="option">
                                 {{ comment.answers.length }} {{ $t('CommentsPage.answers') }}
                     </span>
                    <span v-else>
                            {{ $t('CommentsPage.noAnswers') }}
                    </span>

                    <!-- Reply -->
                    <div class="option" @click="doReply(comment.commentID)">
                        <b-icon icon="pencil" class="my-icon my-icon-small" style="margin-left: 1em"></b-icon>
                        <span style="margin-left: 0.3em">{{ $t('CommentsPage.reply') }}</span>
                    </div>
                </div>

                <!-- Answers -->
                <div class="answer-container" v-if="show[comment.commentID]">

                    <!-- Write reply-->
                    <WriteComment v-if="reply[comment.commentID]" style="margin-bottom: 1em"
                                  :commentId="comment.commentID"
                                  @close="closeReply(comment.commentID)"></WriteComment>

                    <CommentBox v-for="answer in comment.answers" :key="answer.answerID"
                                :comment="answer"></CommentBox>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommentBox from "@/main/vue/components/commentsPage/CommentBox";
import WriteComment from "@/main/vue/components/commentsPage/WriteComment";

export default {
    name: "AllCommentsBox",
    components: {WriteComment, CommentBox},
    props: {
        comments: Array
    },
    data() {
        return {
            show: [],
            reply: []
        }
    },
    methods: {
        // toggles if answers to given comment with commentID id show
        showAnswers(id) {
            this.$set(this.show, id, !this.show[id])
        },
        // opens up reply box for comment with commentID id
        doReply(id) {
            this.$set(this.show, id, true)
            this.$set(this.reply, id, true)
        },
        // closes reply box
        closeReply(id) {
            this.$set(this.reply, id, false)
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
