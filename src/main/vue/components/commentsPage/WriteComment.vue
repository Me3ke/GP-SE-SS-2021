<template>
    <div>

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
            <div class="option">
                <b-icon icon="reply-fill" class="my-icon" style="margin-left: 1em"></b-icon>
                <span class="action-text" style="margin-left: 0.3em">{{ $t('CommentsPage.send') }}</span>
            </div>
        </div>
    </div>
</template>

<script>
import NameBubble from "@/main/vue/components/commentsPage/NameBubble";
import {mapGetters} from "vuex";

export default {
    name: "WriteComment",
    components: {NameBubble},
    data() {
        return {
            comment: ''
        }
    },
    mounted() {
        this.$store.dispatch('fetchUser')
    },
    methods: {
        cancel() {
            this.comment = ''
            this.$emit('close')
        }
        // TODO: add reply method via api , also emit close then
    },
    computed: {
        ...mapGetters({
            user: 'getUser'
        })
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
