
import generateKeyPair from "./keypairGenerator"

export const state = {
   keypair: {}
}

export const mutations = {
    SET_KEYPAIR(state, keypair) {
        state.keypair = keypair
    },
    CLEAR_KEYPAIR(state) {
        state.keypair = {}
    }
}

export const actions = {
    callToGenerate({commit}) {
        commit('SET_KEYPAIR',generateKeyPair())
    },
    callToClear({commit}) {
        commit('CLEAR_KEYPAIR')
    }
}

export const getters = {
    getKeypair: (state) => {
        return state.keypair
    }
}