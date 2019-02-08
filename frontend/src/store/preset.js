import axios from "axios";
import router from '../router'
import Vue from "vue";
import http from '../util/httpCommon';

export default {
    state: {
        presets: [],
    },
    mutations: {
        pushPresets (state, payload) {
            state.presets.push(payload);
        },
        clearPresets (state) {
            //state.presets = payload;
            Vue.set(state, 'presets', []);
        },
        deletePreset (state, payload) {
            var idx = null;
            state.presets.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.presets.splice(idx, 1);
        }
    },
    actions: {
        getPresetsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('clearPresets');
                response.data.forEach((item) => {
                    commit('pushPresets', item);
                });
            };
            http
                .post('/admin/v1/preset/', payload.url, payload, onMethod);
        },
        deletePresetAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Пресет успешно удален'
                });
                commit('deletePreset', options.id);
            };
            http
                .delete('/admin/v1/preset/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        },
        /**
         * TODO:: Единый метод для всех пресетов
         */
        addPresetAsync ({commit, state, rootGetters}, payload) {

            const store = JSON.stringify(payload);
            console.log("store", store);
            axios
                .post('/admin/v1/preset/store', store, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("preset-store", response)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Пресет успешно добавлен'
                    });
                    commit("pushPresets", response.data)
                })
                .catch(error => {
                    if(error.response.status === 422) {
                        console.log("error", error.response);
                        /*
                        this.$store.commit('addErrors', error.response.data);
                        this.$store.commit('selectSendForm', false);
                        */
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Проверьте введенные данные'
                        });
                        /*
                        this.checkErrors();
                        */
                    }
            });
        },
    },
    getters: {
        getPresets (state) {
            return state.presets;
        },
    }
}
