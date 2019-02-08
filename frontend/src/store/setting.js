import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        isLoader: false,
        isNavMin: false,
    },
    mutations: {
        changeNavMin (state, payload) {
            Vue.set(state, 'isNavMin', state = !state.isNavMin );
        },
        setLoader (state, payload) {
            Vue.set(state, 'isLoader', state = payload);
        },
    },
    actions: {

    },
    getters: {
        isNavMin: state => {
            return state.isNavMin;
        },
        isLoader: state => {
            return state.isLoader;
        }
    }
}
