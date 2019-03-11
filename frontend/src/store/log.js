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
            };
            http
                //.post('/admin/v1/log/search', payload.obj, payload, onMethod);
                .post('/admin/v1/log/search', {}, payload, onMethod);
        }
    },
    getters: {
        getLogs: state => {
            return state.logs;
        },
    }
}
