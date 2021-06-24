<template>
    <div>
        <!-- Error Messages -->
        <b-alert :show="showError" dismissible
                 @dismissed="showError = false"
                 style="margin-bottom: 1em">
            <span>
                {{ $t('CommentsPage.serverErrorOne') }}
            </span>
            <span>
                {{ $t('CommentsPage.serverErrorTwo') }}
            </span>
        </b-alert>

        <!-- Write area -->
        <div style="display: flex">
            <NameBubble :name="user.firstname + ' ' +  user.lastname"></NameBubble>
            <b-form-textarea
                id="input-comment"
                v-model="comment"
                :placeholder="$t('CommentsPage.writePrompt') + user.firstname + ' ' + user.lastname + '...'"
                rows="1"
                no-resize>
            </b-form-textarea>
        </div>

        <!-- Options -->
        <div style="display: flex;" class="justify-content-end mt-2">
            <!-- Cancel -->
            <div @click="cancel()" class="option">
                <b-icon icon="x-circle-fill" class="my-icon"></b-icon>
                <span class="action-text" style="margin-left: 0.3em">{{ $t('CommentsPage.cancel') }}</span>
            </div>
            <!-- Send -->
            <div @click="postComment()" class="option">
                <b-icon icon="reply-fill" class="my-icon" style="margin-left: 1em"></b-icon>
                <span class="action-text" style="margin-left: 0.3em">{{ $t('CommentsPage.send') }}</span>
            </div>
        </div>
    </div>
</template>

<script>
import NameBubble from "@/main/vue/components/commentsPage/NameBubble";
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "WriteComment",
    components: {NameBubble},
    props: {
        commentId: {
            type: Number,
            required: false,
            default: -1
        }
    },
    data() {
        return {
            comment: '',
            showError: false
        }
    },
    mounted() {
        this.$store.dispatch('fetchUser')
    },
    methods: {
        cancel() {
            this.comment = ''
            this.$emit('close')
        },
        async postComment() {
            // checking if user put in something
            if (this.comment !== '') {

                // checking if it is a new comment or an answer
                if (this.commentId === -1) {
                    await this.$store.dispatch("comments/postComment", {
                        docId: this.$route.params.docId,
                        content: this.comment
                    })
                } else {
                    await this.$store.dispatch("comments/postAnswers", {
                        docId: this.$route.params.docId,
                        commentId: this.commentId,
                        content: this.comment
                    })
                }

                // updating comments in store
                await this.$store.dispatch('comments/fetchComments', this.$route.params.docId)

                // closing if it's used as an answer
                if (!this.hasError) {
                    this.$emit('close')
                    this.comment = ''
                } else {
                    this.showError = true
                }
            }
        }
    },
    computed: {
        ...mapGetters({
            user: 'getUser',
            postErrorComment: 'comments/getPostCommentError',
            postErrorAnswer: 'comments/getAnswerCommentError'
        }),
        hasError() {
            return !_.isEmpty(this.postErrorAnswer) || !_.isEmpty(this.postErrorComment);
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
