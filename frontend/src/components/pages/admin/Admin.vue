<template>
    <div class="container container_admin" ref="container">
        <div class="nav_wrapper" ref="nav">
            <app-nav :pages="adminPages"></app-nav>
        </div>
        <div class="main_wrapper" ref="body">
            <app-header></app-header>
            <app-body></app-body>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
import Vue from 'vue';
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
            adminPages: [
                {
                    name: 'Абоненты',
                    link: '/admin/clients',
                    icon: 'user'
                },
                {
                    name: 'Интернет тарифы',
                    link: '/admin/internet',
                    icon: 'user'
                },
                {
                    name: 'Смотрешка',
                    link: '/admin/tv',
                    icon: 'tv'
                },
                {
                    name: 'Оборудование',
                    link: '/admin/set',
                    icon: 'user'
                },
                {
                    name: 'Услуги',
                    link: '/admin/service',
                    icon: 'user'
                },
                {
                    name: 'Финансы',
                    link: '/admin/bussines',
                    icon: 'user'
                }
            ],
        }
    },
    mounted() {
        //this.h = this.$refs.container.clientHeight;
    },
    computed: {

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
</style>
