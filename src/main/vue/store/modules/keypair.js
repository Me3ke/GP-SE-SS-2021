
import generateKeyPair from "./keypairGenerator"
import api from "@/main/vue/api";

export const state = {
   keypair: {
       publicKey:null,
       privateKey:null
   }
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
        commit('SET_KEYPAIR', generateKeyPair());
    },
    sendPublicKey({commit},{publicKey}) {
        api.publicKeyAPI.changePublicKey(publicKey).then().catch(error => {
            commit('CLEAR_KEYPAIR')
            console.log(error)
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
}