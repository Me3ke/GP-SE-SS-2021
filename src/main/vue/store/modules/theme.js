export const namespaced = true

export const state = {
    modeInitialized: false,
    theme: {
        theme: ''
    }
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
        console.log(theme)
        state.theme = theme
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
    }
}


export const getters = {
    getTheme: (state) => {
        return state.theme.theme
    }
}
