import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

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

            axios
                .post('/admin/v1/mikrotik/store', payload.obj, {
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
                            commit("pushMikrotiks", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Настройки добавлены'
                            });
                        } else {
                            //console.log("internet", this.internet)
                            commit("editMikrotik", response.data)
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
        getMikrotiksAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/mikrotik/', {}, {
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
        deleteMikrotikAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/mikrotik/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                    commit('deleteMikrotik', payload.id);
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
        getMikrotiks: state => {
            return state.mikrotiks;
        },
        getEditMikrotik (state) {
            return state.editMikrotik;
        }
    }
}
