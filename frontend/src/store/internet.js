import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        internets: [],
        listInternets: [],
        editInternet: null
    },
    mutations: {
        pushInternets (state, payload) {
            state.internets.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editInternet (state, payload) {
            state.internets.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.internets[index]) {
                        state.internets[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearInternets (state) {
            Vue.set(state, 'internets', []);
        },
        setInternets (state, payload) {
            Vue.set(state, 'internets', [...payload]);
        },
        setListInternets (state, payload) {
            Vue.set(state, 'listInternets', [...payload]);
            //state.internets = payload;
            //state.listInternets = { ...state.listInternets, payload };
        },
        deleteInternet (state, payload) {
            var idx = null;
            state.internets.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.internets.splice(idx, 1);
        },
        setInternet (state, payload) {
            state.editInternet = payload;
        },
    },
    actions: {
        addInternetAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                if (payload.isFormCreate) {
                    commit("pushInternets", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Тариф успешно добавлен'
                    });
                } else {
                    commit("editInternet", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Тариф успешно отредактирован'
                    });
                }
                payload.successFunction();
            };

            http
                .post('/admin/v1/internet/store', payload.obj, payload, onMethod)
        },
        getInternetsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setInternets', response.data);
            };
            commit('setLoader',true, {root: true});
            http
                .get('/admin/v1/internet/', onMethod);
        },
        deleteInternetAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Интернет тариф успешно удален'
                });
                commit('deleteInternet', options.id);
            };
            http
                .delete('/admin/v1/internet/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod)
        },
        searchInternetsAsync ({commit, state, rootGetters }, payload) {
            let onMethod = (response) => {
                commit('setInternets', response.data);
            };

            http
                .post('/admin/v1/internet/search', payload, payload, onMethod);
        },
    },
    getters: {
        getInternets: state => {
            return state.internets;
        },
        getListInternets: state => {
            return state.listInternets;
        },
        getEditInternet (state) {
            return state.editInternet;
        }
    }
}
