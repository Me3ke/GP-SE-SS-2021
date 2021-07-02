import UserManagementAPI from "@/main/vue/api/UserManagementAPI";


export const state = {
    allUsers: [],
    userManagementError: {}
}

export const mutations = {
    FETCH_ALL_USERS(state, users) {
        state.allUsers = users
    },

    SET_USER_MANAGEMENT_ERROR(state, error) {
        state.userManagementError = error
    }
}


export const actions = {

    fetchAllUsers({commit}) {
        return UserManagementAPI.getAllUser().then(response => {
            commit('FETCH_ALL_USERS', response.data)
            commit('SET_USER_MANAGEMENT_ERROR', {})
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    }
}


export const getters = {
    getUsers: (state) => {
        return state.allUsers
    },
    userManagementError: (state) => {
        return state.userManagementError
    }
}
