import _ from "lodash";
import messagesAPI from "@/main/vue/api/messagesAPI";

export const namespaced = true

export const state = {
    messages: {},
    selectedMsg: {},
    messagesConfig: {},
    errorGetMessages: {},
    resDeleteMessage: {},
    errorGetMessagesConfig: {},
    resPutMessagesConfig: {},
    resPutMessageWatched: {}
}


export const mutations = {
    SET_MESSAGES(state, messages) {
        state.messages = messages.reverse()
    },
    CHANGE_SELECTED_MSG(state, msg) {
        state.selectedMsg = msg
    },
    SET_MESSAGES_CONFIG(state, config) {
        state.messagesConfig = config
    },
    /* Errors */
    SET_ERROR_GET_MESSAGES(state, error) {
        state.errorGetMessages = error
    },
    SET_ERROR_MESSAGES_CONFIG(state, error) {
        state.errorGetMessagesConfig = error
    },
    /* Responses */
    SET_RES_PUT_WATCHED(state, res) {
        state.resPutMessageWatched = res
    },
    SET_RES_PUT_MESSAGES_CONFIG(state, res) {
        state.resPutMessagesConfig = res
    },
    SET_RES_DELETE_MESSAGE(state, res) {
        state.resDeleteMessage = res
    }
}


export const actions = {
    fetchMessages({commit}) {
        return messagesAPI.getMessages().then(response => {
            commit('SET_MESSAGES', response.data)
            commit('SET_ERROR_GET_MESSAGES', {})
        }).catch(error => {
            commit('SET_ERROR_GET_MESSAGES', error)
        })
    },
    fetchMessagesConfig({commit}) {
        return messagesAPI.getMessagesConfig().then(response => {
            commit('SET_MESSAGES_CONFIG', response.data)
            commit('SET_ERROR_MESSAGES_CONFIG', {})
        }).catch(error => {
            commit('SET_ERROR_MESSAGES_CONFIG', error)
        })
    },
    patchChangeMessageSettings({commit}, settings) {
        return messagesAPI.putMessagesConfig(settings).then(response => {
            commit('SET_RES_PUT_MESSAGES_CONFIG', response.data)
        }).catch(error => {
            commit('SET_RES_PUT_MESSAGES_CONFIG', error)
        })
    },
    patchChangeSelectedMsg({state, commit}, msg) {
        if (state.selectedMsg !== msg) {
            commit('CHANGE_SELECTED_MSG', msg)
        }
    },
    patchChangeWatchedStatus({state, commit}) {
        if (!_.isEmpty(state.selectedMsg) && state.selectedMsg.watched !== true) {
            return messagesAPI.updateWatched(state.selectedMsg.messageID).then(response => {
                commit('SET_RES_PUT_WATCHED', response.data)
            }).catch(error => {
                commit('SET_RES_PUT_WATCHED', error)
            })
        }
    },
    patchDeleteMessage({state, commit}) {
        return messagesAPI.deleteMessage(state.selectedMsg.messageID).then(response => {
            commit('SET_RES_DELETE_MESSAGE', response.data)
        }).catch(error => {
            commit('SET_RES_DELETE_MESSAGE', error)
        })
    }
}


export const getters = {
    unwatchedCount: (state) => {
        return state.messages.filter(msg => msg.watched === false).length
    },
    getMessages: (state) => {
        return state.messages
    },
    getSelectedMessage: (state) => {
        return state.selectedMsg
    },
    getFirstNMessages: (state) => (n) => {
        return state.messages.slice(0, n)
    },
    isSelected: (state) => (id) => {
        return state.selectedMsg.messageID === id
    },
    getConfig: (state) => {
        return state.messagesConfig
    },
    getDeleteResponse: (state) => {
        return state.resDeleteMessage
    }
}
