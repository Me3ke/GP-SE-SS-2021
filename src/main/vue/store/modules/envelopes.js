export const state = {
    envelopes: [{
        id: 1,
        name: "Titel dieses Envelopes!",
        owner: {
            id: 11,
            eMail: "sehrTolle@email.com",
            firstname: "Otto",
            lastname: "Wehner"
        },
        creationDate: "03.03.2021",
        documents: [
            {
                id: 12,
                title: "Titel dieses Dokumentes",
                creationDate: "03.03.2021",
                owner: {
                    id: 11,
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
                    id: 11,
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
        ]
    }, {
        id: 2,
        name: "Titel dieses tollen Envelopes!",
        owner: {
            id: 11,
            eMail: "sehrTolle@email.com",
            firstname: "Otto",
            lastname: "Wehner"
        },
        creationDate: "03.03.2021",
        documents: [
            {
                id: 21,
                title: "Titel dieses Dokumentes",
                creationDate: "03.03.2021",
                owner: {
                    id: 11,
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
        ]
    }]
}

export const getters = {
    getEnvelopes: (state) => {
        return state.envelopes
    },
    getEnvelope: (state) => (id) => {
        return state.envelopes.find(env => env.id === id)
    }
}
