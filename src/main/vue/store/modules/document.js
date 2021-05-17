export const state = {
    // TODO replace example data with only ONE document via axios call
    documents: [{
        id: 12,
        title: "Titel dieses Dokumentes",
        creationDate: "03.03.2021",
        owner: {
            eMail: "sehrTolle@email.com",
            firstname: "Otto",
            lastname: "Wehner"
        },
        state: "open",
        endDate: "25.05.2021",
        dataType: "PDF",
        signatureType: "simple",
        signatory: true,
        reader: false,
        signed: false,
        read: false
    },
        {
            id: 13,
            title: "Titel dieses Dokumentes",
            creationDate: "03.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "25.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        },
        {
            id: 21,
            title: "Titel dieses Dokumentes",
            creationDate: "03.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "closed",
            endDate: "05.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        }
    ],
    // TODO removes ones axios call is there
    selectedDocument: {}
}

export const mutations = {
    SET_DOCUMENT(state, doc) {
        state.selectedDocument = doc
    }
}


export const actions = {
    // TODO use service here instead of changing state
    fetchDocument({state, commit}, id) {
        commit('SET_DOCUMENT', state.documents.find(doc => doc.id === id))
    }
}

export const getters = {
    document: (state) => {
        return state.selectedDocument
    }
}
