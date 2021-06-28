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
    /* Errors */
    SET_ERROR_GET_MESSAGES(state, error) {
        state.errorGetMessages = error
    },
    /* Responses */
    SET_RES_PUT_WATCHED(state, res) {
        state.resPutMessageWatched = res
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
    patchChangeSelectedMsg({state, commit}, msg) {
        if (state.selectedMsg !== msg) {
            commit('CHANGE_SELECTED_MSG', msg)
        }
    },
    patchChangeWatchedStatus({state, commit}) {
        if (!_.isEmpty(state.selectedMsg) && state.selectedMsg.watched !== true) {
            messagesAPI.updateWatched(state.selectedMsg.messageID).then(response => {
                commit('SET_RES_PUT_WATCHED', response.data)
            }).catch(error => {
                commit('SET_RES_PUT_WATCHED', error)
            })

        }
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
    }
}
