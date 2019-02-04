<template>
    <div class="wrapper__settings">
        <div class="wrapper__create">
            <app-create :editItem="this.getEditSms"></app-create>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getSmses" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
    </div>
</template>

<script>
import Create from './Create'
import Items from './Item'
import Table from '../../../../../semantic-blocks/forms/Table'

import { mapGetters } from 'vuex';

export default {
    components: {
        AppCreate: Create,
        AppItem: Items,
        AppTable: Table
    },
    data () {
        return {
            offset: 10,
            columns: [
                {
                    field: 'id',
                    name: 'ID',
                    sort: 1,
                    isShow: true,
                    width: "50px",
                },
                {
                    field: 'host',
                    name: 'Хост',
                    sort: 2,
                    isShow: true,
                    width: "300px",
                },
                {
                    field: 'port',
                    name: 'Порт',
                    sort: 3,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'login',
                    name: 'Логин',
                    sort: 4,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'isStatus',
                    name: 'Статус',
                    sort: 5,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 6,
                    isShow: true,
                    width: "300px",
                },
            ]
        }
    },
    created(){
        this.$store.dispatch('getSmsesAsync');
    },
    methods: {
        editItem: function(item) {
            this.$store.commit("setSms", item);
        },
        deleteItem: function(id){
            this.$store.dispatch("deleteSmsAsync", {id: id});
        }
    },
    computed: {
        ...mapGetters([
            'getSmses', 'getEditSms'
        ]),
        sorted(){
            this.columns = this._.sortBy(this.columns, 'sort');
            return this.columns;
        },
        getTable(){
            return this.sorted;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
