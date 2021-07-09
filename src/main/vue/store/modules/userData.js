import api from "@/main/vue/api";

export const state = {
    userData: {},
    userById: {}
}

export const mutations = {
    SET_USER_DATA(state, userData) {
        state.userData = userData
    },
    SET_USER_BY_ID(state, user) {
        state.userById = user
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
    fetchUserDataById({commit}, id) {
        api.userData.getMyUserDataById(id).then(response => {
            commit('SET_USER_BY_ID', response.data)
        }).catch(error => {
            console.log(error)
        })
    },
    clearUserDataById({commit}) {
        commit('SET_USER_BY_ID', {})
    }
}

export const getters = {
    getUserData: (state) => {
        return state.userData
    },
    getUserByID: (state) => {
        return state.userById
    }
}
