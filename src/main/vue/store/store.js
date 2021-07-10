import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";


import * as messages from './modules/messages.js';
import * as envelopes from './modules/envelopes.js';
import * as document from './modules/document.js';
import * as user from './modules/user';
import * as userData from './modules/userData';
import * as keypair from './modules/keypair';
import * as theme from './modules/theme';
import * as twoFakAuth from './modules/twoFakAuth'
import authorization from "@/main/vue/store/modules/authorization";
import * as documentUpload from './modules/documentUpload.js';
import * as documentSettings from './modules/documentSettings.js';
import * as userManagement from './modules/userManage'
import * as comments from "./modules/comments";
import guestAuthorization from "@/main/vue/store/modules/guestAuthorization";

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        messages,
        envelopes,
        document,
        user,
        documentUpload,
        userData,
        keypair,
        theme,
        twoFakAuth,
        auth: authorization,
        documentSettings,
        comments,
        userManagement,
        guestAuth: guestAuthorization
    },
    mutations: {
        INITIALIZE_STORE(state) {
            if (localStorage.getItem('store')) {
                this.replaceState(Object.assign(state, JSON.parse(localStorage.getItem('store'))))
            } else {
                state.auth.authenticated = null;
                state.auth.username = null;
                state.auth.token = null
                state.auth.role = null;
            }
        }
    }
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers['Authorization'] = state.token
});

export default store
