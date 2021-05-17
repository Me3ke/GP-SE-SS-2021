export const state = {
    envelopes: [
        {
                id: 1,
                name: "Wichtige Änderungen der Essenspläne",
                owner: {
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto",
                    lastname: "Wehner"
                },
                creationDate: "01.05.2021",
                documents: [
                    {
                        id: 12,
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
                        id: 13,
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
                    ,{
                        id: 14,
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
                ]
            }
        ,{
            id: 10,
            name: "Nutzungsbedingungen der Gemeinschafts-Kühlschränke",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            creationDate: "25.03.2021",
            documents: [
                {
                    id: 12,
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
                    id: 13,
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
            ]
        }
        ,{
                id: 4,
                name: "Bedienungsanleitung Wasserspender",
                owner: {
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto",
                    lastname: "Wehner"
                },
                creationDate: "15.04.2021",
                documents: [
                    {
                        id: 41,
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
                ]
            }
        ,{
            id: 11,
            name: "Kantinenordnung",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            creationDate: "01.04.2021",
            documents: [
                {
                    id: 41,
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
            ]
        }
        ,{
                id: 5,
                name: "International Congress 2021",
                owner: {
                    eMail: "sehrTolle@email.com",
                    firstname: "Otto",
                    lastname: "Wehner"
                },
                creationDate: "03.03.2021",
                documents: [
                    {
                        id: 12,
                        title: "Hygienics and reduced amount of visitor passes",
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
                        signatory: false,
                        reader: false,
                        signed: false,
                        read: false
                    },
                    {
                        id: 13,
                        title: "Programme",
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
                        signatory: false,
                        reader: false,
                        signed: false,
                        read: false
                    }
                ]
            }
        ,{
            id: 2,
            name: "Mietvertrag Büroräume",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            creationDate: "10.01.2021",
            documents: [
                {
                    id: 21,
                    title: "Mietvertrag Büroräume ",
                    creationDate: "10.01.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
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
            ]
        }
        ,{
            id: 3,
            name: "Abgabe für das 2. Kundengespräch",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            creationDate: "25.01.2021",
            documents: [
                {
                    id: 31,
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
            ]
        }
        ,{
            id: 9,
            name: "Pläne die Weltherrschaft zu eroberen",
            owner: {
                eMail: "sehrTolle@email.com",
                firstname: "Otto",
                lastname: "Wehner"
            },
            creationDate: "05.01.2021",
            documents: [
                {
                    id: 21,
                    title: "Plan die Weltherrschaft zu eroberen",
                    creationDate: "05.01.2021",
                    owner: {
                        eMail: "sehrTolle@email.com",
                        firstname: "Otto",
                        lastname: "Wehner"
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
            ]
        }
    ]
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
