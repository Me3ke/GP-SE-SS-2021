import Vue from "vue";
import Vuex from "vuex";
//import api from "../api";

import * as messages from './modules/messages.js';
import * as envelopes from './modules/envelopes.js';

Vue.use(Vuex)

export default new Vuex.Store({
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

