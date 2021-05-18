import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

import * as messages from './modules/messages.js';
import * as envelopes from './modules/envelopes.js';
import api from '../api'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        messages,
        envelopes
    },
    state: {
        authenticated: null,
        token: null,
        username: null
    },
    mutations: {
        authenticate(state, token) { //<2>
            if (token !== null) {
                this.state.token = token
                this.state.authenticated = true
                axios.defaults.headers['Authorization'] = token
            } else {
                this.state.authenticated = false
            }
        },
        initializeStore(state) {
            if (localStorage.getItem('store')) {
                this.replaceState(Object.assign(state, JSON.parse(localStorage.getItem('store'))))
            } else {
                state.authenticated = null
                state.token = null
                state.username = null
            }
        }
    },
    actions: {
        requestToken({commit}, credentials) { //<4>
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password).then(res => {
                    this.state.authenticated = true
                    let token = res.headers.authorization
                    commit('authenticate', token)
                    console.log(res.headers)
                    resolve()
                }).catch(() => {
                    commit('authenticate', null)
                    reject()
                })
            })
        }
    },
    getters: {
        isAuthenticated() {
            return this.state.authenticated;
        }
    }
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers['Authorization'] = state.token
});


export default store

