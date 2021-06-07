import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    //qr code get request
    async getQrCode() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/qrCode'
        })
    },

    //hasSetUp get request
    async getHasSetUp() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/2FAconfigurated'
        })
    },

    //post request to test if qrCodeCode is correct
    async postValidateCode(code) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/settings/qrCodeCode',
            headers: {"Content-Type": "application/json"},
            data: {
                "qrCodeCode": code
            }
        })
    }
}
