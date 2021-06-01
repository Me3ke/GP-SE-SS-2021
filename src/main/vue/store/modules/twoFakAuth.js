import twoFakAuthAPI from "@/main/vue/api/twoFakAuthAPI";

export const namespaced = true

export const state = {
    hasSetUp: Boolean,
    errorHasSetUp:{},
    qrCode: {},
    errorGetQrCode: {},
    correctInput: Boolean,
    errorCorrectInput:{}
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

    SET_ERROR_CORRECT_INPUT(state, error){
        state.errorCorrectInput = error
    }
}

export const actions = {
    // makes axios call to get information if user has already set up two fak auth, either sets hasSetUp (success) or error (error)
    fetchHasSetUp({commit}) {
        twoFakAuthAPI.getHasSetUp().then(response => {
            commit('SET_HAS_SET_UP', response.data)
        }).catch(error => {
            commit('SET_ERROR_HAS_SET_UP', error)
        })
    },

    // makes axios call to get qr code, either sets qrCode (success) or error (error)
    fetchQrCode({commit}) {
        twoFakAuthAPI.getQrCode().then(response => {
            commit('SET_QR_CODE', response.data)
        }).catch(error => {
            commit('SET_ERROR_GET_QR_CODE', error)
        })
    },

    // makes axios call to check if qrCodeCode is correct
    validateCode({commit}, code){
        twoFakAuthAPI.postValidateCode(code).then(response => {
            commit('SET_CORRECT_INPUT', response.data)
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
    }
}
