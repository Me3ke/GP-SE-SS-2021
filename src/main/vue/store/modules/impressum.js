import impressumAPI from "@/main/vue/api/impressumAPI";

export const namespaced = true

export const state = {
    impressum: {},
    errorImpressum: {}
}

export const mutations = {
    // sets given envelopes as state
    SET_IMPRESSUM(state, progress) {
        state.impressum = progress
    },

    //sets error of getDocument request
    SET_ERROR_GET_IMPRESSUM(state, error) {
        state.errorImpressum = error
    }
}

export const actions = {
    // makes axios call to get envelopes, either sets envelopes (success) or error (error)
    fetchImpressum({commit}) {
        impressumAPI.getImpressum().then(response => {
            commit('SET_IMPRESSUM', response.data)
            commit('SET_ERROR_GET_IMPRESSUM', {})
        }).catch(error => {
            commit('SET_ERROR_GET_IMPRESSUM', error)
        })
    }
}

export const getters = {
    getImpressumResponse(state) {
        return state.impressum
    },
    getErrorImpressum(state) {
        return state.errorImpressum
    }
}
