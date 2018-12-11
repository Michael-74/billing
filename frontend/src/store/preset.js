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
        setPresets (state, payload) {
            //state.presets = payload;
            Vue.set(state, 'presets', [...payload]);
            state.presets.push(payload);
        },
    },
    actions: {
        getPresetsAsync ({commit, state}, payload) {
            axios
                .post('/admin/v1/preset/', payload.url, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + payload.token
                    }
                })
                .then(response => {
                    var data = [];
                    response.data.forEach((item) => {
                        commit('pushPresets', {id: item.id, val: item.name, settings: item.settings});
                        //data.push({id: item.id, val: item.name, settings: item.settings});
                    });
                    //commit('setPresets', data);
                })
        },
        /**
         * TODO:: Единый метод для всех пресетов
         */
        addPresetAsync ({commit, state}, payload) {

            const data = {url: payload.url, name: payload.name, settings: payload.fields};
            const store = JSON.stringify(data);
            console.log("store", store);
            axios
                .post('/admin/v1/preset/store', store, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + payload.token
                    }
                })
                .then(response => {
                    console.log("preset-store", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Пресет успешно добавлен'
                    });
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
