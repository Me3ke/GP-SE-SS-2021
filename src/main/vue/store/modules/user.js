import api from "@/main/vue/api";

export const state = {
    user: {}
}

export const mutations = {
    SET_USER(state, user) {
        state.user = user
    },
}

export const actions = {
    fetchUser({commit}) {
        return api.user.getMyUser().then(response => {
            commit('SET_USER', response.data)
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
