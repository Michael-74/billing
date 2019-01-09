import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        packs: [],
        editPack: null,
        selectedPacks: [],
    },
    mutations: {
        pushPacks (state, payload) {
            state.packs.unshift(payload);
            //state.tasks.push(payload);
        },
        /**
         * Добавляем выбранные задачи
         * И проверяем, что не добавили повторно
         * @param state - список задач
         * @param payload - id задачи
         */
        pushSelectedPacks (state, payload) {
            const isEmpty = state.selectedPacks.find(function(item, index){
                if(item.id === payload.id){
                    return true;
                }
            });

            if(!isEmpty) {
                state.selectedPacks.push(payload);
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Пакет успешно выбрана'
                });
            } else {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'error ',
                    text: 'Пакет уже выбрана'
                });
            }
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editPack (state, payload) {
            state.packs.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.packs[index]) {
                        state.packs[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearPacks (state) {
            //state.presets = payload;
            Vue.set(state, 'packs', []);
        },
        setPacks (state, payload) {
            state.packs = payload;
        },
        deletePack (state, payload) {
            var idx = null;
            state.packs.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.packs.splice(idx, 1);
        },
        deleteSelectedPack (state, payload) {
            var idx = null;
            state.selectedPacks.forEach((item, index) => {
                if(item.id === payload.id) {
                    idx = index;
                }
            });
            state.selectedPacks.splice(idx, 1);

            Vue.prototype.$notify({
                group: 'notify',
                type: 'success ',
                text: 'Пакет успешно убрана'
            });
        },
        setEditPack (state, payload) {
            state.editPack = payload;
        },
    },
    actions: {
        addPackAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/package/store', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {

                        commit("pushPacks", response.data)
                        if(payload.isFormCreate) {
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Пакет успешно добавлен'
                            });
                        } else {
                            Vue.prototype.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Пакет успешно отредактирован'
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
        getPacksAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/package/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setPacks', response.data);
                })
        },
        deletePackAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/package/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                        text: 'Пакет успешно удалена'
                    });
                    commit('deletePack', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении пакета'
                    });
                })
        },
        searchPacksAsync ({commit, state, rootGetters }, payload) {
            console.log("searchPacksAsync", payload);
            //const data = {data: payload.data};
            //const store = JSON.stringify(data);

            axios
                .post('/admin/v1/package/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchPacksAsync success", response);
                    commit('setPacks', response.data);
                })
        },
    },
    getters: {
        getPacks (state) {
            return state.packs;
        },
        getEditPack (state) {
            return state.editPack;
        },
        getSelectedPacks (state) {
            return state.selectedPacks;
        }
    }
}
