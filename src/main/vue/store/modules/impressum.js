import impressumAPI from "@/main/vue/api/impressumAPI";

export const namespaced = true

export const state = {
    impressum: {},
    impressumPutResponse: {},
    errorImpressum: {}
}

export const mutations = {
    // sets given envelopes as state
    SET_IMPRESSUM(state, progress) {
        state.impressum = progress
    },

    UPDATE_IMPRESSUM(state, progress) {
        state.impressumPutResponse = progress
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
    },

    updateImpressum({commit}, impressumText) {
        console.log('-- ' , impressumText)
        impressumAPI.updateImpressum(impressumText).then(response => {
            commit('UPDATE_IMPRESSUM', response.data)
            commit('SET_ERROR_GET_IMPRESSUM', {})
        }).catch(error => {
            commit('SET_ERROR_GET_IMPRESSUM', error)

        })
    }
}

export const getters = {
    getImpressumResponse(state) {
        return state.impressum.message
    },
    getErrorImpressum(state) {
        return state.errorImpressum
    }
}
