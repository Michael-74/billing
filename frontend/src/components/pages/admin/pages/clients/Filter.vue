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
                    <app-select-multiple :data="client.internet"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select-multiple :data="client.tvs"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select-multiple :data="client.rents"></app-select-multiple>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="client.balance"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="client.priceOverMonth"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockService">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="filters__header-checkbox filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.isPromisedPay"></app-select>
                </div>
                <div class="clear"></div>
            </div >
            <div class="filters__block filters__block_discount filters__fix-height">
                <h2>Скидка и статус</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.typeDiscount"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.isStatus"></app-select>
                </div>
                <div class="clear"></div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding filters__discount-width">
                    <app-difference-input :data="client.discount"></app-difference-input>
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
                    <app-input :data="client.login"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.ip"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.contract"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.loyalty"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean" @click="clearBlockClient">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.fio"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.address"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.phone"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.email"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select_width260 filters__select_padding">
                    <app-datepicker type="date" format="DD.MM.YYYY" :data="client.createdAt"></app-datepicker>
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
import { mapGetters } from 'vuex';
import Input from '../../../../semantic-blocks/forms/Input'
import Datepicker from '../../../../semantic-blocks/forms/Datepicker'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import SelectMultiple from '../../../../semantic-blocks/forms/SelectMultiple'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { input, select, selectMultiple, checkbox, datepicker, inputDifference } from '../../../../../util/fields'
import { parseObj, clearFields, showPresets, selectsIdVal } from '../../../../../util/helpers'

export default {
    props: ['state'],
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
                selectPreset: select(null, 'не выбрано', 'name', false, false, null, null, this.getPresets),
                inputPreset: input(null, 'Введите название пресета', 'name', false, false, null, null),
            },
            client: {
                fio: input('ФИО', 'ФИО', 'fio', false, false, null, null),
                address: input('Адрес', 'Адрес', 'address', false, false, null, null),
                phone: input('Телефон', 'Телефон', 'phone', false, false, null, null),
                email: input('Email', 'Email', 'email', false, false, null, null),
                ip: input('IP адрес', 'IP', 'ip', false, false, null, null),
                login: input('Логин', 'Логин', 'login', false, false, null, null),
                contract: input('Номер договора', 'Номер договора', 'contract', false, false, null, null),
                loyalty: select('Лояльность', 'Не выбрано', 'loyalty', false, false, null, null, this.state.loyalty),
                createdAt: datepicker('Дата создания', 'Дата создания', 'createdAt', true, false, false, null, null),
                discount: inputDifference('Размер скидки', 'от', 'до', 'discount', false, false, null, [null, null]),
                typeDiscount: select('Тип скидки', 'Не выбрано', 'typeDiscount', false, false, null, null, this.state.typeDiscount),
                isStatus: select('Статус', 'Не выбрано', 'isStatus', false, false, null, null, this.state.switchedOffOn),
                isPromisedPay: select('Обещанный платеж', 'Не выбрано', 'isPromisedPay', false, false, null, null, this.state.switchedOffOn),
                balance: inputDifference('Баланс', 'от', 'до','balance', false, false, null, [null, null]),
                priceOverMonth: inputDifference('Цена до конца месяца', 'от', 'до','priceOverMonth', false, false, null, [null, null]),
                internet: selectMultiple('Интернет', 'Не выбрано', 'internet', false, false, null, [], []),
                tvs: selectMultiple('Смотрешка', 'Не выбрано', 'tvs', false, false, null, [], [{id:1, val:'Тариф 1'}, {id:2, val:'Тариф 2'}]),
                rents: selectMultiple('Аренда оборудования', 'Не выбрано', 'rents', false, false, null, [], [{id:1, val:'Тариф 1'}, {id:2, val:'Тариф 2'}])
            }
        }
    },
    created () {
        //if(!this.$store.getters.getPresets.length) {
            this.$store.dispatch('getPresetsAsync', {url: this.$route.path});


        //}
    },
    mounted () {
        this.client.internet.items = this.getListInternets;
        this.client.rents.items = this.getListRents;
        this.client.tvs.items = this.getTvs;
    },
    watch: {
        getListInternets() {
            this.client.internet.items = this.getListInternets;
        },
        getListRents() {
            this.client.rents.items = this.getListRents;
        },
        getTvs() {
            this.client.tvs.items = this.getTvs;
        }
    },
    methods: {
        applyFilter: function () {
            console.log('applyFilter');
            const settings = parseObj(this.client);

            this.$store.dispatch('searchClientsAsync', settings)
        },
        clearBlockService: function () {
            const checkedFields = ['internet', 'tvs', 'rents', 'balance', 'priceOverMonth', 'isPromisedPay'];
            clearFields(this.client, checkedFields, true);
        },
        clearBlockClient: function () {
            const checkedFields = ['fio', 'address', 'phone', 'email', 'ip', 'login', 'contract', 'loyalty', 'createdAt'];
            clearFields(this.client, checkedFields, true);
        },
        clearBlockDiscount: function () {
            const checkedFields = ['typeDiscount', 'isStatus', 'discount'];
            clearFields(this.client, checkedFields, true);
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
        ...mapGetters([
            'getPresets', 'getListInternets', 'getListRents', 'getTvs'
        ])
    }
}
</script>

<style lang="scss" scoped>
    .filters__fix-height {
        height: 290px;
    }
    @media screen and (max-width: 1510px) {
        .filters__fix-height {
            height: 350px;
        }
    }
    .filters__block_service {
        width: 49%;
        float: left;
        margin-right: 1%;
    }
    .filters__block_discount {
        width: 49%;
        float: left;
    }
    .filters__block_contact {
        margin: 20px 0 0;
    }

</style>
