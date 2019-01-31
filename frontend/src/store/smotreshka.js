import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

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
                    for(let cur in state.smotreskas[index]) {
                        state.smotreskas[index][cur] = payload[cur];
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
            state.editSmotreshka = payload;
        },
    },
    actions: {
        addSmotreshkaAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/smotreshka/store', payload.obj, {
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
                            commit("pushSmotreshkas", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно добавлен'
                            });
                        } else {
                            //console.log("internet", this.internet)
                            commit("editSmotreshka", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно отредактирован'
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
        getSmotreshkasAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/smotreshka/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setSmotreskas', response.data);
                })
        },
        deleteSmotreshkaAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/smotreshka/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                        text: 'Интернет тариф успешно удален'
                    });
                    commit('deleteSmotreshka', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении интернет тарифа'
                    });
                })
        }
    },
    getters: {
        getSmotreshkas: state => {
            return state.internets;
        },
        getEditSmotreshka (state) {
            return state.editSmotreska;
        }
    }
}
