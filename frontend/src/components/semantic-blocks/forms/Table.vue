<template>
    <div class="items__clients">
        <div class="wrapper-cog">
            <app-setting-table :columns="getColumns"></app-setting-table>
        </div>
        <div class="items__wrapper-tables">
            <div class="items__tables">
                <!--<div class="items__table-fix2">
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
                </div>-->
                <div class="items__table-source">
                <table class="items__table">
                    <thead class="items__thead items__thead_hide">
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
                    <tr v-for="item in getRows" :key="item.id" id="myFirstMenu"  @contextmenu.prevent.stop="handleClick($event, item)">
                        <td class="items__td items__th_square" @click="checkItem(item.id)">
                            <font-awesome-icon class="select__icon select__icon_blue" v-if="isCheckItem(item.id)" icon="check-square"></font-awesome-icon>
                            <font-awesome-icon class="select__icon select__icon_blue" v-else icon="square"></font-awesome-icon>
                        </td>

                        <td :style="{width: col.width}" v-if="col.field !== 'isStatus' && col.field !== 'tasks' && col.field !== 'typeDiscount' && col.field !== 'isPromisedPay'  && col.field !== 'note' && col.field !== 'internet' && col.field !== 'tvs' && col.field !== 'rents' && col.field !== 'balance'"
                            v-show="col.isShow" class="items__td" v-for="col in getСolumns">{{ item[col.field] }}</td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'tasks'" v-show="col.isShow"  class="items__td">
                            <div class="" v-for="task in item[col.field]">
                                <span class="items__pack">{{ task.name }}</span>
                            </div>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'balance'" v-show="col.isShow" class="items__td">{{ formatPriceLocal(item.balance) }}</td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'internet'" v-show="col.isShow" class="items__td">
                            <div v-if="item.internet !== null">
                                <span class="items__pack" v-if="isSearchNameForId('getInternets', item.internet.id)">{{ searchNameForId("getInternets", item.internet.id) }}</span>
                            </div>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'tvs'" v-show="col.isShow" class="items__td">
                            <div v-if="item.tvs.length !== 0">
                                <div v-for="tv in item.tvs">
                                    <span class="items__pack" v-if="isSearchNameForId('getTvs', tv.id)">{{ searchNameForId("getTvs", tv.id) }}</span>
                                </div>
                            </div>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'rents'" v-show="col.isShow" class="items__td">
                            <div v-if="item.rents.length !== 0">
                                <div v-for="rent in item.rents">
                                    <span class="items__pack" v-if="isSearchNameForId('getRents', rent.id)">{{ searchNameForId("getRents", rent.id) }}</span>
                                </div>
                            </div>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'typeDiscount'" v-show="col.isShow" class="items__td">
                            {{ item.typeDiscount === "discount10" ? "Скидка до 10%" : ""}}
                            {{ item.typeDiscount === "discount20" ? "Скидка до 20%" : "" }}
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'isPromisedPay'" v-show="col.isShow" class="items__td items_nowrap">
                                <span v-show="item.isPromisedPay">
                                    <span class="items__signal"></span>
                                    <span class="items__status-text">Включен</span>
                                </span>
                            <span v-show="!item.isPromisedPay">
                                    <span class="items__signal items__signal_off"></span>
                                    <span class="items__status-text">Выключен</span>
                                </span>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'createdAt'" class="items__td">
                            {{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'isStatus'" v-show="col.isShow" class="items__td items_nowrap">
                            <span v-show="item.isStatus">
                                <span class="items__signal"></span>
                                <span class="items__status-text">Включен</span>
                            </span>
                            <span v-show="!item.isStatus">
                                <span class="items__signal items__signal_off"></span>
                                <span class="items__status-text">Выключен</span>
                            </span>
                        </td>

                        <td :style="{width: col.width}" v-else-if="col.field === 'note'" v-show="col.isShow" class="items__td">
                            <font-awesome-icon class="items__icon"
                                               :class="{'items__icon_orange': item.note}"
                                               icon="comment-alt"
                                               v-tooltip="{
                                                           content: item.note,
                                                           placement: 'top-center',
                                                           classes: ['info'],
                                                           targetClasses: ['it-has-a-tooltip'],
                                                           offset: 10,
                                                           delay: {
                                                               show: 200,
                                                               hide: 100,
                                                           }
                                                       }"
                            ></font-awesome-icon>
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
        <vue-simple-context-menu
            :elementId="getTypeName()"
            :options="optionsArray"
            :ref="getTypeName()"
            @optionClicked="optionClicked">
        </vue-simple-context-menu>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import SettingTable from '../../logic-blocks/SettingTable'
import { formatPrice, addCashModal } from "../../../util/helpers";
import { mapGetters } from 'vuex';

export default {
    props: ["columns","rows", "editItem", "deleteItem", "typeSetting"],
    components: {
        FontAwesomeIcon,
        AppSettingTable: SettingTable
    },
    data () {
        return {
            flagSort: true,
            isSelectAllItems: false,
            items: [],
            optionsArray: this.getOptions()
        }
    },
    methods: {
        formatPriceLocal (price) {
            return formatPrice(price);
        },
        getTypeName(){
            return "vueSimpleContextMenu1" + this.typeSetting;
        },
        handleClick (event, item) {
            this.$refs[this.getTypeName()].showMenu(event, item)
        },
        optionClicked (event) {
            switch(event.option.slug) {
                case "addCash":
                    addCashModal(event.item)
                    break;
                case "edit":
                    this.editItem(event.item);
                    break;
                case "delete":
                    this.deleteItem(event.item.id);
                    break;
            }
        },
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

            this.isSelectAllItems = !!this.isDiffRowsAndItems();
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
        },
        isSearchNameForId(nameGetter, id){
            const name = this[nameGetter].filter(item => {
                if(item.id === id){
                    return item.name;
                }
            });

            return name.length !== 0;
        },
        searchNameForId(nameGetter, id){
            const name = this[nameGetter].filter(item => {
                if(item.id === id){
                    return item.name;
                }
            });

            return name.length !== 0 ? name[0].name : null;
        },
        /**
         * Определяем функционал для контекстного меню
         * в зависимости от страницы
         **/
        getOptions() {
            let options = [];

             if(this.$route.name === "Clients") {
                 options.push({
                     name: 'Добавить на счет',
                     slug: 'addCash'
                 });
             }

             options.push({
                    name: 'Отредактировать',
                    slug: 'edit'
                });
             options.push({
                    name: 'Удалить',
                    slug: 'delete'
                });

            return options;
        }
    },
    computed: {
        ...mapGetters([
            'getTvs', 'getInternets', 'getRents'
        ]),
        getColumns() {
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
.items__wrapper-tables {

}
.items__tables {
    border-radius: 4px;
    box-shadow: 1px 2px 10px 2px rgba(0, 0, 0, 0.2);
    width: 100%;
    padding-bottom: 20px;
}
.items__table {
    width: 100%;
}
.items__tbody {
    background: #FFF;
}
.items__td_setting {
    width: 80px;
}
.items__table-source {
    max-height: 600px;
    overflow-x: scroll;
    padding-bottom: 20px;
}
/*
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
*/
</style>
