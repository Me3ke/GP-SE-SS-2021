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
                    {{ commentTime }}
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
        }),
        commentTime() {
            var now = new Date().toISOString();
            var nowYear = now.split('T')[0].slice(0, 4)
            var nowMonth = now.split('T')[0].slice(5, 7)
            var nowDay = now.split('T')[0].slice(8, 10)
            var nowHour = parseInt(now.split('T')[1].slice(0, 2))
            var nowMinute = parseInt(now.split('T')[1].slice(3, 5))

            var postedYear = this.comment.creationDate.split('T')[0].slice(0, 4)
            var postedMonth = this.comment.creationDate.split('T')[0].slice(5, 7)
            var postedDay = this.comment.creationDate.split('T')[0].slice(8, 10)
            var postedHour = parseInt(this.comment.creationDate.split('T')[1].slice(0, 2)) - 2
            var postedMinute = parseInt(this.comment.creationDate.split('T')[1].slice(3, 5))

            if (nowYear === postedYear && nowMonth === postedMonth && nowDay === postedDay) {
                console.log(nowHour === postedHour)
                if (nowHour === postedHour) {
                    var diff = nowMinute - postedMinute
                    if (diff === 0) {
                        if (this.$i18n.locale === 'de') {
                            return "Gerade eben"
                        } else {
                            return "Just now"
                        }
                    } else {
                        if (this.$i18n.locale === 'de') {
                            if (diff === 1) {
                                return "Vor " + diff + " Minute"
                            } else {
                                return "Vor " + diff + " Minuten"
                            }
                        } else {
                            if (diff === 1) {
                                return diff + " minute ago"
                            } else {
                                return diff + " minutes ago"
                            }
                        }
                    }
                } else {
                    var diff2 = nowHour - postedHour

                    if (this.$i18n.locale === 'de') {
                        if (diff2 === 1) {
                            return "Vor " + diff2 + " Stunde"
                        } else {
                            return "Vor " + diff2 + " Stunden"
                        }
                    } else {
                        if (diff2 === 1) {
                            return diff2 + " hour ago"
                        } else {
                            return diff2 + " hours ago"
                        }
                    }
                }
            } else if (nowYear === postedYear && nowMonth === postedMonth) {
                var diff3 = nowMonth - postedMonth

                if (this.$i18n.locale === 'de') {
                    if (diff3 === 1) {
                        return "Vor " + diff3 + " Monat"
                    } else {
                        return "Vor " + diff3 + " Monaten"
                    }
                } else {
                    if (diff3 === 1) {
                        return diff3 + " month ago"
                    } else {
                        return diff3 + " months ago"
                    }
                }
            } else {
                var diff4 = nowYear - postedYear

                if (this.$i18n.locale === 'de') {
                    if (diff4 === 1) {
                        return "Vor " + diff4 + " Jahr"
                    } else {
                        return "Vor " + diff4 + " Jahren"
                    }
                } else {
                    if (diff4 === 1) {
                        return diff4 + " year ago"
                    } else {
                        return diff4 + " years ago"
                    }
                }
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/commentsPage.css">
</style>
