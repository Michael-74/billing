import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        packs: [],
        editPack: null,
        selectedPacks: [],
    },
    mutations: {
        pushPacks (state, payload) {
            state.packs.push(payload);
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
            let onMethod = (response) => {
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
            };
            http
                .post('/admin/v1/package/store', payload.obj, payload, onMethod);
        },
        getPacksAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setPacks', response.data);
            };
            http
                .get('/admin/v1/package/', onMethod);
        },
        deletePackAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Пакет успешно удален'
                });
                commit('deletePack', payload.id);
            };
            http
                .delete('/admin/v1/package/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        },
        searchPacksAsync ({commit, state, rootGetters }, payload) {
            let onMethod = (response) => {
                commit('setPacks', response.data);
            };
            http
                .post('/admin/v1/package/search', payload, payload, onMethod);
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
