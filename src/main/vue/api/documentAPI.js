import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back document with id docId  in envelop wit id envId, does not download it
    async getDocument(envId, docId) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes/' + envId + '/documents/' + docId
        })
    },

    // downloads document with id docId  in envelop wit id envId into given path
    async downloadDocument(envId, docId) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes/' + envId + '/documents/' + docId + '/download',
            params: {
                path: null
            }
        })
    }
}
