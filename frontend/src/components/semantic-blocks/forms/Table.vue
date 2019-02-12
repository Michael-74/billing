<template>
    <div class="items__clients">
        <div class="wrapper-cog">
            <app-setting-table :columns="getСolumns"></app-setting-table>
        </div>
        <div class="items__tables">
            <div class="items__table-fix">
                <table class="items__table">
                    <thead class="items__thead">
                    <tr>
                        <th class="items__th items__th_square" @click="selectAllItems">
                            <font-awesome-icon class="select__icon select__icon_white" v-if="isSelectAllItems" icon="check-square"></font-awesome-icon>
                            <font-awesome-icon class="select__icon select__icon_white" v-else icon="square"></font-awesome-icon>
                        </th>
                        <th class="items__th"
                            v-for="column in getСolumns"
                            :style="{width: column.width}"
                            @click="sort(column.field)"
                            v-show="column.isShow"
                        >{{ column.name }}</th>
                        <th class="items__th items__td_setting">Действие</th>
                    </tr>
                    </thead>
                    <tbody class="items__tbody">
                    <tr v-for="item in getRows" :key="item.id"></tr>
                    </tbody>
                </table>
            </div>
            <div class="items__table-source">
            <table class="items__table">
                <thead class="items__thead items__thead_hide">
                <tr>
                    <th class="items__th items__th_square">
                        <font-awesome-icon class="select__icon select__icon_blue" icon="square"></font-awesome-icon>
                    </th>
                    <th class="items__th"
                        v-for="column in getСolumns"
                        :style="{width: column.width}"
                        v-show="column.isShow"
                    >{{ column.name }}</th>
                    <th class="items__th items__td_setting">Действие</th>
                </tr>
                </thead>
                <tbody class="items__tbody">
                <tr v-for="item in getRows" :key="item.id">
                    <td class="items__td items__th_square" @click="checkItem(item.id)">
                        <font-awesome-icon class="select__icon select__icon_blue" v-if="isCheckItem(item.id)" icon="check-square"></font-awesome-icon>
                        <font-awesome-icon class="select__icon select__icon_blue" v-else icon="square"></font-awesome-icon>
                    </td>
                    <td v-if="col.field !== 'isStatus'" v-show="col.isShow" class="items__td" v-for="col in getСolumns">{{ item[col.field] }}</td>
                    <td v-else-if="col.field === 'createdAt'" class="items__td">
                        {{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}
                    </td>
                    <td v-else-if="col.field === 'isStatus'" v-show="col.isShow" class="items__td items_nowrap">
                        <span v-show="item.isStatus">
                            <span class="items__signal"></span>
                            <span class="items__status-text">Включен</span>
                        </span>
                        <span v-show="!item.isStatus">
                            <span class="items__signal items__signal_off"></span>
                            <span class="items__status-text">Выключен</span>
                        </span>
                    </td>
                    <td class="items__td items__td_setting">
                        <font-awesome-icon class="items__icon" icon="cog" @click="edit(item)"></font-awesome-icon>
                        <font-awesome-icon class="items__icon" icon="times-circle" @click="del(item.id)"></font-awesome-icon>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import SettingTable from '../../logic-blocks/SettingTable'

export default {
    props: ["columns","rows", "editItem", "deleteItem"],
    components: {
        FontAwesomeIcon,
        AppSettingTable: SettingTable
    },
    data () {
        return {
            flagSort: true,
            isSelectAllItems: false,
            items: []
        }
    },
    methods: {
        byField(field) {
            return function(a, b) {
                return a[field] > b[field] ? 1 : -1;
            }
        },
        byFieldEnd(field) {
            return function(a, b) {
                return a[field] < b[field] ? 1 : -1;
            }
        },
        filterIt(arr, field, string) {
            return arr.filter(function(item) {
                return item[field].search(string) !== -1;
            });
        },
        search(arr, field, string){
            return this.filterIt(arr, field, string);
        },
        sort(field){
            if(this.flagSort) {
                this.getRows.sort(this.byField(field));
                this.flagSort = !this.flagSort;
            } else {
                this.getRows.sort(this.byFieldEnd(field));
                this.flagSort = !this.flagSort;
            }
        },
        edit(item){
            this.editItem(item);
        },
        del(id){
            this.deleteItem(id);
        },
        isCheckItem(id) {
            return this.items.indexOf(id) !== -1;
        },
        checkItem(id) {
            if(!this.isCheckItem(id)){
                this.items.push(id);
            } else {
                let idx = this.items.indexOf(id);
                this.items.splice(idx, 1);
            }

            if(this.isDiffRowsAndItems()) {
                this.isSelectAllItems = true
            } else {
                this.isSelectAllItems = false
            }
        },
        isDiffRowsAndItems() {
            return this.getRows.length === this.items.length
        },
        selectAllItems() {
            this.isSelectAllItems = true
            if(this.isDiffRowsAndItems()) {
                this.isSelectAllItems = false
                this.items = [];
            } else {
                this.items = this._.map(this.getRows, 'id');
            }
        }
    },
    computed: {
        getСolumns() {
            return this.columns;
        },
        getRows() {
            return this.rows;
        },
        /* TODO:: доработать пагинацию
        countPages(){
            return _.chunk(this.getRows, 10);
        }
        */
    }
}
</script>

<style scoped>
.items__clients {
    /*background: #FFF;*/
    /*margin: 20px 0;*/
}
.items__tables {
    background: #FFF;
    /*border-radius: 4px;*/
    box-shadow: 1px 2px 10px 2px rgba(0, 0, 0, 0.2);
    width: 100%;
    padding-bottom: 20px;
}
.items__table {
    width: 100%;
}
.items__td_setting {
    width: 80px;
}
.items__table-source {
    max-height: 600px;
    overflow: scroll;
    padding-bottom: 20px;
}
.items__thead_hide {
    visibility: hidden;
}
.items__thead_hide .items__th {
    height: 0px;
}
.items__table-source .items__thead th {
    padding: 0px;
    font-size: 0px;
}

</style>
