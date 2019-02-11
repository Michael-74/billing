import Vue from "vue";
import http from '../util/httpCommon';

export default {
    state: {
        emails: [],
        editEmail: null
    },
    mutations: {
        pushEmails (state, payload) {
            state.emails.push(payload);
            //state.internets.push(payload);
        },
        /**
         * Меняем значения в стейте на измененные данные из формы
         * @param state
         * @param payload
         */
        editEmail (state, payload) {
            state.emails.forEach((item, index) => {
                if(item.id === payload.id) {
                    for(let cur in state.emails[index]) {
                        state.emails[index][cur] = payload[cur];
                    }
                }
            });
        },
        clearEmails (state) {
            Vue.set(state, 'emails', []);
        },
        setEmails (state, payload) {
            Vue.set(state, 'emails', [...payload]);
        },
        deleteEmail (state, payload) {
            var idx = null;
            state.emails.forEach((item, index) => {
                if(item.id === payload) {
                    idx = index;
                }
            });
            state.emails.splice(idx, 1);
        },
        setEmail (state, payload) {
            Vue.set(state, 'editEmail', payload);
            //state.editMikrotik = payload;
        },
    },
    actions: {
        addEmailsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                // Если форма стоит для Добавления
                if(payload.isFormCreate) {
                    commit("pushEmails", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки добавлены'
                    });
                } else {
                    //console.log("internet", this.internet)
                    commit("editEmail", response.data)
                    Vue.prototype.$notify({
                        group: 'notify',
                        type: 'success ',
                        text: 'Настройки отредактированы'
                    });
                }
                payload.successFunction();
            };

            http
                .post('/admin/v1/email/store', payload.obj, payload, onMethod);
        },
        getEmailsAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response) => {
                commit('setEmails', response.data);
            };
            http
                .get('/admin/v1/email/', onMethod);
        },
        deleteEmailAsync ({commit, state, rootGetters}, payload) {
            let onMethod = (response, options) => {
                Vue.prototype.$notify({
                    group: 'notify',
                    type: 'success ',
                    text: 'Настройки успешно удалены'
                });
                commit('deleteEmail', options.id);
            };
            http
                .delete('/admin/v1/email/delete/' + payload.id, {id: parseInt(payload.id)}, payload, onMethod)
        }
    },
    getters: {
        getEmails: state => {
            return state.emails;
        },
        getEditEmail (state) {
            return state.editEmail;
        }
    }
}
