<template>
    <div class="nav">
        <div class="nav__logo">
            <img class="nav__logo-img" src="../../assets/images/nav/logo_white.png" alt="logo">
        </div>
        <ul class="nav__ul">
            <i class="fas fa-coffee fa-xs"></i>
            <li class="nav__li" v-for="page in pages">
                <div class="nav__two"></div>
                <router-link :to='{path: page.link}' tag="a" class="nav__a" exact active-class="nav__a_active">
                    <font-awesome-icon class="nav__icon" :icon='page.icon'></font-awesome-icon>
                    {{page.name}}
                    <ul class="nav__second">
                        <li class="nav__li-second">
                            <a class="nav__a-second">Услуги</a>
                        </li>
                        <li class="nav__li-second">
                            <a class="nav__a-second">Обещанный платеж</a>
                        </li>
                        <li class="nav__li-second">
                            <a class="nav__a-second">Тарифы</a>
                        </li>
                        <li class="nav__li-second">
                            <a class="nav__a-second">Интернет</a>
                        </li>
                        <li class="nav__li-second">
                            <a class="nav__a-second">Смотрешка</a>
                        </li>
                    </ul>
                </router-link>
            </li>
        </ul>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['pages'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss" scoped>
    .nav {
        height: 100%;
        font-size: 14px;
        font-weight: bold;
        color: #FFF;
        background: #2b87db;
    }
    .nav__ul {
        padding: 20px 0;
    }
    .nav__logo {
        height: 70px;
        border-bottom: 1px solid #70abe0;
    }
    .nav__logo-img {
        width: 120px;
        height: auto;
        font-size: 0px;
        margin: auto;
        display: block;
        padding: 20px 0;
    }
    .nav__li {
        /*position: relative;*/
    }
    .nav__li .nav__a{
        color: #FFF;
    }
    .nav__a {
        /*position: relative;*/
        display: block;
        padding: 14px 0 14px 30px;
        border-left: 2px solid #2b87db;
    }
    .nav__a:hover, .nav__a_active {
        border-left: 2px solid #FFF;
        background: rgba(255,255,255,0.3);
    }
    .nav__icon {
        width: 15px;
        margin-right: 10px;
        opacity: 0.5;
    }
    .nav__li:hover .nav__second {
        display: block;
    }
    .nav__second {
        position: absolute;
        top: 0;
        left: 220px;
        width: 220px;
        background: #FFF;
        display: none;
        z-index: 2;
    }
    .nav__li-second {

    }
    .nav__a-second {
        font-size: 14px;
        padding: 14px 20px;
        display: block;
        color: #333;
    }
    .nav__a-second:hover {
        color: #2b87db;
    }

    .nav__two {
        position: fixed;
        top:0;
        left: 220px;
        min-height: 100%;
        width: 220px;
        background: #FFF;
        display: none;
        z-index: 1;
    }

    .nav__li:hover .nav__two {
        display: block;
    }
</style>
