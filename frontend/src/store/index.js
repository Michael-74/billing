import Vue from 'vue'
import Vuex from 'vuex'
import client from './client'
import user from './user'
import preset from './preset'
import internet from './internet'
import tv from './tv'
import task from './task'
import error from './error'


Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        client,
        user,
        preset,
        internet,
        tv,
        task,
        error
    }
})
