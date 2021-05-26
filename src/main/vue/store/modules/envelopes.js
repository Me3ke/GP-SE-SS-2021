import envelopeAPI from "@/main/vue/api/envelopeAPI";

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
    getEnvelopes: (state) => (filters) => {
        let result = [];
        let i
        for (i = 0; i < state.envelopes.length; i++) {
            console.log(state.envelopes[i].name)
            let open = false
            let j
            for (j = 0; j < state.envelopes[i].documents.length; j++) {
                console.log(state.envelopes[i].documents[j].state)
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
        return result
    },
    getErrorGetEnvelopes: (state) => {
        return state.errorGetEnvelopes
    },
    getEnvelope: (state) => (id) => {
        return state.envelopes.find(env => env.id === id)
    }
}
