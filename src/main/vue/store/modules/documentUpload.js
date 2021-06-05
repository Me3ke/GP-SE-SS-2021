import documentUploadAPI from "@/main/vue/api/documentUploadAPI";

export const namespaced = true

export const state = {
    errorUploadDocument: {}
}

export const mutations = {
    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENTS(state, error) {
        state.errorUploadDocument = error
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
    }
}

export const getters = {
    getErrorUploadDocument: (state) => {
        return state.errorGetEnvelopes
    }
}
