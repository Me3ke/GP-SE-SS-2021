import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back all envelopes associated with user
    async getEnvelopes() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api.elsa.de/user/' + store.state.auth.username + '/envelopes'
        })
    }
}
