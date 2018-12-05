<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client></app-create-client>
        </div>
        <div class="clients__items">
            <app-item-client :data="clients"></app-item-client>
        </div>
    </div>
</template>

<script>
import FilterClients from './Filter'
import CreateClients from './Create'
import ItemClients from './Item'
import axios from "axios";

export default {
    components: {
        AppFilterClient: FilterClients,
        AppCreateClient: CreateClients,
        AppItemClient: ItemClients,
    },
    data () {
        return {
            clients: this.$store.getters.getClients,
        }
    },
    created() {
        this.$store.dispatch('getClientsAsync', {token: this.$store.getters.getUser.token});
    }
}
</script>

<style lang="scss" scoped>

</style>
