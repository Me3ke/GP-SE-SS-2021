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
    }
}
