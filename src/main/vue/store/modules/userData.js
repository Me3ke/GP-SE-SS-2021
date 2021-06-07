import api from "@/main/vue/api";

export const state = {
    userData: {}
}

export const mutations = {
    SET_USER_DATA(state, userData) {
        state.userData = userData
    }
}

export const actions = {
    fetchUserData({commit}) {
        api.userData.getMyUserData().then(response => {
            commit('SET_USER_DATA', response.data)
        }).catch(error => {
            console.log(error)
        })
    },
}

export const getters = {
    getUserData: (state) => {
        return state.userData
    }
}