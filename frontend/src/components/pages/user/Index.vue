<template>
    <div>
        <h1>User</h1>
        <div v-for="user in users">
            <ul>
                <li>{{user.id}}</li>
                <li>{{user.username}}</li>
                <li>{{user.email}}</li>
                <li>{{user.email}}</li>
            </ul>
        </div>
    </div>
</template>

<script>

import axios from "axios";

export default {
    data () {
        return {
            users: null
        }
    },
    created() {
        axios
            .get('/admin/v1/user/')
            .then(response => {
                this.users = response.data;
                console.log("response", this.users)
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
