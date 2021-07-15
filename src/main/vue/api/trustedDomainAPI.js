import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    async getTrustedDomain() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/email/settings/trustedDomain?token='+ store.state.auth.token
        })
    },
    async putTrustedDomain(domain) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/email/settings/trustedDomain?token='+ store.state.auth.token + '&domain=.*@' + domain
        })
    },
    async getSMTP() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/email/settings?token='+ store.state.auth.token
        })
    },
    async putSMTP(host, port, username, password, mailSMTPAuth, mailSMTPStartTLSEnable) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/email/settings?token='+ store.state.auth.token ,
            data: {
                'host': host,
                'port': port,
                'username': username,
                'password': password,
                'mailSMTPAuth': mailSMTPAuth,
                'mailSMTPStartTLSEnable': mailSMTPStartTLSEnable
            }
        })
    },
}
