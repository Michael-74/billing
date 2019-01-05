import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        tasks: [],
        editTask: null
    },
    mutations: {
        pushTasks (state, payload) {
            state.tasks.unshift(payload);
            //state.tasks.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editTask (state, payload) {
            state.tasks.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.tasks[index]) {
                        state.tasks[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearTasks (state) {
            //state.presets = payload;
            Vue.set(state, 'tasks', []);
        },
        setTasks (state, payload) {
            state.tasks = payload;
        },
        deleteTask (state, payload) {
            var idx = null;
            state.tasks.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.tasks.splice(idx, 1);
        },
        setTask (state, payload) {
            state.editTask = payload;
        },
    },
    actions: {
        addTaskAsync ({commit, state, rootGetters}, payload) {

            axios
                .post('/admin/v1/task/store', payload.obj, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {

                        commit("pushTasks", response.data)
                        Vue.prototype.$notify({
                            group: 'notify',
                            type: 'success ',
                            text: 'Задача успешно добавлена'
                        });
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

                        checkErrors(payload.items, rootGetters.getErrors, payload.options);
                    }
                });

        },
        getTasksAsync ({commit, state, rootGetters}, payload) {
            axios
                .get('/admin/v1/task/', {}, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    commit('setTasks', response.data);
                })
        },
        deleteTaskAsync ({commit, state, rootGetters}, payload) {
            axios
                .delete('/admin/v1/task/delete/' + payload.id, {id: parseInt(payload.id)}, {
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
                        text: 'Задача успешно удалена'
                    });
                    commit('deleteTask', payload.id);
                })
                .catch(error => {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Ошибка при удалении задачи'
                    });
                })
        },
        searchTasksAsync ({commit, state, rootGetters }, payload) {
            console.log("searchTasksAsync", payload);
            //const data = {data: payload.data};
            //const store = JSON.stringify(data);

            axios
                .post('/admin/v1/task/search', payload, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + rootGetters.getUser.token
                    }
                })
                .then(response => {
                    console.log("searchTasksAsync success", response);
                    commit('setTasks', response.data);
                })
        },
    },
    getters: {
        getTasks (state) {
            return state.tasks;
        },
        getEditTask (state) {
            return state.editTask;
        }
    }
}
