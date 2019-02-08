import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        smotreshkas: [],
        editSmotreshka: null
    },
    mutations: {
        pushSmotreshkas (state, payload) {
            state.smotreshkas.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editSmotreshka (state, payload) {
            state.smotreshkas.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.smotreshkas[index]) {
                        state.smotreshkas[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearSmotreshkas (state) {
            Vue.set(state, 'smotreshkas', []);
        },
        setSmotreshkas (state, payload) {
            Vue.set(state, 'smotreshkas', [...payload]);
        },
        deleteSmotreshka (state, payload) {
            var idx = null;
            state.smotreshkas.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.smotreshkas.splice(idx, 1);
        },
        setSmotreshka (state, payload) {
            Vue.set(state, 'editSmotreshka', payload);
            //state.editSmotreshka = payload;
        },
    },
    actions: {
        addSmotreshkaAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                if(payload.isFormCreate) {
                    commit("pushSmotreshkas", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки добавлены'
                    });
                } else {
                    commit("editSmotreshka", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки отредактированы'
                    });
                }
                payload.successFunction();
            };
            http
                .post('/admin/v1/smotreshka/store', payload.obj, payload, onMethod);
        },
        getSmotreshkasAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setSmotreshkas', response.data);
            };
            http
                .get('/admin/v1/smotreshka/', onMethod);
        },
        deleteSmotreshkaAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Настройки успешно удалены'
                });
                commit('deleteSmotreshka', payload.id);
            };
            http
                .delete('/admin/v1/smotreshka/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        }
    },
    getters: {
        getSmotreshkas: state => {
            return state.smotreshkas;
        },
        getEditSmotreshka (state) {
            return state.editSmotreshka;
        }
    }
}
