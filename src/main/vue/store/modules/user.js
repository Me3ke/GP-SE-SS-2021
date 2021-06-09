import api from "@/main/vue/api";

export const state = {
    user: {},
    firstLoginChange: {}
}

export const mutations = {
    SET_USER(state, user) {
        state.user = user
    },
    SET_FIRST_LOGIN_CHANGE(state, login) {
        state.firstLoginChange = login
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
    }
}

export const getters = {
    getUser: (state) => {
        return state.user
    },
}
