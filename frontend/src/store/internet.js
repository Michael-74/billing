import axios from "axios";
import router from '../router'
import Vue from "vue";

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
        }
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
