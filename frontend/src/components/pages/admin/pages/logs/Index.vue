<template>
    <div class="index">
        <div class="clients__filters">
            <app-type-filter></app-type-filter>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getLogs" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
    </div>
</template>

<script>
import TypeFilter from "./TypeFilter"
import Table from '../../../../semantic-blocks/forms/Table'
import { mapGetters } from 'vuex';

export default {
    components: {
        AppTypeFilter: TypeFilter,
        AppTable: Table
    },
    data () {
        return {
            columns: [
                {
                    field: 'id',
                    name: 'ID',
                    sort: 1,
                    isShow: true,
                    width: "50px",
                },
                {
                    field: 'url',
                    name: 'Строка запроса',
                    sort: 2,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'service',
                    name: 'Название услуги',
                    sort: 3,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'method',
                    name: 'Название метода',
                    sort: 4,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'userId',
                    name: 'Администратор',
                    sort: 6,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'clientId',
                    name: 'Абонент',
                    sort: 7,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'request',
                    name: 'Тело запроса',
                    sort: 8,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'response',
                    name: 'Тело ответа',
                    sort: 9,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 70,
                    isShow: true,
                    width: "300px",
                },
            ]
        }
    },
    created() {
        if(!this.getLogs.length) {
            this.$store.dispatch('searchLogAsync');
        }
    },
    methods: {
        editItem(item) {
            // null
        },
        deleteItem(id) {
            // null
        }
    },
    computed: {
        ...mapGetters([
            'getLogs'
        ]),
        getTable(){
            return this._.sortBy(this.columns, "sort");
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
