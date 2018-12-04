import Vue from 'vue'
import Router from 'vue-router'

import '../assets/scss/main.scss'

import Main from '../components/pages/Main.vue'
import UserIndex from '../components/pages/user/Index.vue'
import UserShow from '../components/pages/user/Show.vue'
import NotFound from '../components/pages/404.vue'
import AccessDenied from '../components/pages/403.vue'
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
            meta: {isRequiresAuth: false}
        },
        {
            path: '/login',
            component: Login,
            meta: {isRequiresAuth: false}
        },
        {
            path: '/user',
            component: UserIndex,
            meta: {isRequiresAuth: true}
        },
        {
            path: '/user/:id',
            props: true,
            component: UserShow,
            meta: {isRequiresAuth: false}
        },
        {
            path: "*",
            component: NotFound,
            meta: {isRequiresAuth: false}
        },
        {
            path: "/403",
            component: AccessDenied,
            meta: {isRequiresAuth: false}
        },
        {
            path: "/admin",
            component: Admin,
            redirect: '/admin/clients',
            meta: {isRequiresAuth: true},
            children: [
                {
                    path: 'clients',
                    component: Clients,
                    name: 'Clients',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'internet',
                    component: Internet,
                    name: 'Internet',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'tv',
                    component: Tv,
                    name: 'Tv',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'rent',
                    component: Rent,
                    name: 'Rent',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'logs',
                    component: Logs,
                    name: 'Logs',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'mail',
                    component: Mail,
                    name: 'Mail',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'service',
                    component: Service,
                    name: 'Service',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'settings',
                    component: Settings,
                    name: 'Settings',
                    meta: {isRequiresAuth: true}
                },
                {
                    path: 'wallet',
                    component: Wallet,
                    name: 'Wallet',
                    meta: {isRequiresAuth: true}
                }
            ]
        }
    ],
    mode: 'history'
});

router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.isRequiresAuth)
    const isAuthenticated = localStorage.getItem("JWT")

    if(!requiresAuth) {
        next();
    } else {
        if(isAuthenticated) {
            store.commit('changeAuth', {token: localStorage.getItem("JWT"), username: localStorage.getItem("username")})
            next()
        } else {
            router.push('/login')
        }
    }
});

export default router;
