import axios from "axios";
import router from '../router'
import Vue from "vue";

export default {
    state: {
        errors: null
    },
    mutations: {
        setErrors (state, payload) {
            state.errors = payload;
        },
        clearErrors (state, payload) {
            state.errors = null;
        }
    },
    actions: {

    },
    getters: {
        getErrors (state) {
            return state.errors;
        }
    }
}
