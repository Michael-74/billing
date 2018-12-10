import axios from "axios";
import router from '../router'
import Vue from "vue";

export default {
    state: {
        presets: [],
    },
    mutations: {
        pushPresets (state, payload) {
            state.presets.push(payload);
        },
    },
    actions: {
        getPresetsAsync ({commit, state}, payload) {
            axios
                .post('/admin/v1/preset/', payload.url, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + payload.token
                    }
                })
                .then(response => {
                    response.data.forEach((item) => {
                        commit('pushPresets', {id: item.id, val: item.name});
                    });
                })
        }
    },
    getters: {
        getPresets (state) {
            return state.presets;
        },
    }
}
