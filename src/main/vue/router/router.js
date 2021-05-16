import VueRouter from "vue-router";
import i18n from "@/i18n";
import NotFoundPage from "@/main/vue/views/NotFoundPage";
import NoConnectionPage from "@/main/vue/views/NoConnectionPage";
import OverviewPage from "@/main/vue/views/OverviewPage";
import LoginPage from "@/main/vue/views/LoginPage";
import MessagePage from "@/main/vue/views/MessagePage";
import LandingPage from "@/main/vue/views/LandingPage";
import UserPage from "@/main/vue/views/UserPage";
import DocumentPage from "@/main/vue/views/DocumentPage";
import ImpressumPage from "@/main/vue/views/ImpressumPage";
import EnvelopePage from "@/main/vue/views/EnvelopePage";

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
                    path: 'user',
                    name: 'user',
                    component: UserPage
                },
                {
                    path: 'landing',
                    name: 'landing',
                    component: LandingPage
                },
                {
                    path: 'overview',
                    name: 'overview',
                    component: OverviewPage
                },
                {
                    path: 'login',
                    name: 'login',
                    component: LoginPage
                },
                {
                    path: 'messages',
                    name: 'messages',
                    props: true,
                    component: MessagePage
                },
                {
                    path: 'document/:docId',
                    name: 'document',
                    component: DocumentPage,
                    props: true
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
                    path: 'impressum',
                    name: 'impressum',
                    component: ImpressumPage
                },
                {
                    path: 'envelope/:envId',
                    name: 'envelope',
                    component: EnvelopePage,
                    props: true
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
