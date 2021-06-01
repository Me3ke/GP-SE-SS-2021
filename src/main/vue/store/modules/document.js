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

    EDIT_DOCUMENT(state,doc, docId) {
        const originalDocIndex = state.documents.findIndex(i => i.id === docId);
        state.documents[originalDocIndex] = doc
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
    /*
    TODO add axios call to change the document in the database instead of changing the state
     (local storage, need refreshing for changing the view)
     */

    editDocument({commit}, {newDoc, envId, docId}) {
        console.log(envId)
        console.log(newDoc)
        console.log('typ: ',typeof newDoc.type)
        console.log('state: ',typeof newDoc.state)
        console.log('endDate: ',typeof newDoc.endDate)
        console.log('lastModified: ',typeof newDoc.lastModified)
        console.log('sign Type: ',typeof newDoc.signatureType)


        // todo getting 400 bad request
        documentAPI.editDocument(envId, docId, newDoc).then(response => {
            commit('EDIT_DOCUMENT', response.data, docId)
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
