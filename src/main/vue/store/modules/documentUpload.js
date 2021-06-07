import documentUploadAPI from "@/main/vue/api/documentUploadAPI";

export const namespaced = true

export const state = {
    errorUploadDocument: {},
    errorCreateEnvelope: {},
    createdEnvelope: {}
}

export const mutations = {
    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENTS(state, error) {
        state.errorUploadDocument = error
    },
    SET_ERROR_CREATE_ENVELOPE(state, error) {
        state.errorCreateEnvelope = error
    },
    SET_CREATED_ENVELOPE(state, envelope) {
        state.createdEnvelope = envelope
    }
}

export const actions = {
    // makes axios call to upload document

    uploadDocument({commit}, {envID, file, settings}) {
        documentUploadAPI.uploadDocumentApi(envID, file, settings).then(() => {
            console.log("success")
            commit('SET_ERROR_GET_DOCUMENTS', {})
        }).catch(error => {
            commit('SET_ERROR_GET_DOCUMENTS', error)
        })
    },
    async createEnvelope({commit}, {name}) {
        await documentUploadAPI.createEnvelopeApi(name).then((response) => {
            console.log("success")
            commit('SET_CREATED_ENVELOPE', response.data)
            commit('SET_ERROR_CREATE_ENVELOPE', {})
        }).catch(error => {
            commit('SET_ERROR_CREATE_ENVELOPE', error)
        })
    }}

export const getters = {
    getErrorUploadDocument: (state) => {
        return state.errorUploadDocument
    },
    getErrorCreateEnvelope: (state) => {
        return state.errorCreateEnvelope
    },
    getCreatedEnvelope: (state) => {
        return state.createdEnvelope
    }
}
