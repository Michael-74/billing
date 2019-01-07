<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client :editItem="selectedItem" :getInternets="getInternets"></app-create-client>
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
            editItem: null
        }
    },
    created() {
        if(!this.$store.getters.getClients.length) {
            this.$store.dispatch('getClientsAsync');
        }

        addClient(data => {
            console.log("addClient Index", data)
            this.$store.commit('pushClients', data);
        });

        deleteClient(data => {
            this.$store.commit('deleteClient', data);
            this.$notify({
                group: 'notify',
                type: 'success ',
                text: 'Абонент успешно удален'
            });
        });
    },
    computed: {
        selectedItem: function() {
            return this.editItem = this.$store.getters.getEditClient;
        },
        getClients () {
            return this.$store.getters.getClients;
        },
        getInternets(){
            console.log("---", this.$store.getters.getInternets);
            //return this.$store.getters.getInternets;
            return this.$store.getters.getInternets;
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
