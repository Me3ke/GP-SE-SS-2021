import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back document with id docId  in envelop wit id envId, does not download it

    async uploadDocumentApi(envId, file, settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes/' + envId,
            headers: {"Content-Type": "application/json"},
            data: {
                'data': file.data,
                'title': file.title,
                'dataType': file.type,
                'signatories': settings.signatories,
                'endDate': settings.endDate,
                'orderRelevant': settings.orderRelevant,
                'showHistory': settings.showHistory,
            }
        })
    },

    async createEnvelopeApi(name) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes',
            params: {
                name: name
            }
        })
    }
}
