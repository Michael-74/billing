import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        smses: [],
        editSms: null
    },
    mutations: {
        pushSmses (state, payload) {
            state.smses.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editSms (state, payload) {
            state.smses.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.smses[index]) {
                        state.smses[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearSmses (state) {
            Vue.set(state, 'smses', []);
        },
        setSmses (state, payload) {
            Vue.set(state, 'smses', [...payload]);
        },
        deleteSms (state, payload) {
            var idx = null;
            state.smses.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.smses.splice(idx, 1);
        },
        setSms (state, payload) {
            Vue.set(state, 'editSms', payload);
            //state.editMikrotik = payload;
        },
    },
    actions: {
        addSmsesAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                if(payload.isFormCreate) {
                    commit("pushSmses", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки добавлены'
                    });
                } else {
                    //console.log("internet", this.internet)
                    commit("editSms", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки отредактированы'
                    });
                }
                payload.successFunction();
            };
            http
                .post('/admin/v1/sms/store', payload.obj, payload, onMethod);
        },
        getSmsesAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setSmses', response.data);
            };
            http
                .get('/admin/v1/sms/', onMethod);
        },
        deleteSmsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Настройки успешно удалены'
                });
                commit('deleteSms', options.id);
            };
            http
                .delete('/admin/v1/sms/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        }
    },
    getters: {
        getSmses: state => {
            return state.smses;
        },
        getEditSms (state) {
            return state.editSms;
        }
    }
}
