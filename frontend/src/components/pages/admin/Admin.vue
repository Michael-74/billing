<template>
    <div class="container container_admin" :class="{container_settings:getRouteSetting}" ref="container">

        <div class="nav_wrapper" ref="nav">
            <app-nav :pages="getRoutes"></app-nav>
        </div>
        <div class="background-nav"></div>
        <div class="main_wrapper" ref="body">
            <app-header></app-header>
            <app-body></app-body>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
import Header from '../../semantic-blocks/Header.vue'
import Body from '../../semantic-blocks/Body.vue'
import Nav from '../../semantic-blocks/Nav.vue'

export default {
    components: {
        appHeader: Header,
        appBody: Body,
        appNav: Nav
    },
    data() {
        return {

        }
    },
    mounted() {

    },
    computed: {
        getRoutes () {
            const routes = this.$router.options.routes.filter(item => {
                if(item.path === "/admin") {
                    return item;
                }
            });
            return routes.length !== 0 ? routes[0].children : [];
        },
        getRouteSetting(){
            return this.$route.name === "Settings"
        }
    },
    watch: {

    },
    methods: {

    }
}
</script>

<style lang="scss">
    .container {
        height: 100%;
        background: #ecf1f3;
    }
    .container_settings {
        background: #fff;
    }
    .nav_wrapper {
        height: 100%;
        width: 220px;
        //float: left;
        position: absolute;
        top: 0;
        left: 0;
    }
    .main_wrapper {
        //float: left;
        //width: calc(100% - 220px);
        margin-left: 220px;
    }
    .background-nav {
        position: fixed;
        top: 0;
        left: 100%;
        background: rgba(0,0,0,0.4);
        width: 100%;
        height: 100%;
        z-index: 2;
        margin-left: 220px;
        transition: opacity 0.3s ease;
        opacity: 0;
    }
    /* TODO:: затемнение экрана
    .nav_wrapper:hover + .background-nav {
        left: 0;
        opacity: 1;
    }
    */
</style>
