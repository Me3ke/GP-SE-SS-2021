import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    documentInfo: {},
    errorGetDocumentInfo: {},
    protocol: {},
    errorGetProtocol: {},
    reviewResponse: {},
    errorReviewDocument: {},
    simpleSignResponse: {},
    errorSimpleSignResponse: {},
    advancedSignResponse: {},
    errorAdvancedSignResponse: {}
}

export const mutations = {
    // sets given document as state
    SET_DOCUMENT_INFO(state, doc) {
        state.documentInfo = doc
    },
    // sets given document as state
    SET_PROTOCOL(state, pro) {
        state.protocol = pro
    },
    // sets response of reviewing (statusCode + message)
    SET_REVIEW_RESPONSE(state, res) {
        state.reviewResponse = res
    },
    // sets response of signing (simple) (statusCode + message)
    SET_SIMPLE_SIGN_RESPONSE(state, res) {
        state.simpleSignResponse = res
    },
    // sets response of signing (advanced) (statusCode + message)
    SET_ADVANCED_SIGN_RESPONSE(state, res) {
        state.advancedSignResponse = res
    },
    // TODO need to change this mutation
    EDIT_DOCUMENT(state, doc) {
        state.document = doc
    },
    // sets error of getDocument request
    SET_ERROR_GET_DOCUMENT_INFO(state, error) {
        state.errorGetDocumentInfo = error
    },
    // sets error of getDocument request
    SET_ERROR_GET_PROTOCOL(state, error) {
        state.errorGetProtocol = error
    },
    // sets error of reviewDocument request
    SET_ERROR_REVIEW_DOCUMENT(state, error) {
        state.errorReviewDocument = error
    },
    // sets error of simpleSignDocument request
    SET_ERROR_SIMPLE_SIGN_DOCUMENT(state, error) {
        state.errorSimpleSignResponse = error
    },
    // sets error of advancedSignDocument request
    SET_ERROR_ADVANCED_SIGN_DOCUMENT(state, error) {
        state.errorSimpleSignResponse = error
    }
}

export const actions = {
    // makes axios call to get document, either sets document (success) or error (error)
    fetchDocumentInfo({commit}, {envId, docId}) {
        return documentAPI.getDocument(envId, docId).then(response => {
            var info = {}
            for (const key in response.data) {
                if (key !== 'data' && Object.prototype.hasOwnProperty.call(response.data, key)) {
                    info[key] = response.data[key]
                }
            }
            commit('SET_DOCUMENT_INFO', info)
            commit('SET_ERROR_GET_DOCUMENT_INFO', {})
        }).catch(error => {
            commit('SET_ERROR_GET_DOCUMENT_INFO', error)
        })
    },
    // makes axios call to get protocol of document, either sets protocol (success) or error (error)
    fetchProtocol({commit}, {docId}) {
        return documentAPI.getProtocol(docId).then(response => {
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
    },
    // makes axios call to sign (simple) document, either sets simpleSignResponse (success) or error (error)
    simpleSignDocument({commit}, {docId}) {
        return documentAPI.simpleSignDocument(docId).then(response => {
            commit('SET_SIMPLE_SIGN_RESPONSE', response.data)
            commit('SET_ERROR_SIMPLE_SIGN_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_SIMPLE_SIGN_DOCUMENT', error)
        })
    },
    // makes axios call to sign (advanced) document, either sets advancedSignResponse (success) or error (error)
    advancedSignDocument({commit}, {docId, signature}) {
        return documentAPI.advancedSignDocument(docId, signature).then(response => {
            commit('SET_ADVANCED_SIGN_RESPONSE', response.data)
            commit('SET_ERROR_ADVANCED_SIGN_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_ADVANCED_SIGN_DOCUMENT', error)
        })
    }
}

export const getters = {
    getDocumentInfo: (state) => {
        return state.documentInfo
    },
    getProtocol: (state) => {
        return state.protocol
    },
    getReviewStatus: (state) => {
        return state.reviewResponse.status
    },
    getSimpleSignStatus: (state) => {
        return state.simpleSignResponse.status
    },
    getAdvancedSignStatus: (state) => {
        return state.advancedSignResponse.status
    },
    getErrorGetDocumentInfo: (state) => {
        return state.errorGetDocumentInfo
    },
    getErrorGetProtocol: (state) => {
        return state.errorGetProtocol
    },
    getErrorReviewDocument: (state) => {
        return state.errorReviewDocument
    },
    getErrorSimpleSignDocument: (state) => {
        return state.errorSimpleSignResponse
    },
    getErrorAdvancedSignDocument: (state) => {
        return state.errorAdvancedSignResponse
    }
}
