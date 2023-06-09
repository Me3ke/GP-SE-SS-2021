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
    errorPutColorResponse: {},
    logos: {},
    errorLogoResponse: {},
    putLogosResponse: {},
    errorPutLogosResponse: {}
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
    // sets response for putting colors into database
    SET_PUT_COLORS_RESPONSE(state, res) {
        state.putColorResponse = res
    },
    // changes logos in localstorage
    CHANGE_LOGOS(state, logos) {
        state.logos = logos
    },
    // sets response for putting logos into database
    SET_PUT_LOGOS_RESPONSE(state, res) {
        state.putLogosResponse = res
    },

    // sets error of request
    SET_ERROR_FETCH_COLORS(state, error) {
        state.errorFetchColors = error
    },
    SET_ERROR_PUT_COLORS_RESPONSE(state, error) {
        state.errorPutColorResponse = error
    },
    SET_ERROR_CHANGE_LOGOS(state, error) {
        state.errorLogoResponse = error
    },
    SET_ERROR_PUT_LOGOS_RESPONSE(state, error) {
        state.errorPutLogosResponse = error
    }

}

export const actions = {
    /* General Theme */
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

    /* Colors */
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
    },

    /* Logos */
    // gives back logos
    getLogos({commit}) {
        return themeAPI.getLogos().then(response => {
            commit('CHANGE_LOGOS', response.data)
            commit('SET_ERROR_CHANGE_LOGOS', {})
        }).catch(error => {
            commit('SET_ERROR_CHANGE_LOGOS', error)
        })
    },
    // puts logos into database
    putLogos({commit}, {logo, logoType, dark}) {
        return themeAPI.putLogos(logo, logoType, dark).then(response => {
            commit('SET_PUT_LOGOS_RESPONSE', response.data)
            commit('SET_ERROR_PUT_LOGOS_RESPONSE', {})
        }).catch(error => {
            commit('SET_ERROR_PUT_LOGOS_RESPONSE', error)
        })
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
    },
    getLightLogo: (state) => {
        return state.logos.logo
    },
    getLightLogoType: (state) => {
        return state.logos.logoType
    },
    getDarkLogo: (state) => {
        return state.logos.logoDark
    },
    getDarkLogoType: (state) => {
        return state.logos.logoDarkType
    },
    getErrorPutLogoResponse: (state) => {
        return state.errorPutLogosResponse
    },
    getErrorPutColorResponse: (state) => {
        return state.errorPutColorResponse
    }
}
