import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

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

            axios
                .post('/admin/v1/rent/store', payload.obj, {
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
                            commit("pushRents", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно добавлен'
                            });
                        } else {
                            //console.log("internet", this.internet)
                            commit("editRent", response.data)
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
        getRentsAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/rent/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setRents', response.data);
                })
        },
        deleteRentAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/rent/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                        text: 'Тариф успешно удален'
                    });
                    commit('deleteRent', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении тарифа'
                    });
                })
        },
        searchRentsAsync ({commit, state, rootGetters }, payload) {

            axios
                .post('/admin/v1/rent/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchRentAsync success", response);
                    commit('setRents', response.data);
                })
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
