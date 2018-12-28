<template>
    <div class="items">
        <div class="items__select items__field_float" :class="{'items_no-margin-right': getType == null}">
            <app-select :data="items.typeWriteOff"></app-select>
        </div>
        <div class="items__select items__field_float items__select_width200" v-if="getType == 'onetime'">
            <app-datepicker type='datetime' format="DD.MM.YYYY HH:mm" :data="items.onetime"></app-datepicker>
        </div>
        <div class="items__select items__field_float" v-if="getType == 'daily'">
            <app-datepicker type='time' format="HH:mm" :data="items.daily"></app-datepicker>
        </div>
        <div class="items__day-time items__field_float" v-if="getType == 'montly'">
            <div class="items__select items__field_float items__select_width100">
                <app-input :data="items.montlyDay"></app-input>
            </div>
            <div class="items__select items__field_float">
                <app-datepicker type='time' format="HH:mm" :data="items.montlyTime"></app-datepicker>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import Input from '../semantic-blocks/forms/Input'
import DifferenceInput from '../semantic-blocks/forms/DifferenceInput'
import Select from '../semantic-blocks/forms/Select'

import { input, select, selectMultiple, checkbox, datepicker } from '../../util/fields'
import Datepicker from '../semantic-blocks/forms/Datepicker'

export default {
    props: ['data'],
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppDatepicker: Datepicker,
        FontAwesomeIcon
    },
    data () {
        return {
            items: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                onetime: datepicker('Выберите дату', 'Выберите дату', 'onetime', false, false, false, null, null),
                daily: datepicker('Выберите время', 'Выберите время', 'daily', false, false, false, null, null),
                montlyDay: input('День списания', 'День', 'name', true, false, null, null),
                montlyTime: datepicker('Выберите время', 'Выберите время', 'timeInDay', false, false, false, null, null),
                typeWriteOff: select('Тип списания', 'Не выбрано', 'type_writeoff', false, false, null, this.data.type, [{id:'onetime', val:'Разово'}, {id:'daily', val:'Ежедневно'}, {id:'montly', val:'Ежемесячно'}]),
            }
        }
    },
    methods: {

    },
    computed: {
        getType() {
            switch(this.items.typeWriteOff.val) {
                case 'onetime':
                    this.data.type = 'onetime';
                    this.data.day = null;
                    this.data.date = this.items.onetime.val;
                    break;
                case 'daily':
                    this.data.type = 'daily';
                    this.data.day = null;
                    this.data.date = this.items.daily.val;
                    break;
                case 'montly':
                    this.data.type = 'montly';
                    this.data.day = this.items.montlyDay.val;
                    this.data.date = this.items.montlyTime.val;
                    break;
                default:
                    this.data.type = null;
                    this.data.day = null;
                    this.data.date = null;
                break;
            }

            return this.items.typeWriteOff.val;
        }
    }
}
</script>

<style lang="scss" scoped>
    .items {
        position: relative;
        top: -22px;
        border: 1px solid #d9e4e8;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 1px 1px 4px 0px rgba(0,0,0,0.2);
    }
    .items__field_float {
        float: left;
        margin-right: 20px;
    }
    .items__select {
        width: 160px;
    }
    .items__select_padding {
        margin: 0px 20px 20px 0;
    }
    .items__input {
        margin: 0 20px 30px 0;
        width: 230px;
        float: left;
    }
    .items__select_width200 {
        width: 200px;
    }
    .items__select_width100 {
        width: 100px;
    }
    .items_margin-right {
        margin-right: 20px;
    }
    .items_no-margin-right {
        margin-right: 0px;
    }
</style>
