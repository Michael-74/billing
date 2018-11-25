<template>
    <div id="app">
        <router-view></router-view>
        <modals-container name="modal1"></modals-container>
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
    position: relative;
    min-width: 1240px;
    max-width: 100%;
    height: 100%;
}
</style>
