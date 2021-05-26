import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back document with id docId  in envelop wit id envId, does not download it
    async getDocument(envId) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api.elsa.de/user/' + store.state.auth.username + '/envelopes/' + envId,
            data: {
                path: null,
                title: null,
                type: null,
                signatories: null,
                readers: null,
                signatureType: null,
                endDate: null,
                orderRelevant: null,
                state: null
            }
        })
    }
}
