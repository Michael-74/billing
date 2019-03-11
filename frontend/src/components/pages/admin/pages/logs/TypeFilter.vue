<template>
    <div class="filters services">
        <div class="services__header">
            <div class="create__body">
                <div class="service__type">
                    <h2 class="create__package-h2">Тип лога</h2>
                    <!-- -->
                    <div class="services__title">Услуги</div>
                    <div class="services__blocks">
                        <div class="services__block" :class="{'services__block_active': this.checkSelected === 'Client'}" @click="selectBlock('Client')">
                            <input type="radio" id="Client" value="Client" class="services__input">
                            <label for="Client">Абоненты</label>
                        </div>
                        <div class="services__block" :class="{'services__block_active': this.checkSelected === 'Mikrotik'}" @click="selectBlock('Mikrotik')">
                            <input type="radio" id="Mikrotik" value="Mikrotik" class="services__input">
                            <label for="Mikrotik">Интернет</label>
                        </div>
                        <div class="services__block" :class="{'services__block_active': this.checkSelected === 'Smotreshka'}" @click="selectBlock('Smotreshka')">
                            <input type="radio" id="Smotreshka" value="Smotreshka" class="services__input">
                            <label for="Smotreshka">КТВ</label>
                        </div>
                        <div class="services__block" :class="{'services__block_active': this.checkSelected === 'Rental'}" @click="selectBlock('Rental')">
                            <input type="radio" id="Rental" value="Rental" class="services__input">
                            <label for="Rental">Аренда оборудования</label>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="service__search">
                    <h2 class="">Поиск по логу</h2>
                    <div class="filters__header-select filters__header-input_inline filters__select_width260 filters__select_padding">
                        <app-datepicker type="date" format="DD.MM.YYYY" :data="log.createdAt"></app-datepicker>
                    </div>
                    <div class="filters__header-input_width filters__header-input_inline filters__select_padding filters__select_width120">
                        <app-input :data="log.clientId"></app-input>
                    </div>
                    <div v-if='this.check("Smotreshka")' class="filters__header-select filters__header-input_inline filters__select filters__select_padding filters__select_width260">
                        <app-select-multiple :data="log.smotreshkaService"></app-select-multiple>
                    </div>
                    <div v-if='this.check("Mikrotik")' class="filters__header-select filters__header-input_inline filters__select filters__select_padding filters__select_width260">
                        <app-select-multiple :data="log.mikrotikService"></app-select-multiple>
                    </div>
                    <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                        <app-input :data="log.contract"></app-input>
                    </div>
                    <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                        <app-input :data="log.email"></app-input>
                    </div>
                    <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                        <app-input :data="log.ip"></app-input>
                    </div>
                    <div class="clear"></div>
                    <div class="filters__button-save">
                        <button class="button button__save" @click="searchLog">Применить фильтр</button>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
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
            services: "Client",
            log: {
                clientId: input('id абонента', 'id абонента', 'clientId', false, false, null, null),
                contract: input('Номер договора', 'Номер договора', 'contract', false, false, null, null),
                email: input('Email', 'Email', 'email', false, false, null, null),
                ip: input('IP адрес', 'IP', 'ip', false, false, null, null),
                smotreshkaService: selectMultiple('Тип события', 'Не выбрано', 'Smotreshka', false, false, null, [], [
                    {id:"addAccount" ,val: "Добавление аккаунта"},
                    {id:"getAccountById" ,val: "Получение аккаунт по ID"},
                    {id:"setInfoOfAccount" ,val: "Изменение информации аккаунта"},
                    {id:"setResetPasswordOfAccount" ,val: "Сброс пароля"},
                    {id:"deleteAccountById" ,val: "Удаление аккаунта по ID"},
                    {id:"getSubscriptionsOfAccount" ,val: "Получение подписок"},
                    {id:"setSubscriptionOfAccount" ,val: "Изменение подписок"},
                    {id:"deleteAllSubscriptionsOfAccount" ,val: "Удаление всех подписок"}
                ]),
                mikrotikService: selectMultiple('Тип события', 'Не выбрано', 'Mikrotik', false, false, null, [], [
                    {id:"search" ,val: "Поиск"},
                    {id:"getAll" ,val: "Получение всех аккаунтов"},
                    {id:"create" ,val: "Добавление аккаунта"},
                    {id:"update" ,val: "Обновление аккаунта"},
                    {id:"delete" ,val: "Удаление аккаунта"},
                ]),
                createdAt: datepicker('Дата создания', 'Дата создания', 'createdAt', true, false, false, null, null),
            },
        }
    },
    created () {

    },
    methods: {
        searchLog() {

        },
        selectBlock(data) {
            this.services = data;
        },
        check(data) {
            return this.services === data
        }
    },
    computed: {
        checkSelected() {
            return this.services;
        },
        getService() {
            return this.serviceNames.Mikrotik
        }
    }
}
</script>

<style lang="scss" scoped>
.services__header {
    background: #f8f9fa;
    border-radius: 4px;
}
.services__input {
    display: none;
}
.services__block_red {
    border: 1px solid red;
}
.services__title {
    padding: 0 0 5px 0;
}
.service__type {
    padding-bottom: 20px;
}
.services__blocks {
    border: 1px solid #468ecf;
    display: inline-block;
    border-radius: 20px;
    overflow: hidden;
}
.services__block {
    float: left;
    padding: 6px 15px;
    cursor: pointer;
    color: #398fdd;
    border-right: 1px solid #468ecf;

    &:hover, &_active {
        cursor: pointer;
        background: #2b87db;
        color: #FFF;
    }
    & label {
        cursor: pointer;
    }
}
.filters__select_width260 {
    width: 260px;
}
.filters__select_width120 {
    width: 120px;
}
.filters__select_padding {
    padding: 0 20px 0 0;
}

</style>
