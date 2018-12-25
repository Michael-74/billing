import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        tvs: [],
        editTv: null
    },
    mutations: {
        pushTvs (state, payload) {
            state.tvs.unshift(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editTv (state, payload) {
            state.tvs.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.tvs[index]) {
                        state.tvs[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearTvs (state) {
            //state.presets = payload;
            Vue.set(state, 'tvs', []);
        },
        setTvs (state, payload) {
            state.tvs = payload;
        },
        deleteTv (state, payload) {
            var idx = null;
            state.tvs.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.tvs.splice(idx, 1);
        },
        setTv (state, payload) {
            state.editTv = payload;
        },
    },
    actions: {
        addTvAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/tv/store', payload.obj, {
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
                            commit("pushTvs", response.data)
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Тариф успешно добавлен'
                            });
                        } else {
                            //console.log("internet", this.internet)
                            commit("editTv", response.data)
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
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Проверьте введенные данные'
                        });
                        checkErrors(payload.items, rootGetters.getErrors);
                    }
                });

        },
        getTvsAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/tv/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setTvs', response.data);
                })
        },
        deleteTvAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/tv/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                    commit('deleteTv', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении интернет тарифа'
                    });
                })
        },
        searchTvsAsync ({commit, state, rootGetters }, payload) {
            console.log("searchTvAsync", payload);
            //const data = {data: payload.data};
            //const store = JSON.stringify(data);

            axios
                .post('/admin/v1/tv/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchTvAsync success", response);
                    commit('setTvs', response.data);
                })
        },
    },
    getters: {
        getTvs (state) {
            return state.tvs;
        },
        getEditTv (state) {
            return state.editTv;
        }
    }
}
