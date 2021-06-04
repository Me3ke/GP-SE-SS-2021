import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    errorDownloadDocument: {}
}

export const mutations = {
    // sets given document as state
    SET_DOCUMENT(state, doc) {
        state.document = doc
    },

    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
    },

    //sets error of downloadDocument request
    SET_ERROR_DOWNLOAD_DOCUMENT(state, error) {
        state.errorDownloadDocument = error
    }
}

export const actions = {
    // makes axios call to get document, either sets document (success) or error (error)
    fetchDocument({commit}, {envId, docId}) {
        documentAPI.getDocument(envId, docId).then(response => {
            commit('SET_DOCUMENT', response.data)
            commit('SET_ERROR_GET_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_GET_DOCUMENT', error)
        })
    },

    // makes axios call to download document to given path, either sets document (success) or error (error)
    downloadDocument({commit}, {envId, docId}) {
        return documentAPI.downloadDocument(envId, docId).then(() => {
           // commit('SET_DOCUMENT', response.data)
            commit('SET_ERROR_DOWNLOAD_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_DOWNLOAD_DOCUMENT', error)
        })
    }
}

export const getters = {
    getDocument: (state) => {
        return state.document
    },
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
    }

}
