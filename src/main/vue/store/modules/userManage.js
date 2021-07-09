import UserManagementAPI from "@/main/vue/api/UserManagementAPI";
import {filterUser, sortUser} from "@/main/vue/scripts/filterSortUser";

export const namespaced = true

export const state = {
    allUsers: [],
    userValidateStatus: {},
    userMakeAdminStatus: {},
    lockUserStatus: {},
    seeUserStatus: {},
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

    SET_USER_SEE(state, progress) {
        state.seeUserStatus = progress
    },

    SET_USER_MANAGEMENT_ERROR(state, error) {
        state.userManagementError = error
    },

    EMPTY_EVERYTHING(state) {
        state.allUsers = []
        state.userValidateStatus = {}
        state.userMakeAdminStatus = {}
        state.lockUserStatus = {}
        state.seeUserStatus = {}
        state.userManagementError = {}
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
    },

    seeUser({commit}, userId) {
        return UserManagementAPI.seeUser(userId).then(response => {
            commit('SET_USER_SEE', response.data)
        }).catch(error => {
            commit('SET_USER_MANAGEMENT_ERROR', error)
        })
    },

    // empties store part
    emptyStore({commit}) {
        commit('EMPTY_EVERYTHING')
    }
}


export const getters = {
    getAllUsers: (state) => {
        return state.allUsers.allUsers
    },

    getFilteredPagesUsers: (state) => (filters, pageLimit, page) => {
        //filter
        let filteredUsers = filterUser(state.allUsers.allUsers, filters);

        //sorting
        let sortedUsers = sortUser(filteredUsers, filters);

        //paging
        return sortedUsers.slice((page - 1) * pageLimit, page * pageLimit)
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
