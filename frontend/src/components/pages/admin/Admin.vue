<template>
    <div class="container container_admin" ref="container">
        <div class="nav_wrapper" ref="nav">
            <app-nav :pages="adminPages"></app-nav>
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
                    icon: 'globe'
                },
                {
                    name: 'Смотрешка',
                    link: '/admin/tv',
                    icon: 'tv'
                },
                {
                    name: 'Оборудование',
                    link: '/admin/rent',
                    icon: 'cog'
                },
                /*
                {
                    name: 'Услуги',
                    link: '/admin/service',
                    icon: 'briefcase'
                },
                */
                {
                    name: 'Финансы',
                    link: '/admin/wallet',
                    icon: 'wallet'
                },
                {
                    name: 'Логи',
                    link: '/admin/logs',
                    icon: 'clock'
                },
                {
                    name: 'Рассылка',
                    link: '/admin/mail',
                    icon: 'envelope'
                },
                {
                    name: 'Настройка',
                    link: '/admin/settings',
                    icon: 'toggle-on'
                }
            ],
        }
    },
    mounted() {

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
    .nav_wrapper:hover + .background-nav {
        left: 0;
        opacity: 1;
    }
</style>
