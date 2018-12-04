import Vue from 'vue'
import Router from 'vue-router'

import '../assets/scss/main.scss'

import Main from '../components/pages/Main.vue'
import UserIndex from '../components/pages/user/Index.vue'
import UserShow from '../components/pages/user/Show.vue'
import NotFound from '../components/pages/404.vue'
import Login from '../components/pages/Login.vue'
import Clients from '../components/pages/admin/pages/clients/Index.vue'
import Internet from '../components/pages/admin/pages/internet/Index.vue'
import Tv from '../components/pages/admin/pages/tv/Index.vue'
import Rent from '../components/pages/admin/pages/rent/Index.vue'
import Logs from '../components/pages/admin/pages/logs/Index.vue'
import Mail from '../components/pages/admin/pages/mail/Index.vue'
import Settings from '../components/pages/admin/pages/settings/Index.vue'
import Wallet from '../components/pages/admin/pages/wallet/Index.vue'
import Service from '../components/pages/admin/pages/service/Index.vue'
import Admin from '../components/pages/admin/Admin.vue'
import axios from "axios";
import store from '../store';

Vue.use(Router)

const router = new Router({
    routes: [
        {
            path: '/',
            component: Main,
            redirect: '/login',
        },
        {
            path: '/login',
            component: Login,
            meta: {loginPage: true, nonRequiresAuth: true}
        },
        {
            path: '/user',
            component: UserIndex
        },
        {
            path: '/user/:id',
            props: true,
            component: UserShow
        },
        {
            path: "*",
            component: NotFound
        },
        {
            path: "/admin",
            component: Admin,
            redirect: '/admin/clients',
            children: [
                {
                    path: 'clients',
                    component: Clients,
                    name: 'Clients',
                    meta: {nonRequiresAuth: true}
                },
                {
                    path: 'internet',
                    component: Internet,
                    name: 'Internet'
                },
                {
                    path: 'tv',
                    component: Tv,
                    name: 'Tv'
                },
                {
                    path: 'rent',
                    component: Rent,
                    name: 'Rent'
                },
                {
                    path: 'logs',
                    component: Logs,
                    name: 'Logs'
                },
                {
                    path: 'mail',
                    component: Mail,
                    name: 'Mail'
                },
                {
                    path: 'service',
                    component: Service,
                    name: 'Service'
                },
                {
                    path: 'settings',
                    component: Settings,
                    name: 'Settings'
                },
                {
                    path: 'wallet',
                    component: Wallet,
                    name: 'Wallet'
                }
            ]
        }
    ],
    mode: 'history'
});

router.beforeEach((to, from, next) => {
    const requiresAuth = !to.matched.some(record => record.meta.nonRequiresAuth)
    const isLoginPage = to.matched.some(record => record.meta.loginPage)
    const isAuthenticated = localStorage.getItem("JWT")
    if (requiresAuth && !isAuthenticated) {
        next('/login')
    } else if (isLoginPage && isAuthenticated) {
        store.commit('changeAuth', {token: localStorage.getItem("JWT"), username: localStorage.getItem("username")})
        router.push('/admin/clients')
    }
    next()
})

export default router;
