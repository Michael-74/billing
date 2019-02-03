<template>
    <div class="header_wrapper">
        <div class="header">
            <button class="header__slide-nav header__js-slide-nav" @click="slideNav">
                <font-awesome-icon class="header__slide-nav-bars" icon='bars'></font-awesome-icon>
            </button>
            <div class="header__search header_left search">
                <div class="search__block">
                    <font-awesome-icon class="search__icon-search" icon='search'></font-awesome-icon>
                    <input type="text" class="search__input" placeholder="Поиск по системе">
                    <button class="search__submit">Найти</button>
                </div>
            </div>
            <div class="header__settings header_right">
                <router-link to='/admin/clients' tag="a" class="header__a" exact active-class="header__a_active">
                    <font-awesome-icon class="header__icon" icon='cog' @click="logOut"></font-awesome-icon>
                </router-link>
            </div>
            <div class="header__lk header_right">
                <router-link to='/user/lk' tag="a" class="header__user-lk" exact active-class="header__user-lk_active">
                    <font-awesome-icon class="header__user-icon" icon='user'></font-awesome-icon>
                    <div class="header__user-name">
                        {{ getName }}
                    </div>
                </router-link>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import { mapMutations, mapGetters } from 'vuex';

export default {
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
            name: this.$store.getters.getUser
        }
    },
    methods: {
        ...mapMutations([
            'changeNavMin'
        ]),
        logOut: function(e) {
            this.$store.commit('logOut');
            this.$router.push('/');

            e.preventDefault();
        },
        slideNav() {
            this.changeNavMin();
        }
    },
    computed: {
        getName () {
            return this.name.username;
        }
    }
}
</script>

<style lang="scss" scoped>
    $height: 70px;
    .header {
        background: #FFF;
        height: $height;
        width: 100%;
        box-shadow: 5px 5px 30px 4px rgba(0,0,0,0.2);
    }
    .header_left {
        float:left;
    }
    .header__search {
        padding: 12px 10px 0 10px;
    }
    .search__block {
        padding: 0 0 0 20px;
        background: #ecf4fd;
        border-radius: 4px;
    }
    .search__icon-search {
        color: #91a6bf
    }
    .search__input {
        background: #ecf4fd;
        border: 0px;
        padding: 0 20px;
        width: 400px;
    }
    .search__submit {
        background: #91a6bf;
        font-weight: bold;
        color: #FFF;
        border: 0px;
        padding: 15px 20px;
        border-radius: 4px;
        cursor: pointer;

    }
    .search__submit:hover {
        background: #91a6af;
    }
    .search__submit:active {
        box-shadow: inset 2px 2px 6px rgba(0,0,0,0.3);
    }
    .header_right {
        height: $height;
        float:right;
        border-left: 1px solid #f3f3f3;
    }
    .header__a {
        display: block;
        padding: 20px;
    }
    .header__lk {
        padding: 20px;
    }
    .header__user-lk {
        color: #2b87db;
        margin-top: -10px;
    }
    .header__user-name {
        display: inline-block;
        text-decoration: underline;
        vertical-align: super;
        padding: 0 15px;
    }
    .header__user-icon {
        display: inline-block;
        font-size: 30px;
        color: #e6e6e6;
    }
    .header__icon {
        color: #e6e6e6;
        font-size: 30px;
    }
    .header__slide-nav {
        border: 1px solid #2b87db;
        background: #FFF;
        padding: 12px 15px;
        border-radius: 50%;
        float: left;
        margin: 14px 10px 10px;
    }
    .header__slide-nav-bars {
        color: #2b87db;
    }
    .header__slide-nav:hover {
        background: #2b87db;
        cursor: pointer;

        .header__slide-nav-bars {
            color: #FFF;
        }
    }
</style>
