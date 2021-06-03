import axios from 'axios';
import store from "@/main/vue/store/store";


export default {
    async getMyUser() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username
        })
    },

    async registerUser(username, firstname, lastname, password) {
        return axios(
            {
                method: "post",
                url: 'http://localhost:8088/api/newUser/',
                headers: {"Content-Type": "application/json"},
                data: {
                    "username": username,
                    "firstname": firstname,
                    "lastname": lastname,
                    "password": password,
                    "street": "testStreet",
                    "housenumber": "0",
                    "postcode": "0",
                    "hometown": "testTown",
                    "country": "Germany",
                    "birthday": "2021-05-31",
                    "phonenumber": "0177598323"
                }
            }
        )

    }
}
