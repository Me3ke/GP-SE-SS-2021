import axios from 'axios';
import store from "@/main/vue/store/store";



export default {
    async getMyUserData() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/'+store.state.auth.username+'/personal'
        })
    }
}
