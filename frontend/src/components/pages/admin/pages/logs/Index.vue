<template>
    <div class="index">
        <div class="clients__filters">
            <app-type-filter></app-type-filter>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getClients" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
    </div>
</template>

<script>
import TypeFilter from "./TypeFilter"
import Table from '../../../../semantic-blocks/forms/Table'

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
                    field: 'name',
                    name: 'Название',
                    sort: 2,
                    isShow: true,
                    width: "300px",
                },
                {
                    field: 'speed',
                    name: 'Скорость',
                    sort: 3,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'tasks',
                    name: 'Задачи',
                    sort: 4,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'isStatus',
                    name: 'Статус',
                    sort: 6,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 7,
                    isShow: true,
                    width: "300px",
                },
            ]
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
        getTable(){
            return this._.sortBy(this.columns, "sort");
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
