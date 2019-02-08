import axios from 'axios';
import Vue from "vue";
import loaderStore from '../store/setting';
import userStore from '../store/user';
import errorStore from '../store/error';
import { checkErrors } from "./helpers";

export default {
    post(url, data, options, onMethod) {
        loaderStore.state.isLoader = true;

        return axios
            .post(url, data, {
                headers:{
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + userStore.state.auth.token
                }
            })
            .then(response => {
                if (response.status === 200) {
                    onMethod(response);
                }
            })
            .catch(error => {
                if(error.response && error.response.status === 422) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Проверьте введенные данные'
                    });
                    checkErrors(options.items, error.response.data);
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
                    console.log("err", error);
                }
            })
            .finally(() => {
                loaderStore.state.isLoader = false;
            });
    },
    get(url, onMethod) {
        loaderStore.state.isLoader = true;

        return axios
            .get(url, {
                headers:{
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + userStore.state.auth.token
                }
            })
            .then(response => {
                if (response.status === 200) {
                    onMethod(response);
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
