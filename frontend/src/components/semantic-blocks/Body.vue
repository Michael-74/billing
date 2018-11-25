<template>
    <div class="body">
        <app-breadcrumbs></app-breadcrumbs>
        <router-view></router-view>
    </div>
</template>

<script>

import Breadcrumbs from '../semantic-blocks/Breadcrumbs.vue'

export default {
    components: {
        appBreadcrumbs: Breadcrumbs
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss" scoped>
    .body {
        padding: 40px 20px 0 20px;
    }
</style>
