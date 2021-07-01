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

    setEmailTemplate({commit}, template) {
        emailTemplateAPI.createEmailTemplate(template).then(response =>{
            commit('SET_TEMPLATE', response.data)
            commit('SET_TEMPLATE_ERROR', {})
        }).catch(error => {
            commit('SET_TEMPLATE_ERROR', error)
        })
    }

}

export const getters = {

    getEmailTemplates: (state) => {
        console.log(state.template)
        return state.template
    }

}
