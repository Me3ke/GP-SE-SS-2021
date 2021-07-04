import Vue from 'vue'
import VueRouter from "vue-router";
import i18n from "@/i18n";
import NotFoundPage from "@/main/vue/views/NotFoundPage";
import NoConnectionPage from "@/main/vue/views/NoConnectionPage";
import UserGuide from "@/main/vue/views/UserGuide";
import OverviewPage from "@/main/vue/views/OverviewPage";
import LoginPage from "@/main/vue/views/LoginPage";
import RegisterPage from "../views/RegisterPage";
import MessagePage from "@/main/vue/views/MessagePage";
import LandingPage from "@/main/vue/views/LandingPage";
import UserPage from "@/main/vue/views/UserPage";
import DocumentPage from "@/main/vue/views/DocumentPage";
import ImpressumPage from "@/main/vue/views/ImpressumPage";
import EnvelopePage from "@/main/vue/views/EnvelopePage";
import EnvelopeSettingsPage from "@/main/vue/views/EnvelopeSettingsPage";
import store from "@/main/vue/store/store";
import ProtocolPage from "@/main/vue/views/ProtocolPage";
import RegisterConfirmPage from "@/main/vue/views/RegisterConfirmPage";
import CorporateDesignNormalPage from "@/main/vue/views/CorporateDesignNormalPage";
import CorporateDesignExperimentalPage from "@/main/vue/views/CorporateDesignExperimentalPage";
import CommentsPage from "@/main/vue/views/CommentsPage";
import GuestDocumentPage from "@/main/vue/views/GuestDocumentPage";
import UserManagement from "@/main/vue/views/UserManagement";
import HistoryPage from "@/main/vue/views/HistoryPage";


Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: `/${i18n.locale}/landing`
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
                    component: UserPage,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'landing',
                    name: 'landing',
                    component: LandingPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'overview',
                    name: 'overview',
                    component: OverviewPage,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'login',
                    name: 'login',
                    component: LoginPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'register',
                    name: 'register',
                    component: RegisterPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'register/confirm/:id',
                    name: 'register/confirm',
                    component: RegisterConfirmPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'messages',
                    name: 'messages',
                    props: true,
                    component: MessagePage,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'envelope/:envId/document/:docId',
                    name: 'document',
                    component: DocumentPage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'envelope/:envId/document/:docId/:tokenId',
                    name: 'guestDocument',
                    component: GuestDocumentPage,
                    props: true,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'envelope/:envId/document/:docId/protocol',
                    name: 'protocol',
                    component: ProtocolPage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'envelope/:envId/document/:docId/comments',
                    name: 'comments',
                    component: CommentsPage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'envelope/:envId/document/:docId/history',
                    name: 'history',
                    component: HistoryPage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: '404',
                    name: '404',
                    component: NotFoundPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'no-connection',
                    name: 'no-connection',
                    component: NoConnectionPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    // url address h for help/hilfe
                    path: 'help',
                    name: 'help',
                    component: UserGuide,
                    meta: {
                        requiresAuth: true
                    }
                },
                {

                    path: 'impressum',
                    name: 'impressum',
                    component: ImpressumPage,
                    meta: {
                        guest: true
                    }
                },
                {
                    path: 'envelope/:envId',
                    name: 'envelope',
                    component: EnvelopePage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'settings/:envId',
                    name: 'settings',
                    component: EnvelopeSettingsPage,
                    props: true,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'adminSettings/corporate',
                    name: 'corporate',
                    component: CorporateDesignNormalPage,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'adminSettings/corporate/experimental',
                    name: 'corporateExp',
                    component: CorporateDesignExperimentalPage,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: 'adminSettings/userManagement',
                    name: 'userManagement',
                    component: UserManagement,
                    meta: {
                        requiresAuth: true
                    }
                },
                {
                    path: '*',
                    redirect: {name: '404'}
                }]
        }
    ]
})

router.beforeEach((to, from, next) => {
    store.commit('INITIALIZE_STORE')

    // using language from route guard or default language (de)
    let language = to.params.lang;
    if (!language) {
        language = 'de'
    }
    // setting current language
    i18n.locale = language

    if (store.state.auth.exp < Date.now() / 1000) {
        localStorage.removeItem('store')
        localStorage.clear()
        next({
            path: '/' + language + '/login',
            params: {nextUrl: to.fullPath}
        })
    }

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (store.state.auth.authenticated !== true) {
            next({
                path: '/' + language + '/login',
                params: {nextUrl: to.fullPath}
            })
        }
    }
    if (to.matched.some(record => record.name === 'login' || record.name === 'landing')) {
        if (store.state.auth.authenticated === true) {
            next({
                path: '/' + language + '/overview',
                params: {nextUrl: to.fullPath}
            })
        }
    }
    next()
})

export default router
