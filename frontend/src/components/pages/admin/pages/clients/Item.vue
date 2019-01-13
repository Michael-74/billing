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
                        <tr v-for="item in data" :key="item.id">
                            <td class="items__td">{{ item.id }}</td>
                            <td class="items__td">{{ item.login }}</td>
                            <td class="items__td">{{ item.ip }}</td>
                            <td class="items__td">{{ item.balance }}</td>
                            <td class="items__td">{{ item.lastPriceDate }}</td>
                            <td class="items__td">{{ item.lastPrice }}</td>
                            <td class="items__td">{{ item.contract }}</td>
                            <td class="items__td">{{ item.internet }}</td>
                            <td class="items__td">{{ item.tv }}</td>
                            <td class="items__td">{{ item.rent }}</td>
                            <td class="items__td">
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
                            <td class="items__td">
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
                            <td class="items__td">{{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}</td>
                            <td class="items__td">
                                <font-awesome-icon class="items__icon" icon="cog" @click="editClient(item)"></font-awesome-icon>
                                <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteClient(item.id)"></font-awesome-icon>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
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
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import { deleteSendClient } from "../../../../../util/ws";

export default {
    components: {
        FontAwesomeIcon
    },
    props: ['data'],
    data () {
        return {

        }
    },
    methods: {
        editClient: function(item) {
            this.$store.commit("setClient", item);
        },
        deleteClient: (clientId) => {
            console.log("deleteClient", clientId);
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

    }
    .pagination {
        overflow: hidden;
        margin: 40px auto 20px;
        width: 158px;
    }
    .pagination__link {
        border: 1px solid #468ecf;
        padding: 5px 10px;
        float: left;
        margin: 0;
        color: #468ecf;
    }
    .pagination__link:hover, .pagination__link_active  {
        color: #FFF;
        background: #468ecf;
    }
    .pagination__link:first-child {
        padding-left: 12px;
        border-top-left-radius: 20px;
        border-bottom-left-radius: 20px;
    }
    .pagination__link:last-child {
        padding-right: 12px;
        border-top-right-radius: 20px;
        border-bottom-right-radius: 20px;
    }

    .items__clients-wrapper {

    }
    .items__clients {
        max-height:500px;
        position: relative;
    }

</style>
