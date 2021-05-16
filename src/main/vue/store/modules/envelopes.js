export const state = {
    envelopes: [{
        id: 1,
        name: "Titel dieses Envelopes!",
        owner: {
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
            }
        ]
    }, {
        id: 2,
        name: "Titel dieses tollen Envelopes!",
        owner: {
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
    },
    getEnvelopesFiltered: (state) => (open, closed, onlyOwned) => {
        let result;
        result = [];
        if (open === true) {
            let i;
            for (i = 0; i < state.envelopes.length; i++) {
                if (!(state.envelopes[i].documents.filter(document => document.state === "open").length === 0)) {
                    result.push(state.envelopes[i]);
                }
            }
        } else if (closed === true) {
            let i;
            for (i = 0; i < state.envelopes.length; i++) {
                if (state.envelopes[i].documents.filter(document => document.state === "open").length === 0) {
                    result.push(state.envelopes[i]);
                }
            }
        } else {
            result = state.envelopes;
        }
        if (onlyOwned === true) {
             result = result.filter(env => env.owner.eMail === "sehrTolle@email.com"); // TODO!!!!!!!!!!!!
        }
        return result;
    }
}
