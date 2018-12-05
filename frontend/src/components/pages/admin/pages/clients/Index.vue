<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client></app-create-client>
        </div>
        <div class="clients__items">
            <app-item-client :data="getClients"></app-item-client>
        </div>
    </div>
</template>

<script>
import FilterClients from './Filter'
import CreateClients from './Create'
import ItemClients from './Item'
import axios from "axios";
import { addClient } from "../../../../../util/ws";
import { deleteClient } from "../../../../../util/ws";

export default {
    components: {
        AppFilterClient: FilterClients,
        AppCreateClient: CreateClients,
        AppItemClient: ItemClients,
    },
    data () {
        return {

        }
    },
    mounted () {

    },
    created() {
        this.$store.dispatch('getClientsAsync', {token: this.$store.getters.getUser.token});

        addClient(data => {
            console.log("ХУЙ знает добавить", data)
            this.$store.commit('pushClients', data);
        });

        deleteClient(data => {
            console.log("ХУЙ знает удалить", data)
            this.$store.commit('deleteClient', data);
        });
    },
    computed: {
        getClients () {
            return this.$store.getters.getClients;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
