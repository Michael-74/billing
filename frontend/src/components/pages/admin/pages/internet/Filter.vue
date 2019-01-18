<template>
    <div class="filters">
        <div class="filters__header">
            <div class="filters__header-left">
                <div class="filters__header-input_width filters__header-input_inline">
                    <app-input :data="preset.inputPreset"></app-input>
                </div>
                <button class="button button__add filters__header-input_inline" @click="savePreset">
                    Добавить
                </button>
                <div class="filters__header-input_inline">
                    <button class="button button__add" @click="showPresets(internet)">Выбрать из существующих</button>
                    <!--<app-select :data="preset.selectPreset"></app-select>-->
                </div>
            </div>
            <div class="filters__header-right">
                <button class="button button__less" @click="isFilterShow">
                    {{ this.isFilter ? 'Свернуть' : 'Развернуть' }}
                    <span class="filters__button-less-block">
                            <font-awesome-icon class="filters__button-less" icon="signal"></font-awesome-icon>
                        </span>
                </button>
            </div>
            <div class="clear"></div>
        </div>
        <div class="filters__body" v-show="isFilter">
            <div class="filters__block filters__fix-height">
                <h2>Интернет тарифы</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-input :data="internet.name"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="internet.speed"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="internet.isStatus"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select_width260 filters__select_padding">
                    <app-datepicker type="date" format="DD.MM.YYYY" :data="internet.createdAt"></app-datepicker>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockInternet">Очистить</button>
                </div>
                <div class="clear"></div>
            </div >
            <div class="clear"></div>
            <div class="filters__button-save">
                <button class="button button__save" @click="applyFilter">Применить фильтр</button>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</template>

<script>

import Input from '../../../../semantic-blocks/forms/Input'
import Datepicker from '../../../../semantic-blocks/forms/Datepicker'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import SelectMultiple from '../../../../semantic-blocks/forms/SelectMultiple'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import { input, select, selectMultiple, checkbox, datepicker, inputDifference } from '../../../../../util/fields'
import { parseObj, clearFields, showPresets } from '../../../../../util/helpers'

export default {
    components: {
        AppInput: Input,
        AppDatepicker: Datepicker,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppSelectMultiple: SelectMultiple,
        AppCheckbox: Checkbox,
        FontAwesomeIcon
    },
    data () {
        return {
            isFilter: false,
            preset: {
                selectPreset: select(null, 'не выбрано', 'name', false, false, null, null, this.$store.getters.getPresets),
                inputPreset: input(null, 'Введите название пресета', 'name', false, false, null, null),
            },
            internet: {
                name: input('Название тарифа', 'Название тарифа', 'name', false, false, null, null),
                speed: inputDifference('Скорость интернета', 'от', 'до','speed', false, false, null, [null, null]),
                isStatus: select('Статус', 'Не выбрано', 'isStatus', false, false, null, null, [{id:true, val:'Включен'}, {id:false, val:'Выключен'}]),
                createdAt: datepicker('Дата создания', 'Дата создания', 'createdAt', true, false, false, null, null),
            }
        }
    },
    created () {
        //if(!this.$store.getters.getPresets.length) {
            this.$store.dispatch('getPresetsAsync', {url: this.$route.path});
        //}
    },
    methods: {
        applyFilter: function () {
            const settings = parseObj(this.internet);

            this.$store.dispatch('searchInternetsAsync', settings)
        },
        clearBlockInternet: function () {
            const checkedFields = ['speed', 'isStatus', 'name', 'createdAt'];
            clearFields(this.internet, checkedFields, true);
        },
        isFilterShow: function (){
            this.isFilter = !this.isFilter;
        },
        savePreset: function () {
            const objFields = parseObj(this.internet);
            const store = JSON.stringify(objFields);
            this.$store.dispatch("addPresetAsync", {url: this.$route.path, name: this.preset.inputPreset.val, settings: store});
        },
        showPresets(items) {
            showPresets(items)
        },
    },
    computed: {
        getPresets () {
            return this.$store.getters.getPresets
        }
    }
}
</script>

<style lang="scss" scoped>
    .filters {
        border-radius: 4px;
        box-shadow: 1px 2px 10px 2px rgba(0,0,0,0.2);
    }
    .filters__header {
        height: 70px;
    }
    .filters__header-input_width {
        width: 230px;
    }
    .filters__header-input_float-left {
        display: inline-block;
        padding-right: 20px;
    }
    .filters__header-left {
        float: left;
    }
    .filters__header-right {
        float: right;
    }
    .filters__header-input_inline {
        float: left;
        margin-right: 20px;
    }
    .filters__select {
        width: 160px;
    }
    .filters__select_width260 {
        width: 220px;
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
    .filters__body {
        padding: 20px;
    }
    .filters__block {
        background: #FFF;
        padding: 20px;
        border-radius: 4px;
        box-shadow: 1px 2px 10px 2px rgba(0,0,0,0.1);
    }
    .filters__select_padding {
        margin: 20px 20px 10px 0;
    }
    .filters__clean {
        color: #333333;
        border: 0;
        background: none;
        cursor: pointer;
        padding: 54px 0 0 20px;
        text-decoration: underline;
    }
    .filters__clean:hover {
        color: #2b87db;
    }
    .filters__header-checkbox {
        width: 200px;
    }
    .filters__discount-width {
        width: 100px;
    }
    .filters__button-save {
        margin: 20px 0 0px;
    }
    .filters__input_float {
        float: left;
        width: 50%;
    }
</style>
