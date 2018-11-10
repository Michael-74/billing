<template>
    <div>
        <h1>User Show {{ id }}</h1>
        <ul>
            <li>id: {{ user.id }}</li>
            <li>ФИО: {{ user.fio }}</li>
            <li>Email: {{ user.email }}</li>
        </ul>
    </div>
</template>

<script>

import axios from "axios";

export default {
    props: ['id'],
    data () {
        return {
            user: null
        }
    },
    created() {
        this.user = this.id;
        axios
            .get('/admin/v1/user/' + this.id)
            .then(response => {
                this.user = response.data;
                console.log("response", this.user)
              })
            .catch(e => {
                this.errors.push(e);
                console.log("error", e)
            });
    }
}
</script>

<style>
</style>
