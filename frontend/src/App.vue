<template>
    <div id="app">
        <div class="content">
            <router-view></router-view>
            <modals-container name="modal1"></modals-container>
        </div>
        <div class="footer"></div>
        <notifications group="notify" classes="notify"/>
        <app-loader></app-loader>
    </div>
</template>

<script>
import Loader from './components/logic-blocks/Loader';

export default {
    components: {
        AppLoader: Loader,
    },
    name: 'app',
    data() {
        return {
            msg: 'Союз-ком'
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
    background: #ecf1f3;
}
</style>
