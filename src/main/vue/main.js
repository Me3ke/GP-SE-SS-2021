import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Vuex from "vuex";
import i18n from "@/i18n";
import {BootstrapVue} from "bootstrap-vue";
import store from "@/main/vue/store/store";
import router from "@/main/vue/router/router";

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import BaseIcon from "@/main/vue/components/BaseIcon";

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.use(Vuex)

Vue.component('BaseIcon', BaseIcon)

new Vue({
    render: h => h(App),
    router,
    store,
    i18n
}).$mount('#app')
