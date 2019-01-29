<template>
    <div class="wrapper__task">
        <div class="task">
            <h2 class="create__package-h2 task__extra_left">Создание задачи</h2>
            <div class="filters__header-right task__extra_right task__extra_margin-top">
                <button class="button button__less" @click="isExtraChange">
                    {{ this.isExtra ? 'Свернуть' : 'Развернуть' }}
                    <span class="filters__button-less-block">
                            <font-awesome-icon class="filters__button-less" icon="signal"></font-awesome-icon>
                        </span>
                </button>
            </div>
            <div class="clear"></div>
            <div v-show="isExtra">
                <div class="task__input hide">
                    <app-input :data="getEditTask.id"></app-input>
                </div>
                <div class="task__input">
                    <app-input :data="task.name"></app-input>
                </div>
                <div class="task__input">
                    <app-input :data="task.price"></app-input>
                </div>
                <div class="task__field_float task__select_padding">
                    <app-type-write-off :data="task.typeWriteOffRent"></app-type-write-off>
                </div>
                <div class="task__save-button task__field_float">
                    <button class="button button__add filters__header-input_inline" @click="saveTask">
                        {{ isFormCreate ? 'Добавить задачу' : 'Редактировать задачу'}}
                    </button>
                </div>
                <div class="task__save-button task__field_float">
                    <button class="button button__add" @click="showSelectedTasks">Выбранные задачи</button>
                </div>
                <div class="task__save-button">
                    <button class="button button__add" @click="showTasks(task)">Все задачи</button>
                </div>
                <div class="task__save-button">
                    <button class="filters__clean" @click="clearBlockTask">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="task__field_float task__select_padding task_margin-top">
                    <app-action-time label="Время действия" :isRequired=true :data="task.actionTime"></app-action-time>
                </div>
                <div class="clear"></div>
                <br>
                <div class="task__extra">
                    <h2 class="create__package-h2">Дополнительно</h2>
                </div>
                <div class="task__js-extra">
                    <div class="task__input_block">
                        <app-checkbox2 :data="task.isRentWriteOff"></app-checkbox2>
                    </div>
                    <div class="task__input task__input_width">
                        <app-checkbox2 :data="task.isInstallments"></app-checkbox2>
                    </div>
                    <div class="task__input task__input_margin-top task__input_width">
                        <app-input :data="task.priceInstallments"></app-input>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import Input from '../semantic-blocks/forms/Input'
import Checkbox2 from '../semantic-blocks/forms/Checkbox2'
import DifferenceInput from '../semantic-blocks/forms/DifferenceInput'
import Select from '../semantic-blocks/forms/Select'
import TypeWriteOff from '../logic-blocks/TypeWriteOff'
import ActionTime from '../semantic-blocks/forms/ActionTime'

import { parseObj, clearFields, showTasks, showSelectedTasks, formatPrice } from '../../util/helpers'
import { input, select, selectMultiple, checkbox, datepicker, inputDifference, actionTime, checkbox2 } from '../../util/fields'

export default {
    components: {
        AppInput: Input,
        AppCheckbox2: Checkbox2,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppTypeWriteOff: TypeWriteOff,
        AppActionTime: ActionTime,
        FontAwesomeIcon
    },
    data () {
        return {
            isFormCreate: true,
            isExtra: false,
            task: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                price: input('Введите сумму', 'Введите сумму', 'price', true, false, null, null),
                isRentWriteOff: checkbox2('Списывать плату при нулевом балансе', 'isWriteOffRent', true, false, null, false),
                isInstallments: checkbox2('Включить рассрочку', 'isInstallments', true, false, null, false),
                priceInstallments: input(null, 'Сумма в рассрочку', 'priceInstallments', true, false, null, null),
                actionTime: actionTime('actionTime', false, null, {
                    dayStart: null,
                    monthStart: null,
                    dayEnd: null,
                    monthEnd: null
                }),
                typeWriteOffRent: actionTime('typeWriteOffRent', false, null, {
                    typeWriteOff: null,
                    dayInMonth: 0,
                    datetime: null
                })
            }
        }
    },
    methods: {
        formatPriceLocal (price) {
            return formatPrice(price);
        },
        clearBlockTask: function () {
            for(let item in this.task) {
                switch(item){
                    case 'actionTime':
                    case 'typeWriteOffRent':
                        for(let name in this.task[item].val) {
                            this.task[item].val[name] = null;
                        }
                        this.task[item].isError = null;
                        this.task[item].errorText = null;
                        break;
                    default:
                        this.task[item].val = null;
                        this.task[item].isError = null;
                        this.task[item].errorText = null;
                        break;
                }
            }
            this.changeForm(true);
        },
        setDate: function(date) {
            var date = new Date(date);
            date.setHours(date.getHours() + 5);

            return date;
        },
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        isExtraChange: function () {
            this.isExtra = !this.isExtra;
        },
        saveTask: function() {

            const items = {};
            //this.clearBlockTask();
            for(let item in this.task) {

                switch(item){
                    case 'actionTime':
                    case 'typeWriteOffRent':
                        for(let name in this.task[item].val) {
                            items[name] = this.task[item].val[name];
                        }
                        break;
                    default:
                        items[this.task[item].name] = this.task[item].val;
                        break;
                }
            }

            const options = {
                datetime: 'typeWriteOffRent',
                dayInMonth: 'typeWriteOffRent',
                typeWriteOff: 'typeWriteOffRent',
                dayStart: 'actionTime',
                monthStart: 'actionTime',
                dayEnd: 'actionTime',
                monthEnd: 'actionTime',
            };
            this.$store.dispatch('addTaskAsync', {items: this.task, obj: items, options: options, isFormCreate: this.isFormCreate})
        },
        showTasks: function() {
            this.$store.dispatch('getTasksAsync');
            showTasks();
        },
        showSelectedTasks: function() {
            showSelectedTasks();
        }
    },
    computed: {
        getEditTask () {
            var isFlagFormCreate = true;
            if(this.$store.getters.getEditTask) {
                isFlagFormCreate = false;
                for (let item in this.task) {
                    switch(item) {
                        case 'actionTime':
                        case 'typeWriteOffRent':
                            for(let name in this.task[item].val) {
                                if(name === 'datetime'){
                                    this.task[item].val[name] = this.setDate(this.$store.getters.getEditTask[name]);
                                } else {
                                    this.task[item].val[name] = this.$store.getters.getEditTask[name];
                                }
                            }
                            break;
                        default:
                            this.task[item].val = this.$store.getters.getEditTask[item];
                            break;
                    }
                }
                console.log("task", this.task);
            }
            this.changeForm(isFlagFormCreate);

            return this.task
        }
    }
}
</script>

<style lang="scss" scoped>
    .create__package-h2 {
        margin-bottom: 20px;
    }
    .task__field_float {
        float: left;
        margin-right: 20px;
    }
    .task__select {
        width: 160px;
    }
    .task__select_padding {
        margin: 0px 20px 20px 0;
    }
    .task__input {
        margin: 0 20px 30px 0;
        width: 230px;
        float: left;
    }
    .task__input_width {
        width: 180px;
    }
    .task__input_margin-top {
        margin-top: -10px;
    }
    .task__input_block {
        margin: 0 20px 30px 0;
    }
    .task_margin-top {
        margin-top: -30px;
    }

    @media screen and (max-width: 1600px) {
        .task_margin-top {
            margin-top: 20px;
        }
        .task__select_padding {
            margin: 0 20px 0 0;
        }
    }

    .filters__button-less-block {
        position: relative;
        display: inline-block;
        width: 20px;
        height: 12px;
        overflow: hidden;
    }
    .filters__button-less {
        position: absolute;
        transform: rotate(-90deg);
        top: 4px;
        left: 0;
        font-size: 14px;
    }
    .task__extra_right {
        float: right;
    }
    .task__extra_left {
        float: left;
    }
    .task__extra_margin-top {
        margin-top: -6px;
    }
    .task__save-button {
        margin-top: 20px;
        float: left;
    }
    .filters__clean {
        color: #333333;
        border: 0;
        background: none;
        cursor: pointer;
        padding: 12px 0 0 20px;
        text-decoration: underline;
    }
</style>
