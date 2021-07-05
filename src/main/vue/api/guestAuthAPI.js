import axios from 'axios';


export default {
    async guestValidate(envID, docId, guestToken) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/envelopes/' + envID + '/documents/' + docId + '/token/' + guestToken
        })
    },

    async guestSimpleSign(envId, docId, guestToken) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/token/' + guestToken + '/envelopes/' + envId + '/documents/' + docId + '/signSimple'
        })
    },

    async guestReview(envId, docId, guestToken) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/token/' + guestToken + '/envelopes/' + envId + '/documents/' + docId + '/review'
        })
    }
}
