import Vue from 'vue'
import App from './App.vue'
// Importing dependencies
import VueRouter from "vue-router";
import Vuex from "vuex";
import i18n from "@/i18n";
import {BootstrapVue} from "bootstrap-vue";
import store from "@/main/vue/store/store";
import router from "@/main/vue/router/router";
// Importing global components and styles
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import BaseIcon from "@/main/vue/components/BaseIcon";
import BaseHeading from "@/main/vue/components/BaseHeading";
import "@/main/vue/assets/css/global.css";

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.use(Vuex)

Vue.component('BaseIcon', BaseIcon)
Vue.component('BaseHeading', BaseHeading)

new Vue({
    render: h => h(App),
    router,
    store,
    i18n
}).$mount('#app')
