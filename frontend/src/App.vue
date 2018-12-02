<template>
    <div id="app">
        <div class="content">
            <router-view></router-view>
            <modals-container name="modal1"></modals-container>
        </div>
        <div class="footer"></div>
        <notifications group="notify" classes="notify"/>
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
    min-height: 100%;
}
</style>
