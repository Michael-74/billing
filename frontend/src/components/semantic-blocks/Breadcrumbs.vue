<template>
    <div class="breadcrumbs">
        <span class="breadcrumbs__items" v-for="route in this.$route.matched">
            <router-link :to='route.path' tag="a" class="breadcrumbs__a" exact active-class="breadcrumbs__a_active">
                {{route.meta.name}}
            </router-link>
            <span class="breadcrumbs__delimitr"></span>
        </span>
    </div>
</template>

<script>

export default {
    data () {
        return {

        }
    }
}
</script>

<style lang="scss" scoped>
    .breadcrumbs {
        border-bottom: 2px solid #e3eaec;
        margin-bottom: 20px;
    }
    .breadcrumbs__a:first-child {
        padding-left: 0;
    }
    .breadcrumbs__a {
        display: inline-block;
        font-size: 16px;
        padding: 5px 5px;
        color: #97abc3;
    }
    .breadcrumbs__a_active, .breadcrumbs__a:hover {
        color: #333333;
    }
    .breadcrumbs__delimitr {
        border-left: 1px solid #d7e0e7;
        padding: 0 5px;
    }
    .breadcrumbs__items:last-child .breadcrumbs__delimitr {
        display: none;
    }
</style>
