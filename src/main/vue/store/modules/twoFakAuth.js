import twoFakAuthAPI from "@/main/vue/api/twoFakAuthAPI";

export const namespaced = true

export const state = {
    qrCode: {},
    errorGetQrCode: {}
}

export const mutations = {

    SET_QR_CODE(state, qrCode) {
        state.qrCode = qrCode
    },

    SET_ERROR_GET_QR_CODE(state, error) {
        state.errorGetQrCode = error
    }
}

export const actions = {
    // makes axios call to get document, either sets document (success) or error (error)
    fetchQrCode({commit}) {
        twoFakAuthAPI.getQrCode().then(response => {
            commit('SET_QR_CODE', response.data)
            commit('SET_ERROR_GET_QR_CODE', {})
        }).catch(error => {
            commit('SET_ERROR_GET_QR_CODE', error)
        })
    }
}

export const getters = {
    getQrCode: (state) => {
        return state.qrCode
    },
    getErrorGetQrCode: (state) => {
        return state.errorGetQrCode
    }

}