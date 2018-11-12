<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

export default {
    name: 'app',
    data() {
        return {
            msg: 'Современное приложение!'
        }
    }
}
</script>

<style lang="scss">
#app {

}
</style>
