import axios from "axios";

export default {
    state: {


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
    }
}
