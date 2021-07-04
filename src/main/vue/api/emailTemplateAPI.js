import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back all Email templates associated with user
    async getEmailTemplates() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates'
        })
    },

    // TODO add subject, name, system
    async createEmailTemplate(emailTemp) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates',
            param: {
                template: emailTemp,
            }

        })
    },

    async editEmailTemplate(templateId, templateBody) {
        return axios({
          method: 'put',
          url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates/' + templateId,
         param: {
             reworkedTemplate: templateBody
         }
        })

    }

}

