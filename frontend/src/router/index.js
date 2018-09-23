import Vue from 'vue'
import Router from 'vue-router'

import Main from '../components/pages/Main.vue'
import User from '../components/pages/User.vue'

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
        }
    ],
    mode: 'history'
})
