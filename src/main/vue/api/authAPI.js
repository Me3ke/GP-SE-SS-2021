import axios from 'axios';


export default {
    async login(username, password) { //<1>
        let bodyFormData = new FormData();
        bodyFormData.append('username', username);
        bodyFormData.append('password', password);
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/authenticate',
            data: bodyFormData,
            headers: {"Content-Type": "multipart/form-data", "Authorization": ""},
        })
    },

    async logout() {

        return axios({
            method: "get",
            url: 'http://localhost:8088/api/authenticate/logout'
        })

    }
}
