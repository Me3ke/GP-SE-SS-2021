import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    protocol: {},
    errorGetProtocol: {},
    reviewResponse: {},
    errorReviewDocument: {}
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
    // sets response of reviewing (statusCode + message)
    SET_REVIEW_RESPONSE(state, res) {
        state.reviewResponse = res
    },
    // TODO need to change this mutation
    EDIT_DOCUMENT(state, doc) {
        state.document = doc
    },
    // sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
    },
    // sets error of getDocument request
    SET_ERROR_GET_PROTOCOL(state, error) {
        state.errorGetProtocol = error
    },
    // sets error of reviewDocument request
    SET_ERROR_REVIEW_DOCUMENT(state, error) {
        state.errorReviewDocument = error
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
    // makes axios call to review document, either sets reviewResponse (success) or error (error)
    reviewDocument({commit}, {docId}) {
        return documentAPI.reviewDocument(docId).then(response => {
            commit('SET_REVIEW_RESPONSE', response.data)
            commit('SET_ERROR_REVIEW_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_REVIEW_DOCUMENT', error)
        })
    }
}

export const getters = {
    getDocument: (state) => {
        return state.document
    },
    getProtocol: (state) => {
        return state.protocol
    },
    getReviewStatus: (state) => {
        return state.reviewResponse.status
    },
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
    },
    getErrorGetProtocol: (state) => {
        return state.errorGetProtocol
    },
    getErrorReviewDocument: (state) => {
        return state.errorReviewDocument
    }
}
