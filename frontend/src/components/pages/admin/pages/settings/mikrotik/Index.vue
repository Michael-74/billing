<template>
    <div class="wrapper__settings">
        <div class="wrapper__create">
            <app-create :editItem="this.getEditMikrotik"></app-create>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getMikrotiks" :editItem="editItem" :deleteItem="deleteItem"></app-table>
        </div>
    </div>
</template>

<script>
import Create from './Create'
import Table from '../../../../../semantic-blocks/forms/Table'
import { mapGetters } from 'vuex';

export default {
    components: {
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
                    field: 'host',
                    name: 'Хост',
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
                    field: 'isStatus',
                    name: 'Статус',
                    sort: 4,
                    isShow: true,
                    width: "200px",
                },
                {
                    field: 'createdAt',
                    name: 'Дата создания',
                    sort: 5,
                    isShow: true,
                    width: "300px",
                },
            ]
        }
    },
    created(){
        this.$store.dispatch('getMikrotiksAsync');
    },
    methods: {
        editItem: function(item) {
            this.$store.commit("setMikrotik", item);
        },
        deleteItem: function(id){
            this.$store.dispatch("deleteMikrotikAsync", {id: id});
        }
    },
    computed: {
        ...mapGetters([
            'getMikrotiks', 'getEditMikrotik'
        ]),
        getTable(){
            return this._.sortBy(this.columns, 'sort');
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
