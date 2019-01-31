import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

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
        addSmsAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/sms/store', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {

                        // Если форма стоит для Добавления
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
                    }
                })
                .catch(error => {
                    console.log("error", error);
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
        getSmsesAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/sms/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setMikrotiks', response.data);
                })
        },
        deleteSmsAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/sms/delete/' + payload.id, {id: parseInt(payload.id)}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки успешно удалены'
                    });
                    commit('deleteSms', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении настроек'
                    });
                })
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
