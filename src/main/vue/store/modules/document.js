export const state = {
    // TODO replace example data with only ONE document via axios call
    documents: [
        {
            id: 1,
            title: "Essensplan KW 25",
            creationDate: "01.05.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "15.06.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        },
        {
            id: 2,
            title: "Essensplan KW 26",
            creationDate: "22.05.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "25.06.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        }
        , {
            id: 3,
            title: "Essensplan KW 24",
            creationDate: "01.05.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "closed",
            endDate: "15.06.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: true,
            read: false
        }
        ,
        {
            id: 4,
            title: "Kühlschrank EG",
            creationDate: "25.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "26.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: true,
            signed: false,
            read: false
        },
        {
            id: 5,
            title: "Kühlschrank 1.OG",
            creationDate: "26.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "02.06.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: false,
            reader: true,
            signed: false,
            read: false
        }
        , {
            id: 6,
            title: "Bedienungsanleitung Wasserspender",
            creationDate: "15.04.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "open",
            endDate: "19.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        }
        , {
            id: 7,
            title: "Kantinenordnung",
            creationDate: "01.04.2021",
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
        }
        , {
            id: 8,
            title: "Hygienics and reduced amount of visitor passes",
            creationDate: "03.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Hans",
                lastname: "Schneider"
            },
            state: "open",
            endDate: "25.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: false,
            reader: false,
            signed: false,
            read: false
        },
        {
            id: 9,
            title: "Programme",
            creationDate: "03.03.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Hans",
                lastname: "Schneider"
            },
            state: "open",
            endDate: "25.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: false,
            reader: false,
            signed: false,
            read: false
        }
        ,
        {
            id: 10,
            title: "Mietvertrag Büroräume ",
            creationDate: "10.01.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Hans",
                lastname: "Schneider"
            },
            state: "open",
            endDate: "23.06.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: false,
            reader: false,
            signed: false,
            read: false
        }
        ,
        {
            id: 11,
            title: "Handout 2. Kundengespräch",
            creationDate: "25.01.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            state: "closed",
            endDate: "25.02.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: true,
            reader: false,
            signed: false,
            read: false
        }
        ,
        {
            id: 12,
            title: "Plan die Weltherrschaft zu eroberen",
            creationDate: "05.01.2021",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Hans",
                lastname: "Schneider"
            },
            state: "closed",
            endDate: "01.05.2021",
            dataType: "PDF",
            signatureType: "simple",
            signatory: false,
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
