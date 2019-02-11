import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';

export default {
    state: {
        tvs: [],
        editTv: null
    },
    mutations: {
        pushTvs (state, payload) {
            state.tvs.push(payload);
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
            let onMethod = (response) => {
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
                payload.successFunction();
            };
            http
                .post('/admin/v1/tv/store', payload.obj, payload, onMethod);
        },
        getTvsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setTvs', response.data);
            };
            http
                .get('/admin/v1/tv/', onMethod);
        },
        deleteTvAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Интернет тариф успешно удален'
                });
                commit('deleteTv', options.id);
            };
            http
                .delete('/admin/v1/tv/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        },
        searchTvsAsync ({commit, state, rootGetters }, payload) {
            let onMethod = (response) => {
                commit('setTvs', response.data);
            };
            http
                .post('/admin/v1/tv/search', payload, payload, onMethod);
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
