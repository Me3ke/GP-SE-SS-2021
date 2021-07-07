import documentAPI from "@/main/vue/api/documentAPI";
import {filterHistory, sortHistory} from "@/main/vue/scripts/filterSortHistory";

export const namespaced = true

export const state = {

    documentInfo: {},
    errorGetDocumentInfo: {},
    documentHistory: {},
    errorGetDocumentHistory: {},
    documentSeen: {},
    errorDocumentSeen: {},
    errorGetDocument: {},
    errorGetDocumentProgress: {},
    documentProgressArray: [],
    protocol: {},
    errorGetProtocol: {},
    newVersionIds: {},
    errorEditDocument: {},
    reviewResponse: {},
    errorReviewDocument: {},
    simpleSignResponse: {},
    errorSimpleSignResponse: {},
    advancedSignResponse: {},
    errorAdvancedSignResponse: {}

}

export const mutations = {
    // will sets the state to []
    RESET_STATE_DOCUMENT_PROGRESS_ARRAY(state) {
        state.documentProgressArray = []
    },

    // sets given document as state
    SET_DOCUMENT_INFO(state, doc) {
        state.documentInfo = doc
    },

    SET_DOCUMENT_HISTORY(state, his) {
        state.documentHistory = his
    },

    // sets ids of new document
    SET_IDS(state, ids) {
        state.newVersionIds = ids
    },

    //sets error of getDocument request
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
    SET_DOCUMENT_PROGRESS(state, progress) {
        state.documentProgressArray.push(progress)
    },
    SET_ERROR_DOCUMENT_PROGRESS(state, error) {
        state.errorGetDocumentProgress = error
    },
    // sets error of getDocument request
    SET_ERROR_GET_DOCUMENT(state, error) {
        state.errorGetDocument = error
    },

    SET_DOCUMENT_SEEN(state, seen) {
        state.documentSeen = seen
    },
    // sets error of getDocument request
    SET_ERROR_GET_DOCUMENT_INFO(state, error) {
        state.errorGetDocumentInfo = error
    },

    SET_ERROR_GET_DOCUMENT_HISTORY(state, error) {
        state.errorGetDocumentHistory = error
    },

    //sets error of getDocument request
    SET_ERROR_EDIT_DOCUMENT(state, error) {
        state.errorEditDocument = error
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
    },
    SET_DOCUMENT_SEEN_ERROR(state, error) {
        state.errorDocumentSeen = error
    }


}

export const actions = {

    // for resetting the state DOCUMENT_PROGRESS_ARRAY
    resetState({commit}) {
        commit('RESET_STATE_DOCUMENT_PROGRESS_ARRAY')
    },

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

    // fetches History of document, saves without byte arrays
    fetchDocumentHistory({commit}, {envId, docId}) {
        return documentAPI.getHistory(envId, docId).then(response => {
            commit('SET_DOCUMENT_HISTORY', response.data)
            commit('SET_ERROR_GET_DOCUMENT_HISTORY', {})
        }).catch(error => {
            commit('SET_ERROR_GET_DOCUMENT_HISTORY', error)
        })

    },

    // clears history
    clearHistory({commit}) {
        commit('SET_DOCUMENT_HISTORY', {})
        commit('SET_ERROR_GET_DOCUMENT_HISTORY', {})
    },

    // makes axios call to get information if user has already seen the document
    fetchSeen({commit}, docId) {
        return documentAPI.getDocumentSeen(docId).then(response => {
            commit('SET_DOCUMENT_SEEN', response.data);
            commit('SET_DOCUMENT_SEEN_ERROR', {})
        }).catch(error => {
            commit('SET_DOCUMENT_SEEN_ERROR', error)
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
    },
    setSeenFalse({commit}) {

        commit('SET_DOCUMENT_SEEN', false);
    },
    // makes axios call to review document, either sets reviewResponse (success) or error (error)
    reviewDocument({commit}, {envId, docId}) {
        return documentAPI.reviewDocument(envId, docId).then(response => {
            commit('SET_REVIEW_RESPONSE', response.data)
            commit('SET_ERROR_REVIEW_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_REVIEW_DOCUMENT', error)
        })
    },
    // makes axios call to sign (simple) document, either sets simpleSignResponse (success) or error (error)
    simpleSignDocument({commit}, {envId, docId}) {
        return documentAPI.simpleSignDocument(envId, docId).then(response => {
            commit('SET_SIMPLE_SIGN_RESPONSE', response.data)
            commit('SET_ERROR_SIMPLE_SIGN_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_SIMPLE_SIGN_DOCUMENT', error)
        })

    },
    // makes axios call to sign (advanced) document, either sets advancedSignResponse (success) or error (error)
    advancedSignDocument({commit}, {envId, docId, signature}) {
        return documentAPI.advancedSignDocument(envId, docId, signature).then(response => {
            commit('SET_ADVANCED_SIGN_RESPONSE', response.data)
            commit('SET_ERROR_ADVANCED_SIGN_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_ADVANCED_SIGN_DOCUMENT', error)
        })
    },

    async documentProgress({commit, state}, {envId, docId}) {
        let doBreak = false
        for (let i = 0; state.documentProgressArray.length; i++) {
            if (state.documentProgressArray[i].docId === docId) {
                doBreak = true
                break
            }
        }
        if (!doBreak) {
            await documentAPI.getDocumentProgress(envId, docId).then((response) => {
                let data = response.data
                let progress = {docId, data}
                commit('SET_DOCUMENT_PROGRESS', progress)
                commit('SET_ERROR_DOCUMENT_PROGRESS', {})
            }).catch(error => {
                console.error(error)
                commit('SET_ERROR_DOCUMENT_PROGRESS', error)
            })
        }
    },

    progressOfAllDocumentsInEnv({commit, state}, {envelope}) {
        let doBreak = false;
        let promises = []

        for (let i = 0; i < envelope.documents.length; i++) {
            for (let j = 0; j < state.documentProgressArray.length; j++) {
                if (state.documentProgressArray[j].docId === envelope.documents[i].id) {
                    doBreak = true
                    break
                }
            }
            if (!doBreak) {

                promises.push(documentAPI.getDocumentProgress(envelope.id, envelope.documents[i].id).then((response) => {
                    let data = response.data
                    let docId = envelope.documents[i].id
                    let progress = {docId, data}
                    commit('SET_DOCUMENT_PROGRESS', progress)

                }).catch(err => {
                    console.error(err)
                    commit('SET_ERROR_DOCUMENT_PROGRESS', err)
                }))
            }
            doBreak = false
        }
        return Promise.all(promises)
    }
}

export const getters = {
    getDocumentInfo: (state) => {
        return state.documentInfo
    },
    getDocumentHistorySorted: (state) => (filters, pageLimit, page) => {
        //filter
        let filteredHistory = filterHistory(state.documentHistory, filters);

        //sorting
        let sortedHistory = sortHistory(filteredHistory, filters);

        //paging
        return sortedHistory.slice((page - 1) * pageLimit, page * pageLimit)
    },

    getNewDocumentId: (state) => {
        return state.newVersionIds.newDocumentID
    },

    getEditDocumentStatus: (state) => {
        return state.newVersionIds.status
    },
    // for calculating all progress of all documents in env
    getDocumentProgressArray: (state) => {
        return state.documentProgressArray
    },

    getDocumentProgressArrayById: (state) => (id) => {
        let documentProgress
        for (let i = 0; i < state.documentProgressArray.length; i++) {
            if (state.documentProgressArray[i].docId === id) {
                documentProgress = state.documentProgressArray[i]
            }
        }
        return documentProgress
    },


    getDocumentProgressArrayByEnvelope: (state) => (documents) => {
        let envelopeProgress = []
        for (let i = 0; i < state.documentProgressArray.length; i++) {
            for (let j = 0; j < documents.length; j++) {
                if (state.documentProgressArray[i].docId === documents[j].id) {
                    envelopeProgress.push(state.documentProgressArray[i])
                    break
                }
            }
        }
        return envelopeProgress
    },

    getErrorGetDocumentProgress: (state) => {
        return state.errorGetDocumentProgress
    },
    getProtocol: (state) => {
        return state.protocol
    },
    getSeen: (state) => {
        return state.documentSeen
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
    getErrorGetDocumentHistory: (state) => {
        return state.errorGetDocumentHistory
    },
    getErrorEditDocument: (state) => {
        return state.errorEditDocument
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
