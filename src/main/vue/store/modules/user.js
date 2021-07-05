import api from "@/main/vue/api";

export const state = {
    user: {},
    firstLoginChange: {},
    twoFactorLogin: Boolean,
    errorPutTwoFactorLogin: {},
    putSignatureResponse: {}
}

export const mutations = {
    SET_USER(state, user) {
        state.user = user
    },
    SET_FIRST_LOGIN_CHANGE(state, login) {
        state.firstLoginChange = login
    },
    SET_GET_TWO_FACTOR_LOGIN(state, login) {
        state.twoFactorLogin = login
    },
    SET_ERROR_PUT_TWO_FACTOR_LOGIN(state, error) {
        state.errorPutTwoFactorLogin = error
    },
    SET_PUT_SIGNATURE_RESPONSE(state, res) {
        state.putSignatureResponse = res
    }
}

export const actions = {
    fetchUser({commit}) {
        return api.user.getMyUser().then(response => {
            commit('SET_USER', response.data)
        }).catch(error => {
            console.log(error)
        })
    },
    // signals server that user has been logged in for the first time
    putFirstLogin({commit}) {
        return api.user.putFirstLogin().then(response => {
            commit('SET_FIRST_LOGIN_CHANGE', response.data)
        }).catch(error => {
            console.log(error)
        })
    },
    // gives back if user wants a 2FacAuth at login
    getTwoFactorLogin({commit}) {
        return api.user.getTwoFactorLogin().then(response => {
            commit('SET_GET_TWO_FACTOR_LOGIN', response.data)
        }).catch(error => {
            console.log(error)
        })
    },
    // sets if user wants a 2FacAuth at login
    putTwoFactorLogin({commit}, {setting}) {
        return api.user.putTwoFactorLogin(setting).then(response => {
            commit('SET_GET_TWO_FACTOR_LOGIN', response.data)
        }).catch(error => {
            commit('SET_ERROR_PUT_TWO_FACTOR_LOGIN', error)
        })
    },
    // sets status of putSignature request
    putSignature({commit}, {signature, type}) {
        return api.user.putSignature(signature, type).then(response => {
            commit('SET_PUT_SIGNATURE_RESPONSE', response.data)
        }).catch(error => {
            commit('SET_PUT_SIGNATURE_RESPONSE', error)
        })
    }
}

export const getters = {
    getUser: (state) => {
        return state.user
    },
    getTwoFactorLogin: (state) => {
        return state.twoFactorLogin
    },
    getPutSignatureResponse: (state) => {
        return state.putSignatureResponse
    }
}
