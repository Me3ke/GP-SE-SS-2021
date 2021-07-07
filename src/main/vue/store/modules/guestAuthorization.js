
import guestAuthAPI from "../../api/guestAuthAPI";

const guestAuthorization = {
    state: () => ({
        guestSignatory: {},
        signSimpleResponse: {},
        reviewResponse: {}
    }),

    mutations: {
        SAVE_GUEST(state, res) {
            state.guestSignatory = res
        },

        PUT_SIGN_SIMPLE_RESPONSE(state, res) {
            state.signSimpleResponse = res
        },

        PUT_REVIEW_RESPONSE(state, res) {
            state.reviewResponse = res
        }

    },

    actions: {
        requestGuestInfo({commit}, {envId, docId, guestToken}) {
            return guestAuthAPI.guestValidate(envId, docId, guestToken).then(res => {
                commit('SAVE_GUEST', res.data)
            }).catch(error => {
                commit('SAVE_GUEST', error)
            })
        },

        signAsGuest({commit}, {envId, docId, guestToken}) {
            return guestAuthAPI.guestSimpleSign(envId, docId, guestToken).then(res => {
                commit('PUT_SIGN_SIMPLE_RESPONSE', res.data)
            }).catch(error => {
                commit('PUT_SIGN_SIMPLE_RESPONSE', error)
            })
        },

        reviewAsGuest({commit}, {envId, docId, guestToken}) {
            return guestAuthAPI.guestReview(envId, docId, guestToken).then(res => {
                commit('PUT_REVIEW_RESPONSE', res.data)
            }).catch(error => {
                commit('PUT_REVIEW_RESPONSE', error)
            })
        }
    },

    getters: {
        getGuestSignatory: (state) => {
            return state.guestSignatory
        },
        getSignSimpleResponse: (state) => {
            return state.signSimpleResponse
        },
        getReviewResponse: (state) => {
            return state.reviewResponse
        }
    }
}
export default guestAuthorization
