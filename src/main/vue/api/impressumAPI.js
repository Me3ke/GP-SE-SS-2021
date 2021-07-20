import axios from "axios";
import store from "@/main/vue/store/store";


export default {

    async getImpressum(){
        return axios({
            method: 'get',
            url: 'http://localhost:8088/api/impressum',
        })
    },

    async updateImpressum(impressumMessage) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/impressum',
            headers: {
                'token': store.state.auth.token
            },
            data: {
                'impressum': impressumMessage
            }

        })
    }

}
