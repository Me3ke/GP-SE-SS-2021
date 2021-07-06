import emailTemplateAPI from "@/main/vue/api/emailTemplateAPI";


export const namespaced = true

export const state = {

    template: [],
    templateError: {}
}

export const mutations = {

    RESET_STATE_EMAIL_TEMPLATE(state) {
      state.template = []
    },

    FETCH_TEMPLATE(state, emailTemp) {
        state.template = emailTemp
    },

    SET_TEMPLATE(state, progress) {
        state.template.push(progress)
    },

    EDIT_TEMPLATE(state, progress) {
        for(let i = 0; i < state.template.length; i++) {
            if(state.template[i].templateID === progress.templateID) {
                state.template[i] = progress
            }
        }
    },

    DELETE_TEMPLATE(state, templateId) {
        state.template = state.template.filter(temp => temp.templateID !== templateId)
    },

    SET_TEMPLATE_ERROR(state, error) {
        state.templateError = error
    }
}


export const actions = {

    resetEmailTemplate({commit}) {
        commit('RESET_STATE_EMAIL_TEMPLATE')
    },

    fetchEmailTemplate({commit}) {
        emailTemplateAPI.getEmailTemplates().then(response => {
            commit('FETCH_TEMPLATE', response.data)
            commit('SET_TEMPLATE_ERROR', {})
        }).catch(error => {
            commit('SET_TEMPLATE_ERROR', error)
        })
    },

    async setEmailTemplate({commit}, template) {
        await emailTemplateAPI.createEmailTemplate(template).then(response => {
            commit('SET_TEMPLATE', response.data)
            commit('SET_TEMPLATE_ERROR', {})
        }).catch(error => {
            commit('SET_TEMPLATE_ERROR', error)
        })

        console.log("Success")
    },

    editEmailTemplate({commit}, template) {
            emailTemplateAPI.editEmailTemplate(template).then(response => {
                commit('EDIT_TEMPLATE', response.data)
                commit('SET_TEMPLATE_ERROR', {})
            }).catch(error => {
                console.error(error)
                commit('SET_TEMPLATE_ERROR', error)

            })
    },

    deleteEmailTemplate({commit}, templateId) {
        emailTemplateAPI.deleteEmailTemplates(templateId).then(response => {
            commit('EDIT_TEMPLATE', response.data)
            commit('SET_TEMPLATE_ERROR', {})
        }).catch(error => {
            console.error(error)
            commit('SET_TEMPLATE_ERROR', error)

        })
    }

}

export const getters = {

    getEmailTemplates: (state) => {
        return state.template
    }

}
