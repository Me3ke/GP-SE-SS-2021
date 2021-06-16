import themeAPI from "@/main/vue/api/themeAPI";

export const namespaced = true

export const state = {
    modeInitialized: false,
    theme: {
        theme: ''
    },
    sheet: '',
    initialLoad: false,
    colors: [],
    errorFetchColors: {},
    putColorResponse: {},
    errorPutColorResponse: {}
}

export const mutations = {
    //sets initial theme based on user preference
    INITIALIZE_THEME(state) {
        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            state.theme.theme = 'darkMode'
        }
        state.modeInitialized = true
    },
    //changes theme to given theme
    CHANGE_THEME(state, theme) {
        state.theme = theme
    },
    // sets stylesheet string, so it can be applied to dom after refresh
    CHANGE_STYLESHEET(state, sheet) {
        state.sheet = sheet
    },
    // changes initialLoad to indicate that stylesheet has been loaded after start of application
    CHANGE_INITIAL_LOAD(state, load) {
        state.initialLoad = load
    },
    // sets color-array that is in database
    SET_COLORS(state, colors) {
        state.colors = colors
    },
    // sets error of request
    SET_ERROR_FETCH_COLORS(state, error) {
        state.errorFetchColors = error
    },
    SET_PUT_COLORS_RESPONSE(state, res) {
        state.putColorResponse = res
    },
    SET_ERROR_PUT_COLORS_RESPONSE(state, error) {
        state.errorPutColorResponse = error
    }
}

export const actions = {
    setUpTheme({state, commit}) {
        if (!state.modeInitialized) {
            commit('INITIALIZE_THEME')
        }
    },
    setTheme({state, commit}, theme) {
        if (theme !== state.theme) {
            commit('CHANGE_THEME', theme)
        }
    },
    setStylesheet({commit}, sheet) {
        commit('CHANGE_STYLESHEET', sheet)
    },
    setInitialLoad({commit}, load) {
        commit('CHANGE_INITIAL_LOAD', load)
    },
    // fetches the initial colors from server
    fetchColors({commit}) {
        return themeAPI.getColors().then(response => {
            commit('SET_COLORS', response.data)
            commit('SET_ERROR_FETCH_COLORS', {})
        }).catch(error => {
            commit('SET_ERROR_FETCH_COLORS', error)
        })
    },
    // puts new colors into database
    putColors({commit}, colors) {
        return themeAPI.putColors(colors).then(response => {
            commit('SET_PUT_COLORS_RESPONSE', response.data)
            commit('SET_ERROR_PUT_COLORS_RESPONSE', {})
        }).catch(error => {
            commit('SET_ERROR_PUT_COLORS_RESPONSE', error)
        })
    },
    // resets colors in order to clean local storage
    resetColors({commit}) {
        commit('SET_COLORS', {})
        commit('SET_ERROR_FETCH_COLORS', {})
        commit('SET_PUT_COLORS_RESPONSE', {})
        commit('SET_ERROR_PUT_COLORS_RESPONSE', {})
    }
}


export const getters = {
    getTheme: (state) => {
        return state.theme.theme
    },
    getSheet: (state) => {
        return state.sheet
    },
    getInitialLoad: (state) => {
        return state.initialLoad
    },
    getColors: (state) => {
        return state.colors
    }
}
