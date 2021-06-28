import Vue from 'vue'
import App from './App.vue'
// Importing dependencies
import VueRouter from "vue-router";
import Vuex from "vuex";
import i18n from "@/i18n";
import {BootstrapVue, BootstrapVueIcons} from "bootstrap-vue";
import store from "@/main/vue/store/store";
import router from "@/main/vue/router/router";
import VueSweetalert2 from 'vue-sweetalert2';
// Importing global components and styles
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import BaseHeading from "@/main/vue/components/BaseHeading";
import "@/main/vue/assets/css/global.css";
import "@/main/vue/assets/css/quill.css"
import 'sweetalert2/dist/sweetalert2.min.css';
import Vuelidate from "vuelidate";
import VueQuill from 'vue-quill'


// Temp use
import VueQuillEditor from 'vue-quill-editor'

import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme

Vue.use(VueQuillEditor, /* { default global options } */)



Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VueSweetalert2);
Vue.use(Vuelidate);
Vue.use(VueQuill)

Vue.component('BaseHeading', BaseHeading)

new Vue({
    render: h => h(App),
    router,
    store,
    i18n
}).$mount('#app')
