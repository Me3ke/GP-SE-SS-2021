import api from "@/main/vue/api";

export const state = {
    userData: {},
    userById: []
}

export const mutations = {
    SET_USER_DATA(state, userData) {
        state.userData = userData
    },
    SET_USER_BY_ID(state, user) {
        state.userById.push(user)
    },
    CLEAR_USER_BY_ID(state) {
        state.userById = []
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
    fetchUserDataById({commit, state}, id) {
        let doBreak = false
        for (let i = 0; i < state.userById.length; i++) {
            if (state.userById[i].id === id) {
                doBreak = true
                break
            }
        }
        if (!doBreak) {
            return api.userData.getMyUserDataById(id).then(response => {
                let data = response.data
                let user = {id, data}
                commit('SET_USER_BY_ID', user)
            }).catch(error => {
                console.log(error)
            })
        }
    },
    clearUserDataById({commit}) {
        commit('CLEAR_USER_BY_ID')
    }
}

export const getters = {
    getUserData: (state) => {
        return state.userData
    },
    getUserByID: (state) => (email) => {
        let resUser
        resUser = state.userById.filter(user =>
            user.id.toLowerCase().includes(email.toLowerCase()))
        return resUser
    }
}
