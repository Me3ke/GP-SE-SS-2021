import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back comments on document with id docId
    async getComments(docId) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/documents/' + docId + '/comments'
        })
    },
    //  answers comment with id commentId
    async answerComment(docId, commentId, content) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/documents/' + docId + '/comments/'
                + commentId + '/answers',
            data: {
                content: content
            }
        })
    },
    // leaves new comment on document with id docId
    async writeComment(docId, content) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/documents/' + docId + '/comments',
            data: {
                content: content
            }
        })
    }
}
