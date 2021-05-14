import Vue from "vue";
import Vuex from "vuex";

import * as messages from './modules/messages.js';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        messages
    }
})
