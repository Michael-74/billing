<template>
    <div>
        <h1>{{ msg }}</h1>
        <img src="../../assets/images/logo.png">
        <router-link to='/user/' tag="div" active-class="nav__li_active">
            <a>User</a>
        </router-link>
    </div>
</template>

<script>

export default {
    data () {
        return {
            msg: 'Main'
        }
    }
}
</script>

<style>
</style>
