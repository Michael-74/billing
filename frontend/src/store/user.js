import axios from "axios";
import router from '../router'
import http from '../util/httpCommon';

export default {
    state: {
        auth: {
            isLoggedIn: false,
            token: null,
            username: null
        }
    },
    // setter
    // this.$store.commit('название_сеттера', значение)
    mutations: {
        changeAuth (state, payload) {
            state.auth.isLoggedIn = true;
            state.auth.token = payload.token;
            state.auth.username = payload.username;
        },
        logOut (state, payload) {
            state.auth.isLoggedIn = false;
            state.auth.token = null;
            state.auth.username = null;
            localStorage.removeItem('JWT')
            localStorage.removeItem('username')
        }
    },
    // асинхронный setter
    // this.$store.dispatch('название_асинхронного_сеттера', значение) или ({commit, state}, значение)
    actions: {

    },
    getters: {
        getUser (state) {
            return state.auth;
        }
    }
}
