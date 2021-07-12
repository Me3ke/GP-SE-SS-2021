import addressBookAPI from "@/main/vue/api/addressBookAPI";
import {filterBook, sortBook} from "@/main/vue/scripts/filterSortAddressBook";
import _ from "lodash";

export const namespaced = true

export const state = {
    addressBook: {},
    bookSettings: {},
    resPutEntry: {},
    resPutSettings: {},
    resChangeEntry: {},
    resDeleteEntry: {},
    errorGetAddressBook: {},
    errorGetBookSettings: {},
    errorPutEntry: {},
    errorPutSettings: {},
    errorChangeEntry: {},
    errorDeleteEntry: {}
}

export const mutations = {
    SET_ADDRESS_BOOK(state, book) {
        state.addressBook = book
    },
    SET_BOOK_SETTINGS(state, settings) {
        state.bookSettings = settings
    },
    SET_RES_PUT_ENTRY(state, res) {
        state.resPutEntry = res
    },
    SET_RES_PUT_SETTINGS(state, res) {
        state.resPutSettings = res
    },
    SET_RES_CHANGE_ENTRY(state, res) {
        state.resChangeEntry = res
    },
    SET_RES_DELETE_ENTRY(state, res) {
        state.resDeleteEntry = res
    },

    SET_ERROR_ADDRESS_BOOK(state, error) {
        state.errorGetAddressBook = error
    },
    SET_ERROR_BOOK_SETTINGS(state, error) {
        state.errorGetBookSettings = error
    },
    SET_ERROR_PUT_ENTRY(state, error) {
        state.errorPutEntry = error
    },
    SET_ERROR_PUT_SETTINGS(state, error) {
        state.errorPutSettings = error
    },
    SET_ERROR_CHANGE_ENTRY(state, error) {
        state.errorChangeEntry = error
    },
    SET_ERROR_DELETE_ENTRY(state, error) {
        state.errorDeleteEntry = error
    },

    RESET_BOOK_STATE(state) {
        state.addressBook = {}
        state.bookSettings = {}
        state.resPutEntry = {}
        state.resPutSettings = {}
        state.resChangeEntry = {}
        state.resDeleteEntry = {}
        state.errorGetAddressBook = {}
        state.errorGetBookSettings = {}
        state.errorPutEntry = {}
        state.errorPutSettings = {}
        state.errorChangeEntry = {}
        state.errorDeleteEntry = {}
    }
}

export const actions = {
    // for resetting the state
    resetBookState({commit}) {
        commit('RESET_BOOK_STATE')
    },

    // makes axios call to get book
    fetchBook({commit}) {
        return addressBookAPI.getBook().then(response => {
            commit('SET_ADDRESS_BOOK', response.data)
            commit('SET_ERROR_ADDRESS_BOOK', {})
        }).catch(error => {
            commit('SET_ERROR_ADDRESS_BOOK', error)
        })
    },

    // adds entry to address book
    addEntry({commit}, newEntry) {
        return addressBookAPI.putEntry(newEntry).then(response => {
            commit('SET_RES_PUT_ENTRY', response.data)
            commit('SET_ERROR_PUT_ENTRY', {})
        }).catch(error => {
            commit('SET_ERROR_PUT_ENTRY', error)
        })
    },

    // changes entry of address book
    changeEntry({commit}, {entryId, newEntry}) {
        return addressBookAPI.changeEntry(entryId, newEntry).then(response => {
            commit('SET_RES_CHANGE_ENTRY', response.data)
            commit('SET_ERROR_CHANGE_ENTRY', {})
        }).catch(error => {
            commit('SET_ERROR_CHANGE_ENTRY', error)
        })
    },

    // deletes entry from address book
    deleteEntry({commit}, entryId) {
        return addressBookAPI.deleteEntry(entryId).then(response => {
            commit('SET_RES_DELETE_ENTRY', response.data)
            commit('SET_ERROR_DELETE_ENTRY', {})
        }).catch(error => {
            commit('SET_ERROR_DELETE_ENTRY', error)
        })
    },

    // makes axios call to get book settings
    fetchSettings({commit}) {
        return addressBookAPI.getBookSettings().then(response => {
            commit('SET_BOOK_SETTINGS', response.data)
            commit('SET_ERROR_BOOK_SETTINGS', {})
        }).catch(error => {
            commit('SET_ERROR_BOOK_SETTINGS', error)
        })
    },

    // changes book settings
    changeSettings({commit}, settings) {
        return addressBookAPI.postBookSettings(settings).then(response => {
            commit('SET_RES_PUT_SETTINGS', response.data)
            commit('SET_ERROR_PUT_SETTINGS', {})
        }).catch(error => {
            commit('SET_ERROR_PUT_SETTINGS', error)
        })
    }
}

export const getters = {
    getFilteredPagedBook: (state) => (filters, pageLimit, page) => {
        //filter
        let filteredBook = filterBook(state.addressBook, filters);

        //sorting
        let sortedBook = sortBook(filteredBook, filters);

        //paging
        return sortedBook.slice((page - 1) * pageLimit, page * pageLimit)
    },

    getSettings: (state) => {
        return state.bookSettings
    },

    getResPutEntry: (state) => {
        return state.resPutEntry
    },

    getResChangeEntry: (state) => {
        return state.resChangeEntry
    },

    getResDeleteEntry: (state) => {
        return state.resDeleteEntry
    },

    getResPutSettings: (state) => {
        return state.resPutSettings
    },

    getHasError: (state) => {
        return _.isEmpty(state.errorGetAddressBook)
            && _.isEmpty(state.errorGetBookSettings)
            && _.isEmpty(state.errorPutEntry)
            && _.isEmpty(state.errorPutSettings)
            && _.isEmpty(state.errorChangeEntry)
            && _.isEmpty(state.errorDeleteEntry)
    }
}
