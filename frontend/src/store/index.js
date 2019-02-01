import Vue from 'vue'
import Vuex from 'vuex'
import client from './client'
import user from './user'
import preset from './preset'
import internet from './internet'
import tv from './tv'
import task from './task'
import rent from './rent'
import smotreshka from './smotreshka'
import mikrotik from './mikrotik'
import sms from './sms'
import email from './email'
import pack from './package'
import error from './error'


Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        client,
        user,
        preset,
        internet,
        tv,
        rent,
        task,
        pack,
        smotreshka,
        mikrotik,
        sms,
        email,
        error
    }
})
