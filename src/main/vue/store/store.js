import Vue from "vue";
import Vuex from "vuex";

import * as messages from './modules/messages.js';
import * as envelopes from './modules/envelopes.js';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        messages,
        envelopes
    }
})

