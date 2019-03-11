import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';
export default {
    state: {
        logs: []
    },
    mutations: {
        setLogs (state, payload) {
            Vue.set(state, 'logs', [...payload]);
        },
    },
    actions: {
        searchLogAsync({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setLogs', response.data);

                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Поиск лога'
                });
                //payload.successFunction();
            };
            http
                .post('/admin/v1/client/log', payload.obj, payload, onMethod);
        }
    },
    getters: {
        getLogs: state => {
            return state.logs;
        },
    }
}
