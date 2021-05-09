import Vuex from "vuex";
import axios from "axios";

const store = new Vuex.Store({ //<1>
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
                this.parent.api.auth.login(credentials.username, credentials.password).then(res => {
                    let token = res.headers.authorization
                    commit('authenticate', token)
                    resolve()
                }).catch(() => {
                    commit('authenticate', null)
                    reject()
                })
            })
        },
    },
})

store.subscribe((mutation, state) => { //<5>
    localStorage.setItem('store', JSON.stringify(state)); //<6>
    axios.defaults.headers['Authorization'] = state.token //<7>
});

export default store //<8>
