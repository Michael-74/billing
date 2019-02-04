<template>
    <div class="wrapper__settings">
        <div class="wrapper__create">
            <app-create :editItem="this.getEditSmotreshka"></app-create>
        </div>
        <div class="wrapper__item">
            <app-table :columns="getTable" :rows="this.getSmotreshkas" :editItem="editItem" :deleteItem="deleteItem"></app-table>
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
                    name: 'URL',
                    sort: 2,
                    isShow: true,
                    width: "300px",
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
    created(){
        this.$store.dispatch('getSmotreshkasAsync');
    },
    methods: {
        editItem: function(item) {
            this.$store.commit("setSmotreshka", item);
        },
        deleteItem: function(id){
            this.$store.dispatch("deleteSmotreshkaAsync", {id: id});
        }
    },
    computed: {
        ...mapGetters([
            'getSmotreshkas', 'getEditSmotreshka'
        ]),
        sorted(){
            this.columns = this._.sortBy(this.columns, 'sort');
            return this.columns;
        },
        getTable(){
            return this._.sortBy(this.columns, 'sort');
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
