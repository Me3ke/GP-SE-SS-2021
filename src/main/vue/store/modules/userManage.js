import UserManagementAPI from "@/main/vue/api/UserManagementAPI";


export const state = {
    allUsers: [],
    userValidate: {},
    userMakeAdmin: {},
    userManagementError: {}
}

export const mutations = {
    FETCH_ALL_USERS(state, users) {
        state.allUsers = users
    },

    SET_USER_VALIDATE(state, progress) {
        state.userValidate = progress
    },

    SET_USER_MAKE_ADMIN(state, progress) {
        state.userMakeAdmin = progress
    },

    SET_USER_MANAGEMENT_ERROR(state, error) {
        state.userManagementError = error
    }
}


export const actions = {

    fetchAllUsers({commit}) {
        return UserManagementAPI.getAllUser().then(response => {
            console.log(response.data)
            commit('FETCH_ALL_USERS', response.data)
            commit('SET_USER_MANAGEMENT_ERROR', {})
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    },

    makeUserAdmin({commit}, userId) {
        return UserManagementAPI.makeAdmin(userId).then(response => {
                console.log(response.data.status)
                commit('SET_USER_MAKE_ADMIN', response.data)
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    }
}


export const getters = {
    // returning the list
    getAllUsers: (state) => {
        return state.allUsers.allUsers
    },

    getMakeAdminStatus: (state) => {
        return state.userMakeAdmin
    },

    userManagementError: (state) => {
        return state.userManagementError
    }
}
