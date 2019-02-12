<template>
    <div class="clients">
        <div class="clients__filters">
            <app-filter-client :state="this.stateProps"></app-filter-client>
        </div>
        <div class="clients__create">
            <app-create-client :editItem="getEditClient" :state="this.stateProps"></app-create-client>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getClients" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
        <!--
        <div class="clients__items">
            <app-item-client :data="getClients"></app-item-client>
        </div>
        -->
    </div>
</template>

<script>
import FilterClients from './Filter'
import CreateClients from './Create'
import ItemClients from './Item'

import axios from "axios";
import Table from '../../../../semantic-blocks/forms/Table'
import { mapGetters } from 'vuex';
import { addClient, deleteClient, addCashClientSocket, deleteSendClient, sendAddCashClient } from "../../../../../util/ws";

export default {
    components: {
        AppFilterClient: FilterClients,
        AppCreateClient: CreateClients,
        AppItemClient: ItemClients,
        AppTable: Table
    },
    data () {
        return {
            stateProps: {
                loyalty: [{id:1, val:1}, {id:2, val:2}, {id:3, val:3}, {id:4, val:4}, {id:5, val:5}, {id:6, val:6}, {id:7, val:7}, {id:8, val:8}, {id:9, val:9}, {id:10, val:10}],
                typeDiscount: [{id:"discount10", val:"Скидка 10%"}, {id:"discount20", val:"Скидка 20%"}],
                switchedOffOn: [{id:false, val:"Выключен"}, {id:true, val:"Включен"}],
            },
            columns: [
                {
                    field: 'id',
                    name: 'ID',
                    sort: 1,
                    isShow: true,
                    width: "50px",
                },
                {
                    field: 'fio',
                    name: 'ФИО',
                    sort: 2,
                    isShow: true,
                    width: "300px",
                },
                {
                    field: 'login',
                    name: 'Логин',
                    sort: 3,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'balance',
                    name: 'Баланс',
                    sort: 4,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'contract',
                    name: 'Договор',
                    sort: 5,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'ip',
                    name: 'IP',
                    sort: 6,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'address',
                    name: 'Адрес',
                    sort: 7,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'phone',
                    name: 'Телефон',
                    sort: 8,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'email',
                    name: 'Email',
                    sort: 9,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'discount',
                    name: 'Скидка',
                    sort: 10,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'typeDiscount',
                    name: 'Тип скидки',
                    sort: 11,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'loyalty',
                    name: 'Лояльность',
                    sort: 12,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'isPromisedPay',
                    name: 'Обещанный платеж',
                    sort: 13,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'isStatus',
                    name: 'Статус',
                    sort: 15,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'note',
                    name: 'Заметки',
                    sort: 16,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 17,
                    isShow: true,
                    width: "300px",
                },
                {
                    field: 'internet',
                    name: 'Интернет',
                    sort: 18,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'tvs',
                    name: 'Смотрешка',
                    sort: 19,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'rents',
                    name: 'Аренда',
                    sort: 20,
                    isShow: true,
                    width: "200px",
                },
            ]
        }
    },
    created() {
        if(!this.$store.getters.getClients.length) {
            this.$store.dispatch('getClientsAsync');
        }

        addClient(data => {
            this.$store.commit('pushClients', data);
        });

        addCashClientSocket(data => {
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
    methods: {
        editItem(item) {
            this.$store.commit("setClient", item);
        },
        deleteItem(id) {
            deleteSendClient(id);
        }
    },
    computed: {
        ...mapGetters([
            'getClients', 'getEditClient'
        ]),
        getTable(){
            return this._.sortBy(this.columns, 'sort');
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
