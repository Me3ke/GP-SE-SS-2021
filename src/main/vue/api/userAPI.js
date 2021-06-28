import axios from 'axios';
import store from "@/main/vue/store/store";


export default {
    async getMyUser() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username
        })
    },
    async resetPassword(username) {
        return axios(
            {
                method: "get",
                url: 'http://localhost:8088/api/user/resetPassword/' + username
            }
        )
    },
    async setNewPassword(username, token, password) {
        return axios(
            {
                method: "post",
                url: 'http://localhost:8088/api/user/' + username + '/resetPassword/' + token,
                headers: {"Content-Type": "application/json", "Accept": "*/*", "Connection": "keep-alive"},
                data: {
                    "password": password
                }
            }
        )
    },

    async registerUser(register) {
        return axios(
            {
                method: "post",
                url: 'http://localhost:8088/api/newUser',
                headers: {"Content-Type": "application/json", "Accept": "*/*", "Connection": "keep-alive"},
                data: {
                    "username": register.username,
                    "firstname": register.firstname,
                    "lastname": register.surname,
                    "password": register.password,
                    "street": register.street,
                    "housenumber": register.streetNumber,
                    "postcode": register.postCode,
                    "hometown": register.city,
                    "country": "Germany",
                    "birthday": register.birthdate,
                    "phonenumber": register.phoneNumber,
                }
            }
        )
    },
    async confirmUser(token) {
        return axios(
            {
                method: "get",
                url: 'http://localhost:8088/api/newUser/register',
                headers: {"Content-Type": "application/json", "Accept": "*/*", "Connection": "keep-alive"},
                params: {
                    token: token
                }
            }
        )
    },
    // signals that user has been logged in for the first time
    async putFirstLogin() {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/firstLogin'
        })
    },

    // gives back if user wants to always have to do a 2FakAuth after their login
    async getTwoFactorLogin() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/twoFactorLogin'
        })
    },

    // puts information if user wants to always have to do a 2FakAuth after their login
    async putTwoFactorLogin(setting) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/twoFactorLogin',
            headers: {"Content-Type": "application/json"},
            data: {
                'setting': setting
            }
        })
    },

    // sets image of user signature on server
    async putSignature(signature, signatureType) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/imageSignature',
            headers: {"Content-Type": "application/json"},
            data: {
                'imageSignature': signature,
                'imageSignatureType': signatureType
            }
        })
    },

    // gets image of user signature from server
    async getSignature() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/imageSignature'
        })
    }
}
