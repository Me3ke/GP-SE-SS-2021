import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back messages of current user
    async getMessages() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/messages'
        })
    },
    // sets watched status of message to true
    async updateWatched(msgId) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/messages/' + msgId
        })
    }
}
