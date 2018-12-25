import axios from "axios";
import router from '../router'
import Vue from "vue";

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
            axios
                .post('/admin/v1/preset/', payload.url, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('clearPresets');
                    response.data.forEach((item) => {
                        commit('pushPresets', item);
                    });
                })
        },
        deletePresetAsync ({commit, state, rootGetters}, payload) {
            console.log("id", payload.id);
            axios
                .delete('/admin/v1/preset/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                        text: 'Пресет успешно удален'
                    });
                    console.log('delete preset', state.presets)
                    commit('deletePreset', payload.id);
                    /*
                    var data = [];
                    response.data.forEach((item) => {
                        commit('pushPresets', item);
                        //data.push({id: item.id, val: item.name, settings: item.settings});
                    });
                    //commit('setPresets', data);
                    */
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении пресета'
                    });
                })
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
