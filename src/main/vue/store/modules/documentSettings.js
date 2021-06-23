import documentSettingsAPI from "@/main/vue/api/documentSettingsAPI";

export const namespaced = true

export const state = {
    errorChangeDocumentSettings: {},
    envelopeSettings: [{}],
    errorGetEnvelopeSettings: {}
}

export const mutations = {
    SET_ERROR_CHANGE_DOCUMENT_SETTINGS(state, error) {
        state.errorUpdateDocumentSettings = error
    },
    SET_ENVELOPE_SETTINGS(state, envSet) {
        state.envelopeSettings = envSet
    },
    SET_ERROR_ENVELOPE_SETTINGS(state, error) {
        state.errorGetEnvelopeSettings = error
    }
}

export const actions = {
    fetchEnvelopeSettings({commit}, {envId}) {
        return documentSettingsAPI.getEnvelopeSettings(envId).then(async response => {
            await commit('SET_ENVELOPE_SETTINGS', response.data.documentSettings)
            commit('SET_ERROR_ENVELOPE_SETTINGS', {})
        }).catch(error => {
            console.log(error)
            commit('SET_ERROR_ENVELOPE_SETTINGS', error)
        })
    },
    changeDocumentSettings({commit}, {envId, docId, settings}) {
        return documentSettingsAPI.changeDocumentSettings(envId, docId, settings).then(() => {
            commit('SET_ERROR_CHANGE_DOCUMENT_SETTINGS', {})
        }).catch(error => {
            commit('SET_ERROR_CHANGE_DOCUMENT_SETTINGS', error)
        })
    }
}

export const getters = {
    getEnvelopeSettings: (state) => {
        return state.envelopeSettings
    },
    getErrorChangeDocumentSettings: (state) => {
        return state.errorChangeDocumentSettings
    },
    getErrorGetEnvelopeSettings: (state) => {
        return state.errorGetEnvelopeSettings
    }
}
