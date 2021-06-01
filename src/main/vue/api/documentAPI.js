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
    async editDocument(envId, docId, newDoc) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes/' + envId + '/documents/' + docId,
            data: {
                'byte': newDoc.byte[1],
                'title': newDoc.title,
                'type': newDoc.type,
                'signatoriesId': newDoc.signatoriesId,
                'readersId': newDoc.readersId,
                'signatureType': newDoc.signatureType,
                'endDate': newDoc.endDate,
                'orderRelevant': newDoc.orderRelevant,
                'documentState': newDoc.state,
                'lastModifiedTime': newDoc.lastModified
            }
        })
    }
}
