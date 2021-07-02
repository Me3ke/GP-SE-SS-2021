import axios from 'axios';
import store from "@/main/vue/store/store";



export default {
    async getAllUser() {
        console.log('#1')
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/admin/allusers',
            headers: {
                'token': store.state.auth.token
            }
        })
    },

    async makeAdmin(userID) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/admin/makeadmin',
            params: {
                userid: userID
            },
            headers: {
                'token': store.state.auth.token
            }
        })
    }
}
