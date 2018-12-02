import axios from "axios";
import router from '../router'

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

            router.push('/admin/clients');
        },
        logOut (state, payload) {
            state.auth.isLoggedIn = false;
            state.auth.token = null;
            state.auth.username = null;
            localStorage.removeItem('JWT')
        }
    },
    // асинхронный setter
    // this.$store.dispatch('название_асинхронного_сеттера', значение) или ({commit, state}, значение)
    actions: {
        auth: ({commit, state}, payload) => {
            axios
                .post('/auth/v1/login', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => {
                    if (response.status === 200) {

                        console.log("token", response)
                        commit('changeAuth', {token: response.data.token, username: response.data.user})
                        localStorage.setItem('JWT', response.data.token)
                    } else {
                        console.log("else_1:", response)
                        router.push('/login');
                    }
                })
                .catch(e => {
                    console.log("error", e)
                    router.push('/login');
                });
        }
    },
    getters: {
        getUser (state) {
            return state.auth;
        }
    }
}
