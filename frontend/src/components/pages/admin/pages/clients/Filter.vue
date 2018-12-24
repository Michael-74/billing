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
                    <button class="button button__add" @click="showPresets(client)">Выбрать из существующих</button>
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
            <div class="filters__block filters__block_service filters__fix-height">
                <h2>Услуги и баланс</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select-multiple :data="client.selectInternet"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select-multiple :data="client.selectTv"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select-multiple :data="client.selectRent"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-input class="filters__input_float" :data="client.inputDifferenceFrom"></app-input>
                    <app-input class="filters__input_float" :data="client.inputDifferenceTo"></app-input>
                    <div class="clear"></div>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="client.inputDifferencePrice"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockService">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="filters__header-checkbox filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-checkbox :data="client.checkboxParser"></app-checkbox>
                </div>
                <div class="clear"></div>
            </div >
            <div class="filters__block filters__block_discount filters__fix-height">
                <h2>Скидка и статус</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.selectDiscount"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.selectStatus"></app-select>
                </div>
                <div class="clear"></div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding filters__discount-width">
                    <app-input :data="client.inputDiscount"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockDiscount">Очистить</button>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
            <div class="filters__block filters__block_contact">
                <h2>Учетная запись и контактные данные</h2>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputLogin"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputIp"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputContract"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.selectLoy"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockClient">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputFio"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputAddress"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputPhone"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputEmail"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select_width260 filters__select_padding">
                    <app-datepicker :data="client.createdDifference"></app-datepicker>
                </div>
                <div class="clear"></div>
            </div>
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
import axios from "axios";
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
            client: {
                inputFio: input('ФИО', 'ФИО', 'fio', false, false, null, null),
                inputAddress: input('Адрес', 'Адрес', 'address', false, false, null, null),
                inputPhone: input('Телефон', 'Телефон', 'phone', false, false, null, null),
                inputEmail: input('Email', 'Email', 'email', false, false, null, null),
                inputIp: input('IP адрес', 'IP', 'ip', false, false, null, null),
                inputLogin: input('Логин', 'Логин', 'login', false, false, null, null),
                inputContract: input('Номер договора', 'Номер договора', 'contract', false, false, null, null),
                selectLoy: select('Лояльность', 'Не выбрано', 'loyalty', false, false, null, null, [{id:1, val:1}, {id:2, val:2}]),
                createdDifference: datepicker('Дата создания', 'Дата создания', 'created', true, false, false, null, null),
                inputDiscount: input('Размер скидки', 'Скидка', 'discount', false, false, null, null),
                selectDiscount: select('Скидка', 'Не выбрано', 'type_discount', false, false, null, null, [{id:1, val:1}, {id:2, val:2}]),
                selectStatus: select('Статус', 'Не выбрано', 'status', false, false, null, null, [{id:1, val:1}, {id:2, val:2}]),
                checkboxParser: checkbox(null, 'Обещанный платеж', 'Обещанный платеж', 'promise', false, false, null, null),
                inputDifferenceFrom: input('Баланс', 'от', 'balance_from', false, false, null, null),
                inputDifferenceTo: input('&nbsp;', 'до', 'balance_to', false, false, null, null),
                //inputDifferenceParser: inputDifference('Баланс', 'от -1000', 'до 1000','balance', false, false, null, [null, null]),
                inputDifferencePrice: inputDifference('Цена до конца месяца', 'от', 'до','price_over_month', false, false, null, [null, null]),
                selectInternet: selectMultiple('Интернет', 'Не выбрано', 'internet', false, false, null, [], [{id:1, val:'Тариф 1'}, {id:2, val:'Тариф 2'}]),
                selectTv: selectMultiple('Смотрешка', 'Не выбрано', 'tv', false, false, null, [], [{id:1, val:'Тариф 1'}, {id:2, val:'Тариф 2'}]),
                selectRent: selectMultiple('Аренда оборудования', 'Не выбрано', 'rent', false, false, null, [], [{id:1, val:'Тариф 1'}, {id:2, val:'Тариф 2'}])
            }
        }
    },
    created () {
        if(!this.$store.getters.getPresets.length) {
            this.$store.dispatch('getPresetsAsync', {url: this.$route.path});
        }
    },
    methods: {
        applyFilter: function () {
            console.log('applyFilter');
            const settings = parseObj(this.client);

            this.$store.dispatch('searchClientsAsync', settings)
        },
        clearBlockService: function () {
            const checkedFields = ['selectInternet', 'selectTv', 'selectRent', 'inputDifferenceFrom', 'inputDifferenceTo', 'inputDifferencePrice', 'checkboxParser'];
            clearFields(this.client, checkedFields);
        },
        clearBlockClient: function () {
            const checkedFields = ['inputFio', 'inputAddress', 'inputPhone', 'inputEmail', 'inputIp', 'inputLogin', 'inputContract', 'selectLoy', 'createdDifference'];
            clearFields(this.client, checkedFields);
        },
        clearBlockDiscount: function () {
            const checkedFields = ['selectDiscount', 'selectStatus', 'inputDiscount'];
            clearFields(this.client, checkedFields);
        },
        isFilterShow: function (){
            this.isFilter = !this.isFilter;
            console.log('isFilterShow');
        },
        savePreset: function () {
            const objFields = parseObj(this.client);
            const store = JSON.stringify(objFields);
            this.$store.dispatch("addPresetAsync", {url: this.$route.path, name: this.preset.inputPreset.val, settings: store});
        },
        showPresets: function (items) {
            showPresets(items);
        }
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
    .filters__fix-height {
        height: 280px;
    }
    @media screen and (max-width: 1510px) {
        .filters__fix-height {
            height: 350px;
        }
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
    .filters__block_service {
        width: 49%;
        float: left;
        margin-right: 1%;
    }
    .filters__block_discount {
        width: 49%;
        /*margin-left: 51%;*/
        float: left;
    }
    .filters__block_contact {
        margin: 20px 0 0;
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
