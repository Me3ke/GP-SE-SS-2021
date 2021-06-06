import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    protocol: {},
    errorGetProtocol: {},
    newVersionIds: {},
    errorEditDocument: {}
}

export const mutations = {
    // sets given document as state
    SET_DOCUMENT(state, doc) {
        state.document = doc
    },
    // sets given document as state
    SET_PROTOCOL(state, pro) {
        state.protocol = pro
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
    SET_ERROR_GET_PROTOCOL(state, error) {
        state.errorGetProtocol = error
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
    // makes axios call to get protocol of document, either sets protocol (success) or error (error)
    fetchProtocol({commit}, {docId}) {
        documentAPI.getProtocol(docId).then(response => {
            commit('SET_PROTOCOL', response.data)
            commit('SET_ERROR_GET_PROTOCOL', {})
        }).catch(error => {
            commit('SET_ERROR_GET_PROTOCOL', error)
        })
    },
      // makes axios call to put the newDocument and archive the old one
       editDocument({commit,state}, {newDoc, envId, docId}) {
        return documentAPI.editDocument(envId, docId, newDoc).then((response) => {
            commit('SET_IDS', response.data)
            console.log("res: ", response.data)
            console.log("state: ", state.newVersionIds)
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
        console.log("In DocumentJS: " , state.newVersionIds.newDocumentID)
        return state.newVersionIds.newDocumentID
    },
    getProtocol: (state) => {
        return state.protocol
    },
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
    },
    getErrorGetProtocol: (state) => {
        return state.errorGetProtocol
    },

    getErrorEditDocument: (state) => {
        return state.errorEditDocument
    }
}
