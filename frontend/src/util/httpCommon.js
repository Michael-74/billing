import axios from 'axios';
import Vue from "vue";
import loaderStore from '../store/setting';
import userStore from '../store/user';
import errorStore from '../store/error';
import { checkErrors } from "./helpers";

export default {
    post(url, payload) {
        loaderStore.state.isLoader = true;

        return axios
            .post(url, payload, {
                headers:{
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + userStore.state.auth.token
                }
            })
            .catch(error => {
                if(error.response && error.response.status === 422) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Проверьте введенные данные'
                    });
                    checkErrors(payload.items, errorStore.getters.getErrors);
                } else if(error.response && error.response.status === 403) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Пользователь не зарегистрирован'
                    });
                    setTimeout(()=>{
                        this.$router.push("/login")
                    }, 1000)
                } else {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка, код ошибки неизвестен'
                    });
                }
            })
            .finally(() => {
                loaderStore.state.isLoader = false;
            });
    },
    get(url) {
        loaderStore.state.isLoader = true;

        return axios
            .post(url, {
                headers:{
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + userStore.state.auth.token
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
                    setTimeout(()=>{
                        this.$router.push("/login")
                    }, 1000)
                } else {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка, код ошибки неизвестен'
                    });
                }
            })
            .finally(() => {
                loaderStore.state.isLoader = false;
            });
    }
}
