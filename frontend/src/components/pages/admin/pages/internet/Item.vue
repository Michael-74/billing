<template>
    <div class="items">
        <div class="items__clients-wrapper">
            <div class="items__clients">
                <table class="items__table">
                    <thead class="items__thead">
                    <tr>
                        <th class="items__th">N</th>
                        <th class="items__th">Название</th>
                        <th class="items__th">Скорость</th>
                        <th class="items__th">Статус</th>
                        <th class="items__th">Задачи</th>
                        <th class="items__th">Дата создания</th>
                        <th class="items__th">Действие</th>
                    </tr>
                    </thead>
                    <tbody class="items__tbody">
                        <tr v-for="item in data" :key="item.id">
                            <td class="items__td">{{ item.id }}</td>
                            <td class="items__td">{{ item.name }}</td>
                            <td class="items__td">{{ item.speed }}</td>
                            <td class="items__td">
                                <span v-show="item.status">
                                    <span class="items__signal"></span>
                                    <span class="items__status-text">Включен</span>
                                </span>
                                <span v-show="!item.status">
                                    <span class="items__signal items__signal_off"></span>
                                    <span class="items__status-text">Выключен</span>
                                </span>
                            </td>
                            <td class="items__td">
                                <div class="" v-for="task in item.tasks">
                                    <span class="items__pack">{{ task.name }}</span>
                                </div>
                            </td>
                            <td class="items__td">{{ item.createdAt }}</td>
                            <td class="items__td">
                                <font-awesome-icon class="items__icon" icon="cog" @click="editInternet(item)"></font-awesome-icon>
                                <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteInternet(item.id)"></font-awesome-icon>
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
        editInternet: function(item) {
            this.$store.commit("setInternet", item);
            this.$store.commit("setSelectedTasks", item.tasks);
        },
        deleteInternet: function(clientId){
            this.$store.dispatch("deleteInternetAsync", {id: clientId});
            //deleteSendClient(clientId);
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
