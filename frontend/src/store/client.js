import axios from "axios";
import router from '../router'
import Vue from "vue";

export default {
    state: {
        client: {
            errors: null
        },
        isSendForm: false
    },
    mutations: {
        addErrors (state, payload) {
            state.client.errors = payload;
        },
        clearErrors (state, payload) {
            state.client.errors = null;
        },
        selectSendForm (state, payload) {
            state.isSendForm = payload;
        }
    },
    actions: {

    },
    getters: {
        getClient (state) {
            return state.client;
        },
        getSendForm (state) {
            return state.isSendForm;
        }
    }
}
