import envelopeAPI from "@/main/vue/api/envelopeAPI";
import {filterEnvelopes} from "../../scripts/filterMethods.js";

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
        envelopeAPI.getEnvelopes().then(async response => {
            await commit('SET_ENVELOPES', response.data)
            await commit('SET_ERROR_GET_ENVELOPES', {})
        }).catch(async error => {
            await commit('SET_ERROR_GET_ENVELOPES', error)
        })
    }
}

export const getters = {
    getFilteredPagedEnvelopes: (state) => (filters, pageLimit, page) => {
        //filter
        let filteredEnvelopes = filterEnvelopes(state.envelopes, filters);

        //sorting

        //paging
        filteredEnvelopes = filteredEnvelopes.slice((page-1)*pageLimit, page*pageLimit)

        return filteredEnvelopes
    },
    getFilteredEnvelopes: (state) => (filters) =>  {
        return filterEnvelopes(state.envelopes, filters);
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
