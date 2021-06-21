<template>
    <div>
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
                <div class="option">
                    <b-icon icon="reply" class="my-icon" style="margin-left: 1em"></b-icon>
                    <span class="action-text" style="margin-left: 0.3em">{{ $t('CommentsPage.reply') }}</span>
                </div>
            </div>
            <div class="answer-container" v-if="show[comment.commentID]">
                <CommentBox v-for="answer in comment.answers" :key="answer.answerID"
                            :comment="answer"></CommentBox>
            </div>
        </div>
    </div>
</template>

<script>
import CommentBox from "@/main/vue/components/commentsPage/CommentBox";

export default {
    name: "AnswerBox",
    components: {CommentBox},
    props: {
        comments: Array
    },
    data() {
        return {
            show: []
        }
    },
    methods: {
        // toggles if answers to given comment wit ID id show
        showAnswers(id) {
            this.$set(this.show, id, !this.show[id])
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
