
import generateKeyPair from "./keypairGenerator"
import api from "@/main/vue/api";

export const state = {
    keypair: {
       publicKey:null,
       privateKey:null
    },
    sendingSuccess: {}
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
    }
}

export const actions = {
    callToGenerate({commit}) {
        commit('SET_KEYPAIR', generateKeyPair());
    },
    sendPublicKey({commit},{publicKey}) {
        api.publicKeyAPI.changePublicKey(publicKey).then(
            commit('SET_SENDING_SUCCESS', {})
        ).catch(error => {
            commit('SET_SENDING_SUCCESS', error)
        })
    }
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
}