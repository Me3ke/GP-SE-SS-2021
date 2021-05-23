import axios from "axios";
import api from "@/main/vue/api";

const authorization = {
    state: () => ({
        authenticated: null,
        token: null,
        username: null,
        role: null
    }),
    mutations: {
        AUTHENTICATE(state, token) {
            if (token !== null) {
                state.token = token
                state.authenticated = true
                axios.defaults.headers['Authorization'] = token
                let base64Url = token.split('.')[1];
                let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
                let jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
                    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                }).join(''));
                console.log(jsonPayload);
                let payload = JSON.parse(jsonPayload);
                state.username = payload.sub;
                state.role = payload.rol;
            } else {
                state.authenticated = false
            }
        }
    },
    actions: {
        requestToken({commit}, credentials) { //<4>
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password).then(res => {
                    let token = res.headers.authorization
                    commit('AUTHENTICATE', token)
                    resolve()
                }).catch(() => {
                    commit('AUTHENTICATE', null)
                    reject()
                })
            })
        }
    },
    getters: {
        isAuthenticated(state) {
            return state.authenticated;
        },
        getToken(state) {
            return state.token;
        },
        isAdmin(state) {
            return state.role.includes('ROLE_ADMIN')
        }
    }

}

export default authorization
