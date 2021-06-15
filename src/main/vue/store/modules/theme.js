export const namespaced = true

export const state = {
    modeInitialized: false,
    theme: {
        theme: ''
    },
    sheet: '',
    initialLoad: false
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
    }
}
