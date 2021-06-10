import twoFakAuthAPI from "@/main/vue/api/twoFakAuthAPI";

export const namespaced = true

export const state = {
    hasSetUp: Boolean,
    errorHasSetUp: {},
    qrCode: {},
    errorGetQrCode: {},
    correctInput: Boolean,
    errorCorrectInput: {}
}

export const mutations = {
    SET_HAS_SET_UP(state, hasSetUp) {
        state.hasSetUp = hasSetUp
    },

    SET_ERROR_HAS_SET_UP(state, error) {
        state.errorHasSetUphasSetUp = error
    },

    SET_QR_CODE(state, qrCode) {
        state.qrCode = qrCode
    },

    SET_ERROR_GET_QR_CODE(state, error) {
        state.errorGetQrCode = error
    },

    SET_CORRECT_INPUT(state, correct) {
        state.correctInput = correct
    },

    SET_ERROR_CORRECT_INPUT(state, error) {
        state.errorCorrectInput = error
    }
}

export const actions = {
    // makes axios call to get information if user has already set up two fak auth, either sets hasSetUp (success) or error (error)
    fetchHasSetUp({commit}) {
        return twoFakAuthAPI.getHasSetUp().then(response => {
            commit('SET_HAS_SET_UP', response.data)
            commit('SET_ERROR_HAS_SET_UP', {})

        }).catch(error => {
            commit('SET_ERROR_HAS_SET_UP', error)
        })
    },

    // makes axios call to get qr code, either sets qrCode (success) or error (error)
    fetchQrCode({commit}) {
        return twoFakAuthAPI.getQrCode().then(response => {
            commit('SET_QR_CODE', response.data)
            commit('SET_ERROR_GET_QR_CODE', {})
        }).catch(error => {
            commit('SET_ERROR_GET_QR_CODE', error)
        })
    },

    // makes axios call to check if qrCodeCode is correct
    validateCode({commit}, {code}) {
        return twoFakAuthAPI.postValidateCode(code).then(response => {
            commit('SET_CORRECT_INPUT', response.data)
            commit('SET_ERROR_CORRECT_INPUT', {})
        }).catch(error => {
            commit('SET_ERROR_CORRECT_INPUT', error)
        })
    }
}

export const getters = {
    getQrCode: (state) => {
        return state.qrCode
    },
    getHasSetUp: (state) => {
        return state.hasSetUp
    },
    getCorrectInput: (state) => {
        return state.correctInput
    },
    getErrorGetQrCode: (state) => {
        return state.errorGetQrCode
    },
    getErrorGetHasSetUp: (state) => {
        return state.errorHasSetUp
    },
    getErrorGetCorrectInput: (state) => {
        return state.errorCorrectInput
    }
}
