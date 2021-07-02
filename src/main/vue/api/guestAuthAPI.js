import axios from 'axios';


export default {
    async guestValidate(envID,docId,guestToken) {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/envelopes/'+envID+'/documents/'+docId+'/token/'+guestToken
        })
    }
}
