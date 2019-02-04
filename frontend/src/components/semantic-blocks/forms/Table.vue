<template>
    <div class="items__clients">
        <table class="items__table">
            <thead class="items__thead">
            <tr>
                <th class="items__th"
                    v-for="column in getData.columns"
                    :style="{width: column.width}"
                    @click="sort(column.field)"
                    v-show="column.isShow"
                >{{ column.name }}</th>
                <th class="items__th">Действие</th>
            </tr>
            </thead>
            <tbody class="items__tbody">
            <tr v-for="item in getData.rows" :key="item.id">
                <td v-if="col.field !== 'isStatus'" v-show="col.isShow" class="items__td" v-for="col in getData.columns">{{ item[col.field] }}</td>
                <td v-else-if="col.field === 'createdAt'" class="items__td">
                    {{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}
                </td>
                <td v-else-if="col.field === 'isStatus'" class="items__td items_nowrap">
                    <span v-show="item.isStatus">
                        <span class="items__signal"></span>
                        <span class="items__status-text">Включен</span>
                    </span>
                    <span v-show="!item.isStatus">
                        <span class="items__signal items__signal_off"></span>
                        <span class="items__status-text">Выключен</span>
                    </span>
                </td>
                <!--<td class="items__td">{{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}</td>-->
                <td class="items__td">
                    <font-awesome-icon class="items__icon" icon="cog"></font-awesome-icon>
                    <font-awesome-icon class="items__icon" icon="times-circle"></font-awesome-icon>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ["data"],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
            flagSort: true
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
                this.getData.rows.sort(this.byField(field));
                this.flagSort = !this.flagSort;
            } else {
                this.getData.rows.sort(this.byFieldEnd(field));
                this.flagSort = !this.flagSort;
            }
        }
    },
    computed: {
        getData() {
            return this.data;
        }
    }
}
</script>

<style scoped>
.items__clients {
    background: #FFF;
    margin: 20px 0;
}
.items__table {
    background: #FFF;
    border-radius: 4px;
    padding-bottom: 40px;
    box-shadow: 1px 2px 10px 2px rgba(0, 0, 0, 0.2);
    width: 100%;
}

</style>
