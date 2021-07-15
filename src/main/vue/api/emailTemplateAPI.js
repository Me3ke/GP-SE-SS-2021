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
    async createEmailTemplate(template) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates',
            data: {
                'htmlBody': template.htmlTemplateBody,
                'subject': template.subject,
                'name': template.name
            }

        })
    },

    async editEmailTemplate(template) {
        return axios({
            method: 'put',
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates/' + template.templateID,
            data: {
                'htmlBody': template.htmlTemplateBody,
                'subject': template.subject,
                'name': template.name
            }
        })
    },

    async deleteEmailTemplates(templateId) {
        return axios({
            method: 'delete',
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/templates/' + templateId,
        })
    }

}

