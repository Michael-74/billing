<template>
    <div class="breadcrumbs">
        <router-link to='/admin/clients' tag="a" class="breadcrumbs__a">
            Админ панель
        </router-link>
        <span class="breadcrumbs__delimitr"></span>
        <span class="breadcrumbs__a breadcrumbs__a_active">
            Абоненты
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
        padding: 5px 10px;
        color: #97abc3;
    }
    .breadcrumbs__a_active, .breadcrumbs__a:hover {
        color: #333333;
    }
    .breadcrumbs__delimitr {
        border-left: 1px solid #d7e0e7;
    }
</style>
