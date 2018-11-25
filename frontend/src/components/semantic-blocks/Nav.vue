<template>
    <div class="nav">
        <div class="nav__logo">
            <img class="nav__logo-img" src="../../assets/images/nav/logo_white.png" alt="logo">
        </div>
        <ul class="nav__ul">
            <i class="fas fa-coffee fa-xs"></i>
            <li class="nav__li" v-for="page in pages">
                <router-link :to='{path: page.link}' tag="a" class="nav__a" exact active-class="nav__a_active">
                    <font-awesome-icon class="nav__icon" :icon='page.icon'></font-awesome-icon>
                    {{page.name}}
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
    .nav__li .nav__a{
        color: #FFF;
    }
    .nav__a {
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
</style>
