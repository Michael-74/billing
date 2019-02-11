import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";
import http from '../util/httpCommon';
export default {
    state: {

    },
    mutations: {

    },
    actions: {
        sendMailingAsync({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Сообщение отправлено'
                });
                payload.successFunction();
            };
            http
                .post('/admin/v1/mail/send', payload.obj, payload, onMethod);
        }
    },
    getters: {

    }
}
