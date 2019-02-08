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
                    <button class="button button__add" @click="showPresets(tv)">Выбрать из существующих</button>
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
                <h2>Тарифы аренды оборудования</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-input :data="tv.name"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="tv.isStatus"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select_width260 filters__select_padding">
                    <app-datepicker type="date" format="DD.MM.YYYY" :data="tv.createdAt"></app-datepicker>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockRent">Очистить</button>
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
            tv: {
                name: input('Название', 'Название', 'name', false, false, null, null),
                isStatus: select('Статус', 'Не выбрано', 'isStatus', false, false, null, null, [{id:true, val:'Включен'}, {id:false, val:'Выключен'}]),
                createdAt: datepicker('Дата создания', 'Дата создания', 'createdAt', true, false, false, null, null),
            }
        }
    },
    created () {

    },
    methods: {
        applyFilter: function () {
            const settings = parseObj(this.tv);

            this.$store.dispatch('searchRentsAsync', settings)
        },
        clearBlockRent: function () {
            const checkedFields = ['isStatus', 'name', 'createdAt'];
            clearFields(this.tv, checkedFields, true);
        },
        isFilterShow: function (){
            this.isFilter = !this.isFilter;
        },
        savePreset: function () {
            const objFields = parseObj(this.tv);
            const store = JSON.stringify(objFields);
            this.$store.dispatch("addPresetAsync", {url: this.$route.path, name: this.preset.inputPreset.val, settings: store});
        },
        showPresets(items) {
            this.$store.dispatch('getPresetsAsync', {url: this.$route.path});
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

</style>
