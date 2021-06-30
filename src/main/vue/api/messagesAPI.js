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
    },
    // gives back messages settings
    async getMessagesConfig() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/messages/settingsConfig'
        })
    },
    // sets message settings on server
    async putMessagesConfig(settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/messages/settingsChange',
            data: {
                id: settings.id,
                toDo: settings.toDo,
                sign: settings.sign,
                read: settings.read,
                progress: settings.progress,
                newVersion: settings.newVersion,
            }
        })
    },
    // deletes message with messageID id on server
    async deleteMessage(id) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/message/' + id + '/delete'
        })
    }
}
