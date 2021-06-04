import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    newVersionIds: {},
    errorEditDocument: {}

}

export const mutations = {
    // sets given document as state
    SET_DOCUMENT(state, doc) {
        state.document = doc
    },

    // sets ids of new document
    SET_IDS(state, ids) {
        state.newVersionIds = ids
    },

    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
    },

    //sets error of getDocument request
    SET_ERROR_EDIT_DOCUMENT(state, error) {
        state.errorEditDocument = error
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
    // makes axios call to put the newDocument and archive the old one
    editDocument({commit}, {newDoc, envId, docId}) {
        return documentAPI.editDocument(envId, docId, newDoc).then((response) => {
            commit('SET_IDS', response.data)
            commit('SET_ERROR_EDIT_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_EDIT_DOCUMENT', error)
        })
    }
}

export const getters = {
    getDocument: (state) => {
        return state.document
    },
    getNewDocumentId: (state) => {
        return state.newVersionIds.newDocumentID
    },
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
    },
    getErrorEditDocument: (state) => {
        return state.errorGetDocument
    }

}
