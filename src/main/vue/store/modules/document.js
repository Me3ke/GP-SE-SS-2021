import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    errorGetDocumentProgress: {},
    documentProgressArray: [],
    documentProgress: {},
}

export const mutations = {
    // will sets the state to []
    RESET_STATE_DOCUMENT_PROGRESS_ARRAY(state, reset){
        state.documentProgressArray = reset
    },

    // sets given document as state
    SET_DOCUMENT(state, doc) {
        state.document = doc
    },
    // TODO need to change this mutation
    EDIT_DOCUMENT(state,doc) {
        state.document = doc
    },

    SET_DOCUMENT_PROGRESS(state,progress) {
        //state.documentProgress = progress
        state.documentProgressArray.push(progress)
    },

    //sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
    },

    SET_ERROR_GET_DOCUMENT_PROGRESS(state, error) {
        state.errorGetDocumentProgress = error
    }
}

export const actions = {

    // for resetting the state DOCUMENT_PROGRESS_ARRAY
    resetState({commit}, reset) {
        commit('RESET_STATE_DOCUMENT_PROGRESS_ARRAY', reset)
    },

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
    },

    async getDocumentProgress({commit}, {envId, documentsId}) {
         await documentsId.forEach(docId => {
            documentAPI.getDocumentProgress(envId, docId).then((response) => {
                commit('SET_DOCUMENT_PROGRESS', response.data)
                commit('SET_ERROR_GET_DOCUMENT_PROGRESS', {})
            }).catch(error => {
                console.error(error)
                commit('SET_ERROR_GET_DOCUMENT_PROGRESS', error)
            })
        })
        //console.log("SUCCESS")
    }
}

export const getters = {
    getDocument: (state) => {
        return state.document
    },

    getDocumentProgress: (state) => {
        return state.documentProgress
    },

    getDocumentProgressArray : (state) => {
        return state.documentProgressArray
    },
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
    },
    getErrorGetDocumentProgress: (state) => {
        return state.errorGetDocumentProgress
    },
}
