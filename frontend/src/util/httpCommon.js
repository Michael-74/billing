import axios from 'axios';
import Vue from "vue";
import store from '../store/setting';
import {checkErrors} from "./helpers";

export default {
    post(url, payload, token) {
        store.state.isLoader = true;

        return axios
            .post(url, payload, {
                headers:{
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            })
            .catch(error => {
                if(error.response && error.response.status === 422) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Проверьте введенные данные'
                    });
                } else if(error.response && error.response.status === 403) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Пользователь не зарегистрирован'
                    });
                } else {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка, статус неизвестен'
                    });
                }
            })
            .finally(() => {
                store.state.isLoader = false;
            });
    }
}
