import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        internets: [],
        editInternet: null,
        isSendForm: false
    },
    mutations: {
        pushInternets (state, payload) {
            state.internets.unshift(payload);
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
            //state.presets = payload;
            Vue.set(state, 'internets', []);
        },
        setInternets (state, payload) {
            state.internets = payload;
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
        selectSendForm (state, payload) {
            state.isSendForm = payload;
        },
        setInternet (state, payload) {
            state.editInternet = payload;
        },
    },
    actions: {
        addInternetAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/internet/store', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {
                        commit('selectSendForm', true);

                        // Если форма стоит для Добавления
                        if(payload.isFormCreate) {
                            commit("pushInternets", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно добавлен'
                            });
                        } else {
                            //console.log("internet", this.internet)
                            commit("editInternet", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно отредактирован'
                            });
                        }
                    }
                })
                .catch(error => {
                    console.log("error", error);
                    if(error.response.status === 422) {
                        commit('setErrors', error.response.data);
                        commit('selectSendForm', false);
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Проверьте введенные данные'
                        });
                        checkErrors(payload.items, rootGetters.getErrors);
                    }
                });

        },
        getInternetsAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/internet/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setInternets', response.data);
                })
        },
        deleteInternetAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/internet/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                    commit('deleteInternet', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении интернет тарифа'
                    });
                })
        },
        searchInternetsAsync ({commit, state, rootGetters }, payload) {
            console.log("searchInternetsAsync", payload);
            //const data = {data: payload.data};
            //const store = JSON.stringify(data);

            axios
                .post('/admin/v1/internet/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchInternetsAsync success", response);
                    commit('setInternets', response.data);
                })
        },
    },
    getters: {
        getInternets (state) {
            return state.internets;
        },
        getEditInternet (state) {
            return state.editInternet;
        },
        getInternetSendForm (state) {
            return state.isSendForm;
        }
    }
}
