import Vue from "vue";
import Vuex from "vuex";
//import api from "../api";
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
        user: {
            email: "sehrTolle@email.com",
            firstname: "Otto",
            lastname: "Wehner",
            street: "Siegwardsweg",
            houseNumber: 42,
            postcode: 55555,
            hometown: "Ownerhausen",
            country: "Deutschland",
            birthday: "30-04-2021",
            phoneNumber: "+49 93483932",
            publicKey: "z10f8dh736rz98712c6tz7r983t"
        },
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
        initializeStore(state) { //<3>
            if (localStorage.getItem('store')) {
                this.replaceState(Object.assign(state, JSON.parse(localStorage.getItem('store'))))
            }
        }
    },
    actions: {
        requestToken({commit}, credentials) { //<4>
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password).then(res => {
                    let token = res.headers.authorization
                    commit('authenticate', token)
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
    /*
    mutations: {
        setUser(state, user) { //<3>
            this.state.user = user
        },
    },
    actions: { //<4>
        requestUser({commit}, id) { //<5>
            return new Promise((resolve, reject) => {
                api.user.get(id).then(res => {
                    commit('setUser', res.data)
                    resolve()
                }).catch(() => {
                    commit('setUser', [])
                    reject()
                })
            })
        }
    }

     */
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers['Authorization'] = state.token
});


export default store

