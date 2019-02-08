import axios from "axios";
import Vue from "vue";
import http from '../util/httpCommon';

export default {
    state: {
        tasks: [],
        editTask: null,
        selectedTasks: [],
    },
    mutations: {
        pushTasks (state, payload) {
            state.tasks.push(payload);
            //state.tasks.push(payload);
        },
        /**
         * Добавляем выбранные задачи
         * И проверяем, что не добавили повторно
         * @param state - список задач
         * @param payload - id задачи
         */
        pushSelectedTasks (state, payload) {
            const isEmpty = state.selectedTasks.find(function(item, index){
                if(item.id === payload.id){
                    return true;
                }
            });

            if(!isEmpty) {
                state.selectedTasks.push(payload);
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Задача успешно выбрана'
                });
            } else {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'error ',
                    text: 'Задача уже выбрана'
                });
            }
        },
        setSelectedTasks (state, payload) {
            Vue.set(state, 'selectedTasks', [...payload]);
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
        deleteSelectedTask (state, payload) {
            var idx = null;
            state.selectedTasks.forEach((item, index) => {
                if(item.id === payload.id) {
                    idx = index;
                }
            });
            state.selectedTasks.splice(idx, 1);

            Vue.prototype.$notify({
                group: 'notify',
                type: 'success ',
                text: 'Задача успешно убрана'
            });
        },
        setEditTask (state, payload) {
            state.editTask = payload;
        },
    },
    actions: {
        addTaskAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit("pushTasks", response.data)
                if(payload.isFormCreate) {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Задача успешно добавлена'
                    });
                } else {
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Задача успешно отредактирована'
                    });
                }
            };
            http
                .post('/admin/v1/task/store', payload.obj, payload, onMethod)

        },
        getTasksAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setTasks', response.data);
            };
            http
                .get('/admin/v1/task/', onMethod);
        },
        deleteTaskAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Задача успешно удалена'
                });
                commit('deleteTask', options.id);
            };
            http
                .delete('/admin/v1/task/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod);
        },
        searchTasksAsync ({commit, state, rootGetters }, payload) {
            let onMethod = (response) => {
                commit('setTasks', response.data);
            };
            http
                .post('/admin/v1/task/search', payload, payload, onMethod);
        },
    },
    getters: {
        getTasks (state) {
            return state.tasks;
        },
        getEditTask (state) {
            return state.editTask;
        },
        getSelectedTasks (state) {
            return state.selectedTasks;
        }
    }
}
