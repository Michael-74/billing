import axios from "axios";
import router from '../router'
import Vue from "vue";

export default {
    state: {
        client: {
            errors: null
        },
        clients: [],
        editClient: null,
        isSendForm: false
    },
    mutations: {
        addErrors (state, payload) {
            state.client.errors = payload;
        },
        clearErrors (state, payload) {
            state.client.errors = null;
        },
        selectSendForm (state, payload) {
            state.isSendForm = payload;
        },
        setClient (state, payload) {
            state.editClient = payload;
        },
        setClients (state, payload) {
            state.clients = payload;
        },
        pushClients (state, payload) {
            var old = state.clients;
            var isMatches = false;
            state.clients.forEach((item, index, array) => {
                if(item.id === payload.id) {
                    isMatches = true;
                    console.log("Есть совпадения", item)
                    old[index] = payload;
                }
            });

            // Хак чтобы vuex увидел обновление
            Vue.set(state, 'clients', [...old]);
            if(!isMatches) {
                state.clients.unshift(payload);
            }
        },
        deleteClient (state, id) {
            state.clients.forEach((item, index, array) => {
                if(item.id === id) {
                    state.clients.splice(index, 1);
                }
            });
        }
    },
    actions: {
        searchClientsAsync ({commit, state, rootGetters }, payload) {
            console.log("searchClientsAsync", payload);
            //const data = {data: payload.data};
            //const store = JSON.stringify(data);

            axios
                .post('/admin/v1/client/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchClientsAsync success", response);
                    commit('setClients', response.data);
                })
        },
        getClientsAsync ({commit, state}, payload) {
            axios
                .get('/admin/v1/client/', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + payload.token
                    }
                })
                .then(response => {
                    commit('setClients', response.data);
                })
        }
    },
    getters: {
        getClient (state) {
            return state.client;
        },
        getClients (state) {
            return state.clients;
        },
        getEditClient (state) {
            return state.editClient;
        },
        getSendForm (state) {
            return state.isSendForm;
        }
    }
}
