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
    // gives back protocol of document with id docId
    async getProtocol(docId) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/' + 'documents/' + docId + '/protocol'
        })
    },
    // reviews document with id docId
    async reviewDocument(docId) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/documents/' + docId + '/review'
        })
    },

    async editDocument(envId, docId, newDoc) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/envelopes/' + envId + '/documents/' + docId,

            data: {
                //'byte': newDoc.byte[1],
                'data': newDoc.data,
                'title': newDoc.title,
                'dataType': newDoc.dataType,
                'signatoriesID': newDoc.signatoriesId,
                'readersID': newDoc.readersId,
                'signatureType': newDoc.signatureType,
                'endDate': null,
                'orderRelevant': newDoc.orderRelevant,
                'state': newDoc.state,
                'lastModified': null
            }
        })
    }
}
