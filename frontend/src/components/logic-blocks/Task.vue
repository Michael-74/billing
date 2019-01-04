<template>
    <div class="wrapper__task">
        <div class="task">
            <h2 class="create__package-h2">Создание задачи</h2>
            <div class="task__input">
                <app-input :data="task.name"></app-input>
            </div>
            <div class="task__input">
                <app-input :data="task.price"></app-input>
            </div>
            <div class="task__field_float task__select_padding">
                <app-type-write-off :data="task.type_write_off_rent"></app-type-write-off>
            </div>
            <div class="task__save-button">
                <button class="button button__add filters__header-input_inline" @click="saveTask">
                    Добавить задачу
                </button>
            </div>
            <div class="clear"></div>
            <div class="task__field_float task__select_padding task_margin-top">
                <app-action-time label="Время действия" :isRequired=true :data="task.action_time"></app-action-time>
            </div>
            <div class="clear"></div>
            <br>
            <div class="task__extra">
                <h2 class="create__package-h2 task__extra_left">Дополнительно</h2>
                <div class="filters__header-right task__extra_right task__extra_margin-top">
                    <button class="button button__less" @click="isExtraChange">
                        {{ this.isExtra ? 'Свернуть' : 'Развернуть' }}
                        <span class="filters__button-less-block">
                            <font-awesome-icon class="filters__button-less" icon="signal"></font-awesome-icon>
                        </span>
                    </button>
                </div>
                <div class="clear"></div>
            </div>
            <div class="task__js-extra" v-show="isExtra">
                <div class="task__input_block">
                    <app-checkbox2 :data="task.is_rent_write_off"></app-checkbox2>
                </div>
                <div class="task__input task__input_width">
                    <app-checkbox2 :data="task.is_installments"></app-checkbox2>
                </div>
                <div class="task__input task__input_margin-top task__input_width">
                    <app-input :data="task.price_installments"></app-input>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
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
            isExtra: false,
            task: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                price: input('Введите сумму', 'Введите сумму', 'price', true, false, null, null),
                is_rent_write_off: checkbox2('Списывать плату при нулевом балансе', 'is_rent_write_off', true, false, null, false),
                is_installments: checkbox2('Включить рассрочку', 'is_installments', true, false, null, false),
                price_installments: input(null, 'Сумма в рассрочку', 'price_installments', true, false, null, null),
                action_time: actionTime('action_time', false, null, {
                    day_start: null,
                    month_start: null,
                    day_end: null,
                    month_end: null
                }),
                type_write_off_rent: actionTime('type_write_off_rent', false, null, {
                    type_write_off: null,
                    day: null,
                    datetime: null
                })
            }
        }
    },
    methods: {
        isExtraChange: function () {
            this.isExtra = !this.isExtra;
        },
        saveTask: function() {
            console.log("saveTask:", this.task);

            const items = {};
            //this.clearCreateForm();
            for(let item in this.task) {

                switch(item){
                    case 'action_time':
                        for(let name in this.task[item].val) {
                            items[name] = this.task[item].val[name];
                        }
                        break;
                    case 'type_write_off_rent':
                        for(let name in this.task[item].val) {
                            items[name] = this.task[item].val[name];
                        }
                        break;
                    default:
                        items[this.task[item].name] = this.task[item].val;
                        break;
                }
            }
            console.log("saveTask items:", items);
            this.$store.dispatch('addTaskAsync', {items: this.task, obj: items})
        }
    },
    computed: {

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
        margin-top: -12px;
    }
    .task__save-button {
        margin-top: 20px;
        float: left;
    }
</style>
