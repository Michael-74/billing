import Vue from 'vue'
import Router from 'vue-router'

import '../assets/scss/main.scss'

import Main from '../components/pages/Main.vue'
import UserIndex from '../components/pages/user/Index.vue'
import UserShow from '../components/pages/user/Show.vue'
import NotFound from '../components/pages/404.vue'
import Login from '../components/pages/Login.vue'
import Clients from '../components/pages/admin/pages/clients/Clients.vue'
import Admin from '../components/pages/admin/Admin.vue'


Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            component: Main,
            redirect: '/login',
        },
        {
            path: '/login',
            component: Login
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
                    name: 'Clients'
                }
            ]
        }
    ],
    mode: 'history'
})
