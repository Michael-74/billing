import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        rents: [],
        editRent: null
    },
    mutations: {
        pushRents (state, payload) {
            state.rents.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editRent (state, payload) {
            state.rents.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.rents[index]) {
                        state.rents[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearRents (state) {
            //state.presets = payload;
            Vue.set(state, 'rents', []);
        },
        setRents (state, payload) {
            Vue.set(state, 'rents', payload);
            //state.rents = payload;
        },
        deleteRent (state, payload) {
            var idx = null;
            state.rents.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.rents.splice(idx, 1);
        },
        setRent (state, payload) {
            state.editRent = payload;
        },
    },
    actions: {
        addRentAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                if(payload.isFormCreate) {
                    commit("pushRents", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Тариф успешно добавлен'
                    });
                } else {
                    commit("editRent", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Тариф успешно отредактирован'
                    });
                }
                payload.successFunction();
            };
            http
                .post('/admin/v1/rent/store', payload.obj, payload, onMethod);

        },
        getRentsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setRents', response.data);
            };
            http
                .get('/admin/v1/rent/', onMethod);
        },
        deleteRentAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Тариф успешно удален'
                });
                commit('deleteRent', options.id);
            };
            http
                .delete('/admin/v1/rent/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        },
        searchRentsAsync ({commit, state, rootGetters }, payload) {
            let onMethod = (response) => {
                commit('setRents', response.data);
            };
            http
                .post('/admin/v1/rent/search', payload, payload, onMethod);
        },
    },
    getters: {
        getRents (state) {
            return state.rents;
        },
        getEditRent (state) {
            return state.editRent;
        }
    }
}
