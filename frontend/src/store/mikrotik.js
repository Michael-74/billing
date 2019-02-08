import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        mikrotiks: [],
        editMikrotik: null
    },
    mutations: {
        pushMikrotiks (state, payload) {
            state.mikrotiks.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editMikrotik (state, payload) {
            state.mikrotiks.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.mikrotiks[index]) {
                        state.mikrotiks[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearMikrotiks (state) {
            Vue.set(state, 'mikrotiks', []);
        },
        setMikrotiks (state, payload) {
            Vue.set(state, 'mikrotiks', [...payload]);
        },
        deleteMikrotik (state, payload) {
            var idx = null;
            state.mikrotiks.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.mikrotiks.splice(idx, 1);
        },
        setMikrotik (state, payload) {
            Vue.set(state, 'editMikrotik', payload);
            //state.editMikrotik = payload;
        },
    },
    actions: {
        addMikrotikAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                if(payload.isFormCreate) {
                    commit("pushMikrotiks", response.data);
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки добавлены'
                    });
                } else {
                    //console.log("internet", this.internet)
                    commit("editMikrotik", response.data);
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки отредактированы'
                    });
                }
                payload.successFunction();
            };
            http
                .post('/admin/v1/mikrotik/store', payload.obj, payload, onMethod);
        },
        getMikrotiksAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setMikrotiks', response.data);
            };
            http
                .get('/admin/v1/mikrotik/', onMethod);
        },
        deleteMikrotikAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Настройки успешно удалены'
                });
                commit('deleteMikrotik', options.id);
            };
            http
                .delete('/admin/v1/mikrotik/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        }
    },
    getters: {
        getMikrotiks: state => {
            return state.mikrotiks;
        },
        getEditMikrotik (state) {
            return state.editMikrotik;
        }
    }
}
