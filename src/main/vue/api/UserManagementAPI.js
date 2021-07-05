import axios from 'axios';
import store from "@/main/vue/store/store";


export default {

    async getAllUser() {
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
    },

    async validateUser(userID) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/admin/validate',
            params: {
                userid: userID
            },
            headers: {
                'token': store.state.auth.token
            }
        })
    },

    async lockUser(userID) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/admin/lockUser',
            params: {
                userid: userID
            },
            headers: {
                'token': store.state.auth.token
            }
        })
    },

    async seeUser(userID) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/admin/userseen',
            params: {
                userid: userID
            },
            headers: {
                'token': store.state.auth.token
            }
        })
    },
}
