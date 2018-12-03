import axios from "axios";
import router from '../router'

export default {
    state: {
        client: {
            errors: null
        }
    },
    mutations: {
        addErrors (state, payload) {
            state.client.errors = payload;
        }
    },
    actions: {
        clientStore: function ({commit, state}, payload) {
            axios
                .post('/admin/v1/client/create', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + localStorage.getItem('JWT')
                    }
                })
                .then(response => {
                    if (response.status === 200) {
                        console.log('client-store', "save");
                    }
                })
                .catch(error => {
                    if(error.response.status === 422) {
                        console.log("error", error.response);
                        commit('addErrors', error.response.data)
                    }
                });
        },
        getClientAsync ({commit, state}, payload) {
            return state.client;
        }
    },
    getters: {
        getClient (state) {
            return state.client;
        }
    }
}
