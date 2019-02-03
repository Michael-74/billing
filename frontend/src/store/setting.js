import axios from "axios";
import router from '../router'
import Vue from "vue";
import {checkErrors} from "../util/helpers";

export default {
    state: {
        isNavMin: false,
    },
    mutations: {
        changeNavMin (state, payload) {
            Vue.set(state, 'isNavMin', state = !state.isNavMin );
        },
    },
    actions: {

    },
    getters: {
        isNavMin: state => {
            return state.isNavMin;
        }
    }
}
