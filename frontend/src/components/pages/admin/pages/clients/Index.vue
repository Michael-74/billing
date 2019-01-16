<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client :state="this.stateProps"></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client :editItem="selectedItem"></app-create-client>
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
            stateProps: {
                loyalty: [{id:1, val:1}, {id:2, val:2}, {id:3, val:3}, {id:4, val:4}, {id:5, val:5}, {id:6, val:6}, {id:7, val:7}, {id:8, val:8}, {id:9, val:9}, {id:10, val:10}],
                typeDiscount: [{id:"discount10", val:"Скидка 10%"}, {id:"discount20", val:"Скидка 20%"}],
                switchedOffOn: [{id:false, val:"Выключен"}, {id:true, val:"Включен"}],
            }
        }
    },
    created() {
        if(!this.$store.getters.getClients.length) {
            this.$store.dispatch('getClientsAsync');
        }

        addClient(data => {
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
            return this.$store.getters.getEditClient;
        },
        getClients () {
            return this.$store.getters.getClients.slice().reverse();
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
