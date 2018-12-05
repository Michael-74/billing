import axios from "axios";
import router from '../router'
import Vue from "vue";

export default {
    state: {
        client: {
            errors: null
        },
        clients: [],
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
        setClients (state, payload) {
            state.clients = payload;
        },
        pushClients (state, payload) {
            state.clients.push(payload);
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
                    console.log('client-get', response.data);
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
        getSendForm (state) {
            return state.isSendForm;
        }
    }
}
