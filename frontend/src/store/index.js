import Vue from 'vue'
import Vuex from 'vuex'
import client from './client'
import user from './user'
import preset from './preset'
import internet from './internet'


Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        client,
        user,
        preset,
        internet,
    }
})
