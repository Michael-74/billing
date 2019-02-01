<template>
    <div class="wrapper__settings">
        <div class="wrapper__create">
            <app-create :editItem="selectedItem"></app-create>
        </div>
        <div class="wrapper__item">
            <app-item :data="getItems"></app-item>
            <app-table :data="getTable"></app-table>
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
            editItem: null,
            table: {
                columns: [{
                    field: 'id',
                    name: 'ID',
                    sort: 0,
                    isShow: true,
                    width: "50px",
                },
                {
                    field: 'host',
                    name: 'Хост',
                    sort: 0,
                    isShow: true,
                    width: "300px",
                },
                {
                    field: 'port',
                    name: 'Порт',
                    sort: 0,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'login',
                    name: 'Логин',
                    sort: 0,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'isStatus',
                    name: 'Статус',
                    sort: 0,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 0,
                    isShow: true,
                    width: "300px",
                },
                ],
                rows: []
            }
        }
    },
    created(){
        this.$store.dispatch('getSmsesAsync');
    },
    watch: {
        getSmses(){
            return this.table.rows = this.getSmses
        }
    },
    computed: {
        ...mapGetters([
            'getSmses'
        ]),
        getTable(){
            return this.table;
        },
        selectedItem: function() {
            return this.$store.getters.getEditSms;
        },
        getItems () {
            return this.$store.getters.getSmses;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
