import Vue from 'vue'
import Router from 'vue-router'

import Main from '../components/pages/Main.vue'
import User from '../components/pages/User.vue'
import NotFound from '../components/pages/404.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            component: Main,
            //redirect: '/',
        },
        {
            path: '/user',
            component: User
        },
        {
            path: "*",
            component: NotFound
        }
    ],
    mode: 'history'
})
