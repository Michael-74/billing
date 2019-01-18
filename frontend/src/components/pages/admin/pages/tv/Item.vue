<template>
    <div class="items">
        <div class="items__clients-wrapper">
            <div class="items__clients">
                <table class="items__table">
                    <thead class="items__thead">
                    <tr>
                        <th class="items__th">N</th>
                        <th class="items__th">Название</th>
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
                            <td class="items__td">
                                <div class="" v-for="task in item.tasks">
                                    <span class="items__pack">{{ task.name }}</span>
                                </div>
                            </td>
                            <td class="items__td">{{ item.createdAt ? item.createdAt : "Добавлен или изменен только что" }}</td>
                            <td class="items__td">
                                <font-awesome-icon class="items__icon" icon="cog" @click="editTv(item)"></font-awesome-icon>
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
        editTv: function(item) {
            this.$store.commit("setTv", item);
            this.$store.commit("setSelectedTasks", item.tasks);
        },
        deleteInternet: function(clientId){
            this.$store.dispatch("deleteTvAsync", {id: clientId});
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
        max-height:500px;
        position: relative;
    }
    .items__signal {
        height: 5px;
        width: 5px;
        border-radius: 50%;
        background: #67d071;
        display: inline-block;
        position: relative;
        top: -2px;
        left: -6px;
    }
    .items__signal_off {
        background: red;
    }
    .items__status-text {
        display: inline-block;
        width: 60px;
        margin-left: 5px;
    }
</style>
