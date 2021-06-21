<template>
    <div>
        <!-- Write area -->
        <div style="display: flex; margin-bottom: 1em">
            <NameBubble name="Hans Peter "></NameBubble>
            <div class="comment-container">
                <div class="action-text justify-content-between" style="display:flex;">
                    <div>
                        {{ comment.fromName }}
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

export default {
    name: "CommentBox",
    props: {
        comment: Object
    },
    components: {NameBubble}
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
