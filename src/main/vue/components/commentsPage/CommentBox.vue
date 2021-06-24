<template>
    <div>
        <!-- Write area -->
        <div style="display: flex; margin-bottom: 1em">
            <NameBubble name="Hans Peter "></NameBubble>
            <div class="comment-container">
                <div class="action-text justify-content-between" style="display:flex;">
                    <div>
                        {{ comment.authorName }}
                        <b-badge v-if="comment.authorID === loggedIn">{{ $t('CommentsPage.owner') }}</b-badge>
                    </div>
                    {{ comment.date }}
                </div>
                <hr class="comment-divider">
                <span class="comment-content">
                    {{ comment.content }}
                </span>
            </div>
        </div>
    </div>
</template>

<script>
import NameBubble from "@/main/vue/components/commentsPage/NameBubble";
import {mapGetters} from "vuex";

export default {
    name: "CommentBox",
    props: {
        comment: Object
    },
    components: {NameBubble},
    computed: {
        ...mapGetters({
            loggedIn: 'getUsername'
        })
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
