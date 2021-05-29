import axios from 'axios';
import store from "@/main/vue/store/store";


export default {
    async changePublicKey(publicKey) {

        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/publicKey',
            headers: {"Content-Type": "application/json"},
            data: {
                'publicKey': publicKey
            }
        })
    }
}