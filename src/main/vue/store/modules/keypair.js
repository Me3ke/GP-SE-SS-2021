import generateKeyPair from "./keypairGenerator"
import api from "@/main/vue/api";

export const state = {
    keypair: {
        publicKey: null,
        privateKey: null
    },
    sendingSuccess: {},
    hasKey: {},
    errorHasKey: {}
}

export const mutations = {
    SET_KEYPAIR(state, keypair) {
        state.keypair = keypair
    },
    CLEAR_KEYPAIR(state) {
        state.keypair = {}
    },
    SET_SENDING_SUCCESS(state, sendingSuccess) {
        state.sendingSuccess = sendingSuccess
    },
    SET_HAS_KEY(state, res) {
        state.hasKey = res
    },
    SET_ERROR_HAS_KEY(state, res) {
        state.errorHasKey = res
    }
}

export const actions = {
    callToGenerate({commit}) {
        commit('SET_KEYPAIR', generateKeyPair());
    },
    sendPublicKey({commit}, {publicKey}) {
        api.publicKeyAPI.changePublicKey(publicKey).then(
            commit('SET_SENDING_SUCCESS', {})
        ).catch(error => {
            commit('SET_SENDING_SUCCESS', error)
        })
    },
    // makes axios call to get information if user has already set up keys, either sets hasKey (success) or error (error)
    fetchHasKey({commit}) {
        return api.publicKeyAPI.getHasKey().then(response => {
            commit('SET_HAS_KEY', response.data)
            commit('SET_ERROR_HAS_KEY', {})
        }).catch(error => {
            commit('SET_ERROR_HAS_SET_KEY', error)
        })
    },
}

export const getters = {
    getKeypair: (state) => {
        return state.keypair
    },
    getPrivateKey: (state) => {
        return state.keypair.privateKey
    },
    getPublicKey: (state) => {
        return state.keypair.publicKey
    },
    getSendingSuccess: (state) => {
        return state.sendingSuccess
    },
    getHasKey: (state) => {
        return state.hasKey
    }
}
