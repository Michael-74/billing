<template>
    <div id="app">
        <div class="content">
            <div class="nprog">
                <nprogress-container></nprogress-container>
            </div>
            <router-view></router-view>
            <modals-container name="modal1"></modals-container>
        </div>
        <div class="footer"></div>
        <notifications group="notify" classes="notify"/>
    </div>
</template>

<script>
import NprogressContainer from 'vue-nprogress/src/NprogressContainer'

export default {
    components: {
        NprogressContainer
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
}
.nprog {
    position: fixed;
    top: 0;
    left: 0;
    height: 10px;
    width: 100%;
}
.nprog .nprogress-container {
    height: 10px;
}
</style>
