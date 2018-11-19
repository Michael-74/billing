<template>
    <div>
        <h1>Login</h1>
        <router-link to='/user/' tag="div" active-class="nav__li_active">
            <a>User</a>
        </router-link>
        <form class="auth" method="post" @submit="sendForm">
            <div>
                <label>Логин:</label>
                <input class="login" type="text" name="login" v-model="login">
            </div>
            <div>
                <label>Пароль:</label>
                <input class="password" type="text" name="password" v-model="password">
            </div>
            <div>
                <button>Отправить</button>
            </div>
        </form>
    </div>
</template>

<script>

import axios from "axios";

export default {
    data () {
        return {
            login: null,
            password: null
        }
    },
    methods: {
        sendForm: function (e) {

            const data = {
                'username': this.login,
                'password': this.password
            };

            const data1 = JSON.stringify(data);
            axios
                .post('/auth/v1/login', data1, {
                    headers:{ Accept: 'application/json'}
                })
                .then(response => {
                    if (response.status === 200) {
                        console.log("response:", response)
                        response.json().then(json => {
                            console.log("token", json.token)
                        })
                    } else if (response.status === 401) {
                        this.log = 401
                        console.log("401_1:", response)
                        response.json().then(json => {
                            console.log("401_2:", json)
                        })
                    }else {
                        console.log("else_1:", response)
                        response.json().then(json => {
                            console.log("else_2:", json)
                        })
                    }
                })
                .catch(e => {
                    console.log("error", e)
                });
            e.preventDefault();
        }
    }
}
</script>

<style>
</style>
