import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back all Email templates associated with user
    async getEmailTemplates() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates'
        })
    }
}

