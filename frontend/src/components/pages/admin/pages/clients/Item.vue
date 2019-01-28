<template>
    <div class="items">
        <div class="items__clients-wrapper">
            <div class="items__clients">
                <table class="items__table">
                    <thead class="items__thead">
                    <tr>
                        <th class="items__th">N</th>
                        <th class="items__th">Логин</th>
                        <th class="items__th">IP адресс</th>
                        <th class="items__th">Баланс</th>
                        <th class="items__th">Дата посл. платежа</th>
                        <th class="items__th items__th_last-price">Цена до конца месяца</th>
                        <th class="items__th">Договор</th>
                        <th class="items__th">Интернет</th>
                        <th class="items__th">КТВ</th>
                        <th class="items__th">Аренда оборудования</th>
                        <th class="items__th">Обещанный платеж</th>
                        <th class="items__th items__th_fio">ФИО</th>
                        <th class="items__th items__th_address">Адрес</th>
                        <th class="items__th">Телефон</th>
                        <th class="items__th">Email</th>
                        <th class="items__th">Тип скидки</th>
                        <th class="items__th">Скидка</th>
                        <th class="items__th">Статус</th>
                        <th class="items__th">Лояльность</th>
                        <th class="items__th">Уведомления</th>
                        <th class="items__th">Дата создания</th>
                        <th class="items__th">Действие</th>
                    </tr>
                    </thead>
                    <tbody class="items__tbody">
                        <tr v-for="item in data" :key="item.id" @contextmenu.prevent.stop="handleClick($event, item)">
                            <td class="items__td">{{ item.id }}</td>
                            <td class="items__td">{{ item.login }}</td>
                            <td class="items__td">{{ item.ip }}</td>
                            <td class="items__td">{{ formatPriceLocal(item.balance) }}</td>
                            <td class="items__td">{{ item.lastPriceDate }}</td>
                            <td class="items__td">{{ item.lastPrice }}</td>
                            <td class="items__td">{{ item.contract }}</td>
                            <td class="items__td">
                                <div v-if="item.internet !== null">
                                    <span class="items__pack" v-if="isSearchNameForId('getInternets', item.internet.id)">{{ searchNameForId("getInternets", item.internet.id) }}</span>
                                </div>
                            </td>
                            <td class="items__td">
                                <div v-if="item.tvs.length !== 0">
                                    <div v-for="tv in item.tvs">
                                        <span class="items__pack" v-if="isSearchNameForId('getTvs', tv.id)">{{ searchNameForId("getTvs", tv.id) }}</span>
                                    </div>
                                </div>
                            </td>
                            <td class="items__td">
                                <div v-if="item.rents.length !== 0">
                                    <div v-for="rent in item.rents">
                                        <span class="items__pack" v-if="isSearchNameForId('getRents', rent.id)">{{ searchNameForId("getRents", rent.id) }}</span>
                                    </div>
                                </div>
                            </td>
                            <td class="items__td items_nowrap">
                                <span v-show="item.isPromisedPay">
                                    <span class="items__signal"></span>
                                    <span class="items__status-text">Включен</span>
                                </span>
                                <span v-show="!item.isPromisedPay">
                                    <span class="items__signal items__signal_off"></span>
                                    <span class="items__status-text">Выключен</span>
                                </span>
                            </td>
                            <td class="items__td">{{ item.fio }}</td>
                            <td class="items__td">{{ item.address }}</td>
                            <td class="items__td">{{ item.phone }}</td>
                            <td class="items__td">{{ item.email }}</td>
                            <td class="items__td">
                                {{
                                item.typeDiscount === "discount10" ? "Скидка до 10%" : ""
                                }}
                                {{
                                item.typeDiscount === "discount20" ? "Скидка до 20%" : ""
                                }}
                            </td>
                            <td class="items__td">{{ item.discount }}</td>
                            <td class="items__td items_nowrap">
                                <span v-show="item.isStatus">
                                    <span class="items__signal"></span>
                                    <span class="items__status-text">Включен</span>
                                </span>
                                <span v-show="!item.isStatus">
                                    <span class="items__signal items__signal_off"></span>
                                    <span class="items__status-text">Выключен</span>
                                </span>
                            </td>
                            <td class="items__td">{{ item.loyalty }}</td>
                            <td class="items__td">
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
                            <td class="items__td">{{ item.createdAt }}</td>
                            <td class="items__td">
                                <font-awesome-icon class="items__icon" icon="cog" @click="editClient(item)"></font-awesome-icon>
                                <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteClient(item.id)"></font-awesome-icon>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <vue-simple-context-menu
            :elementId="'myFirstMenu'"
            :options="optionsArray"
            :ref="'vueSimpleContextMenu1'"
            @optionClicked="optionClicked">
        </vue-simple-context-menu>
        <!--
        <div class="items__pagination pagination">
            <a class="pagination__link" href="#">1</a>
            <a class="pagination__link pagination__link_active" href="#">2</a>
            <a class="pagination__link" href="#">3</a>
            <a class="pagination__link" href="#">...</a>
            <a class="pagination__link" href="#">5</a>
        </div>
        -->
    </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from 'vuex';
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import { deleteSendClient, sendAddCashClient } from "../../../../../util/ws";
import { formatPrice, addCashModal } from "../../../../../util/helpers";

export default {
    components: {
        FontAwesomeIcon
    },
    props: ['data'],
    data () {
        return {
            optionsArray: [
                {
                    name: 'Добавить на счет',
                    slug: 'addCash'
                },
                {
                    name: 'Отредактировать',
                    slug: 'edit'
                },
                {
                    name: 'Удалить',
                    slug: 'delete'
                }
            ],
        }
    },
    computed: {
        ...mapGetters([
            'getTvs', 'getInternets', 'getRents'
        ]),
    },
    methods: {
        formatPriceLocal (price) {
            return formatPrice(price);
        },
        searchNameForId(nameGetter, id){
            const name = this[nameGetter].filter(item => {
                if(item.id === id){
                    return item.name;
                }
            });

            return name.length !== 0 ? name[0].name : null;
        },
        isSearchNameForId(nameGetter, id){
            const name = this[nameGetter].filter(item => {
                if(item.id === id){
                    return item.name;
                }
            });

            return name.length !== 0;
        },
        handleClick (event, item) {
            this.$refs.vueSimpleContextMenu1.showMenu(event, item)
        },
        optionClicked (event) {
            switch(event.option.slug) {
                case "addCash":
                    addCashModal(event.item)
                    break;
                case "edit":
                    this.editClient(event.item);
                    break;
                case "delete":
                    this.deleteClient(event.item.id);
                    break;
            }
        },
        editClient: function(item) {
            this.$store.commit("setClient", item);
        },
        deleteClient: (clientId) => {
            deleteSendClient(clientId);
        }
    }
}
</script>

<style lang="scss" scoped>
    .items {
        background: #FFF;
        border-radius: 4px;
        padding-bottom: 40px;
        margin-bottom: 40px;
        box-shadow: 1px 2px 10px 2px rgba(0,0,0,0.2);
    }
    .items__table {
        width: 100%;
    }
    .items__clients-wrapper {
        padding-bottom: 40px;
        overflow-x: scroll;
    }
    .items__clients {
        max-height:500px;
        position: relative;
    }
</style>
