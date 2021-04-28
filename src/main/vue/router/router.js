import VueRouter from "vue-router";
import i18n from "@/i18n";
import TestPage from "@/main/vue/views/TestPage";

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: `/${i18n.locale}`
        },
        {
            path: '/:lang',
            component: {
                render(c) {
                    return c('router-view')
                }
            },
            children: [
                {
                    path: 'test-page',
                    name: 'TestPage',
                    component: TestPage
                }]
        }
    ]
})

router.beforeEach((to, from, next) => {
    // using language from route guard or default language (de)
    let language = to.params.lang;
    if (!language) {
        language = 'de'
    }
    // setting current language
    i18n.locale = language
    next()
})

export default router
