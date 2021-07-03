import UserManagementAPI from "@/main/vue/api/UserManagementAPI";

export const namespaced = true

export const state = {
    allUsers: [],
    userValidateStatus: {},
    userMakeAdminStatus: {},
    lockUserStatus: {},
    userManagementError: {}
}


export const mutations = {
    FETCH_ALL_USERS(state, users) {
        state.allUsers = users
    },

    SET_USER_VALIDATE(state, progress) {
        state.userValidateStatus = progress
    },

    SET_USER_LOCK(state, progress) {
        state.lockUserStatus = progress
    },

    SET_USER_MAKE_ADMIN(state, progress) {
        state.userMakeAdminStatus = progress
    },

    SET_USER_MANAGEMENT_ERROR(state, error) {
        state.userManagementError = error
    }
}


export const actions = {


    // Example await this.$store.dispatch('fetchAllUsers') not 'userManage/fetchAllUsers'

    fetchAllUsers({commit}) {
        return UserManagementAPI.getAllUser().then(response => {
            commit('FETCH_ALL_USERS', response.data)
            commit('SET_USER_MANAGEMENT_ERROR', {})
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    },

    makeUserAdmin({commit}, userId) {
        return UserManagementAPI.makeAdmin(userId).then(response => {
                commit('SET_USER_MAKE_ADMIN', response.data)
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    },

    validateUser({commit}, userId) {
        return UserManagementAPI.validateUser(userId).then(response => {
            commit('SET_USER_VALIDATE', response.data)
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    },

    lockUser({commit}, userId) {
        return UserManagementAPI.lockUser(userId).then(response => {
            commit('SET_USER_LOCK', response.data)
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    }
}


export const getters = {
    getAllUsers: (state) => {
        return state.allUsers.allUsers
    },

    getMakeAdminStatus: (state) => {
        return state.userMakeAdminStatus
    },

    getValidationStatus: (state) => {
      return state.userValidateStatus
    },

    getLockedUserStatus: (state) => {
        return state.lockUserStatus
    },

    userManagementError: (state) => {
        return state.userManagementError
    }
}
