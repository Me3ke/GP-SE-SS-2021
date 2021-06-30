import envelopeAPI from "@/main/vue/api/envelopeAPI";
import store from "@/main/vue/store/store";

export const namespaced = true

export const state = {
    envelopes: {},
    errorGetEnvelopes: {}
}

export const mutations = {
    // sets given envelopes as state
    SET_ENVELOPES(state, envelopes) {
        state.envelopes = envelopes
    },

    //sets error of getDocument request
    SET_ERROR_GET_ENVELOPES(state, error) {
        state.errorGetEnvelopes = error
    }
}

export const actions = {
    // makes axios call to get envelopes, either sets envelopes (success) or error (error)
    fetchEnvelopes({commit}) {
        envelopeAPI.getEnvelopes().then(response => {
            commit('SET_ENVELOPES', response.data)
            commit('SET_ERROR_GET_ENVELOPES', {})
        }).catch(error => {
            commit('SET_ERROR_GET_ENVELOPES', error)
        })
    }
}

export const getters = {
    getFilteredPagedEnvelopes: (state) => (filters, pageLimit, page) => {
        let result = [];
        //filter
        /* Default Values:
        state: "",
        owner: false,
        search: "",
        signatory: false,
        reader: false,

        dataType: ""
        creationDateMin: "",
        creationDateMax: "",
        endDateMin: "",
        endDateMax: "",
        */

        let i
        for (i = 0; i < state.envelopes.length; i++) {
            let stateFilter = false
            let search = false
            let signatory = false
            let reader = false
            let j
            for (j = 0; j < state.envelopes[i].documents.length; j++) {
                let document = state.envelopes[i].documents[j]
                // Filter for state
                if (filters.state.includes(document.state)) {
                    stateFilter = true
                }
                //Filter for documents that need to be signed
                if(document.signatory && !document.signed){
                    signatory = true
                }
                //Filter for documents that need to be read
                if(document.reader && !document.read){
                    reader = true
                }
                //Search in document title
                if(document.title.toLowerCase().includes(filters.search.toLowerCase())) {
                    search = true
                }
            }
            //Search in Envelope name
            if(state.envelopes[i].name.toLowerCase().includes(filters.search.toLowerCase())) {
                search = true
            }
            // Add matching results
            let matches = true
            if(!(filters.state === "")){
                if(!stateFilter) {
                    matches = false
                }
            }
            if(filters.owner){
                if(!(store.state.auth.username === state.envelopes[i].owner.email)) {
                    matches = false
                }
            }
            if(filters.signatory && filters.reader){
                if(!signatory && !reader) {
                    matches = false
                }
            } else if (filters.signatory) {
                if(!signatory) {
                    matches = false
                }
            } else if(filters.reader){
                if(!reader) {
                    matches = false
                }
            }
            if(!(filters.search === "")) {
                if(!search) {
                    matches = false
                }
            }
            if(matches) {
                result.push(state.envelopes[i])
            }
        }

        //sorting


        //paging
        result = result.slice((page-1)*pageLimit, page*pageLimit)

        return result
    },
    getFilteredEnvelopes: (state) => (filters) =>  {
        let result = [];
        let i
        for (i = 0; i < state.envelopes.length; i++) {
            let open = false
            let j
            for (j = 0; j < state.envelopes[i].documents.length; j++) {
                // Filter for state TODO: Other filter functions
                let env_state = state.envelopes[i].documents[j].state
                if (!(filters.state === null) && (env_state === "OPEN" || env_state === "READ")) {
                    open = true
                }
            }
            if ((open === true && filters.state === "open") || (open === false && filters.state === "closed") || filters.state === null) {
                result.push(state.envelopes[i])
            }
        }
        return result;
    },
    getEnvelopes: (state) => {
        return state.envelopes;
    },
    getErrorGetEnvelopes: (state) => {
        return state.errorGetEnvelopes
    },
    getEnvelope: (state) => (id) => {
        return state.envelopes.find(env => env.id === id)
    }
}
