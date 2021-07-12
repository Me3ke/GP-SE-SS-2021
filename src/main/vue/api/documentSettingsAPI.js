import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back settings of all documents in an envelope
    async getEnvelopeSettings(envId) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelope/' + envId + '/settings'
        })
    },
    // sets settings of a document
    async changeDocumentSettings(envId, docId, settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/document/' + docId + '/settings',
            data: {
                signatories: settings.signatories,
                endDate: settings.endDate,
                orderRelevant: settings.orderRelevant,
            }
        })
    }
}
