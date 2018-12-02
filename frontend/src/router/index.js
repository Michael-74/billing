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

Vue.use(Router)

const hasToken = (to, from, next) => {
    console.log(123);
    const data = {
        'username': 'admin',
        'password': '123'
    };
    //this.$router.push('/admin/clients');
    const data1 = JSON.stringify(data);
    axios
        .post('/auth/v1/login', data1, {
            headers:{ Accept: 'application/json'}
        })
        .then(response => {
            console.log(response);
        })
        .catch(e => {
            console.log("error", e)
        });

    /*
    const token = localStorage.getItem('JWT')
    const username = localStorage.getItem('username')
    if (token) {
        store.commit(types.LOGIN_SUCCESS, { token, username })
        router.push('/home')
    } else {
        next()
    }
    */
}


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
        router.push('/admin/clients')
    }
    next()
})

export default router;
