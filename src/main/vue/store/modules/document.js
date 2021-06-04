import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {}
}

export const mutations = {
    // sets given document as state
    SET_DOCUMENT(state, doc) {
        state.document = doc
    },
    // TODO need to change this mutation
    EDIT_DOCUMENT(state,doc) {
        state.document = doc
    },

    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
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
            commit('EDIT_DOCUMENT', response.data)
            // to get the new Id of the new Document (for router)
            return response.data.newDocumentID
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
