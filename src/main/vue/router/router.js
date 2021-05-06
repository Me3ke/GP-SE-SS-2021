import VueRouter from "vue-router";
import i18n from "@/i18n";
import TestPage from "@/main/vue/views/TestPage";
import NotFoundPage from "@/main/vue/views/NotFoundPage";
import NoConnectionPage from "@/main/vue/views/NoConnectionPage";
import UserGuide from "@/main/vue/views/UserGuide";

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
                    name: 'test-page',
                    component: TestPage
                },
                {
                    path: '404',
                    name: '404',
                    component: NotFoundPage
                },
                {
                    path: 'no-connection',
                    name: 'no-connection',
                    component: NoConnectionPage
                },
                {
                    // url address h for help/hilfe
                    path: 'h',
                    name: 'h',
                    component: UserGuide
                },
                {
                    path: '*',
                    redirect: {name: '404'}
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
