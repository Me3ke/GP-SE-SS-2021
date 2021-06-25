import documentAPI from "@/main/vue/api/documentAPI";

export const namespaced = true

export const state = {
    document: {},
    errorGetDocument: {},
    errorGetDocumentProgress: {},
    documentProgressArray: [],
    documentProgress: {},
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
    // will sets the state to []
    RESET_STATE_DOCUMENT_PROGRESS_ARRAY(state){
        state.documentProgressArray = []
    },

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

    SET_DOCUMENT_PROGRESS(state,progress) {
        //state.documentProgress = progress
        state.documentProgressArray.push(progress)
    },
    SET_DOCUMENT_PROGRESS_ARRAY(state,progress) {
        state.documentProgressArray.push(progress)
    },

    SET_ERROR_DOCUMENT_PROGRESS(state, error) {
        state.errorGetDocumentProgress = error
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

    // for resetting the state DOCUMENT_PROGRESS_ARRAY
    resetState({commit}) {
        console.log('i am runnin')
        commit('RESET_STATE_DOCUMENT_PROGRESS_ARRAY')
    },

    // makes axios call to get document, either sets document (success) or error (error)
    fetchDocument({commit}, {envId, docId}) {
        return documentAPI.getDocument(envId, docId).then(response => {
            commit('SET_DOCUMENT', response.data)
            commit('SET_ERROR_GET_DOCUMENT', {})
        }).catch(error => {
            commit('SET_ERROR_GET_DOCUMENT', error)
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
    },

    async documentProgress({commit}, {envId, docId}) {
        commit('RESET_STATE_DOCUMENT_PROGRESS_ARRAY')
        await documentAPI.getDocumentProgress(envId, docId).then((response) => {
            let data = response.data
            //let docProgress = {docId, data}
            let progress = {docId, data}
            commit('SET_DOCUMENT_PROGRESS', progress)
            commit('SET_ERROR_DOCUMENT_PROGRESS', {})
        }).catch(error => {
            console.error(error)
            commit('SET_ERROR_DOCUMENT_PROGRESS', error)
        })
        //console.log("SUCCESS")
    },

     progressOfAllDocumentsInEnv({commit}, {envelope}) {
         //commit('RESET_STATE_DOCUMENT_PROGRESS_ARRAY')

         envelope.documents.forEach(document => {
             documentAPI.getDocumentProgress(envelope.id, document.id).then((response) => {
                 let data = response.data
                 let docId = document.id
                 let progress = {docId, data}
                 commit('SET_DOCUMENT_PROGRESS_ARRAY', progress)

             }).catch(err => {
                 console.error(err)
                 commit('SET_ERROR_DOCUMENT_PROGRESS', err)

             })
         })
     }
}

export const getters = {
    getDocument: (state) => {
        return state.document
    },

    getDocumentProgress: (state) => {
        return state.documentProgress
    },
    // for calculating all progress of all documents in env
    getDocumentProgressArray : (state) => {
        return state.documentProgressArray
    },

    getDocumentProgressArrayById: (state) => (id) => {
        let documentProgress
        for (let i=0; i < state.documentProgressArray.length; i++){
            if(state.documentProgressArray[i].docId === id) {
                documentProgress = state.documentProgressArray[i]
            }
        }
        return documentProgress
    },


    getDocumentProgressArrayByEnvelope: (state) => (documents) => {
        let envelopeProgress = []



        for(let i = 0; i < state.documentProgressArray.length; i++) {
            for(let j = 0; j < documents.length; j++) {
                if(state.documentProgressArray[i].docId === documents[j].id) {
                    envelopeProgress.push(state.documentProgressArray[i])
                    break
                }
            }
        }

        let uniq = {};
        let withOutDup = envelopeProgress.filter(obj => !uniq[obj.docId] && (uniq[obj.docId] = true))
        //envelopeProgress.filter((item, index) => envelopeProgress.i)

        return withOutDup
    },

    getErrorGetDocumentProgress: (state) => {
        return state.errorGetDocumentProgress
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
    getErrorGetDocument: (state) => {
        return state.errorGetDocument
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
