import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {

    },
    mutations: {

    },
    actions: {
        sendMailingAsync({commit, state, rootGetters}, payload) {
            axios
                .post('/admin/v1/mail/send', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'success ',
                            text: 'Сообщение отправлено'
                        });
                        payload.successFunction();
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
                    } else {
                        commit('setErrors', error.response.data);
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Произошла неизвестная ошибка'
                        });
                    }
                });
        }
    },
    getters: {

    }
}
