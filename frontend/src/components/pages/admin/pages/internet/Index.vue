<template>
    <div class="index">
        <div class="clients__filters">
            <app-filter></app-filter>
        </div>
        <div class="clients__create">
            <app-create :editItem="getEditInternet"></app-create>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getInternets" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
    </div>
</template>

<script>
import Filter from './Filter'
import Create from './Create'
import Table from '../../../../semantic-blocks/forms/Table'
import { mapGetters } from 'vuex';

export default {
    components: {
        AppFilter: Filter,
        AppCreate: Create,
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
    created() {
        if(!this.$store.getters.getInternets.length) {
            this.$store.dispatch('getInternetsAsync');
        }
    },
    methods: {
        editItem: function(item) {
            this.$store.commit("setInternet", item);
            this.$store.commit("setSelectedTasks", item.tasks);
        },
        deleteItem: function(id) {
            this.$store.dispatch("deleteInternetAsync", {id: id});
        }
    },
    computed: {
        ...mapGetters([
            'getInternets', 'getEditInternet'
        ]),
        getTable(){
            return this._.sortBy(this.columns, 'sort');
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
