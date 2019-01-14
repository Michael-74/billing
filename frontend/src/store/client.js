import axios from "axios";
import router from '../router'
import Vue from "vue";
import { parseObj, checkErrors } from '../util/helpers'

export default {
    state: {
        clients: [],
        editClient: null
    },
    mutations: {
        setClient (state, payload) {
            Vue.set(state, 'editClient', payload);
        },
        setClients (state, payload) {
            Vue.set(state, 'clients', payload);
            //state.clients = payload;
        },
        pushClients (state, payload) {
            var old = state.clients;
            var isMatches = false;
            state.clients.forEach((item, index, array) => {
                if(item.id === payload.id) {
                    isMatches = true;
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
        addClientAsync ({commit, state, rootGetters }, payload) {
            //sendClient(data); // websocket

            axios
                .post('/admin/v1/client/create', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {
                        if(payload.isFormCreate) {
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Абонент успешно добавлен'
                            });
                        } else {
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Абонент успешно отредактирован'
                            });
                        }
                    }
                })
                .catch(error => {
                    if(error.response.status === 422) {
                        commit('setErrors', error.response.data);
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Проверьте введенные данные'
                        });
                        checkErrors(payload.items, rootGetters.getErrors);
                    }
                });

        },
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
        getClientsAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/client/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("getClientsAsync", response);
                    commit('setClients', response.data.clients);
                    commit('setListInternets', response.data.internets);
                    commit('setListRents', response.data.rents);
                    commit('setListTvs', response.data.tvs);
                }).catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Проверьте введенные данные'
                    });
            });
        }
    },
    getters: {
        getClients (state) {
            return state.clients;
        },
        getEditClient (state) {
            return state.editClient;
        }
    }
}
