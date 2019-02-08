import axios from "axios";
import Vue from "vue";
import http from '../util/httpCommon';

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
                state.clients.push(payload);
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
        addCashClientAsync ({commit, state, rootGetters }, payload) {
            let onMethod = () => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success',
                    text: 'Баланс успешно изменен'
                });
            };
            http
                .post('/admin/v1/client/' + payload.id + '/add-cash', {cash: payload.cash}, {}, onMethod);
        },
        addClientAsync ({commit, state, rootGetters }, payload) {
            //sendClient(data); // websocket

            let onMethod = () => {
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
                    payload.successFunction();
                };

            http
                .post('/admin/v1/client/create', payload.obj, payload, onMethod)
        },
        searchClientsAsync ({commit, state, rootGetters, rootState}, payload) {
            let onMethod = (response) => {
                commit('setClients', response.data);
            };
            http
                .post('/admin/v1/client/search', payload, payload, onMethod)
        },
        getClientsAsync ({commit, state, rootGetters}, payload) {
            const onMethod = (response) => {
                commit('setClients', response.data.clients);
                commit('setInternets', response.data.internets);
                commit('setRents', response.data.rents);
                commit('setTvs', response.data.tvs);
            };
            http
                .get('/admin/v1/client/', onMethod)
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
