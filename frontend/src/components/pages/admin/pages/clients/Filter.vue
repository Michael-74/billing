<template>
    <div class="filters">
        <div class="filters__header">
            <div class="filters__header-left">
                <div class="filters__header-input_width filters__header-input_inline">
                    <app-input :data="filter.inputParser"></app-input>
                </div>
                <button class="button button__add filters__header-input_inline">
                    Добавить
                </button>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <app-select :data="filter.selectParser"></app-select>
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
                    <app-select :data="filter.selectInternet"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="filter.selectTv"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="filter.selectRent"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="filter.inputDifferenceParser"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-difference-input :data="filter.inputDifferencePrice"></app-difference-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean">Очистить</button>
                </div>
                <div class="clear"></div>
                <div class="filters__header-checkbox filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-checkbox :data="filter.checkboxParser"></app-checkbox>
                </div>
                <div class="clear"></div>
            </div >
            <div class="filters__block filters__block_discount filters__fix-height">
                <h2>Скидка и статус</h2>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="discount.selectDiscount"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="discount.selectStatus"></app-select>
                </div>
                <div class="clear"></div>
                <div class="filters__header-input_width filters__select_padding filters__discount-width">
                    <app-input :data="discount.inputDiscount"></app-input>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
            <div class="filters__block filters__block_contact">
                <h2>Учетная запись и контактные данные</h2>
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
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputIp"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputLogin"></app-input>
                </div>
                <div class="filters__header-input_width filters__header-input_inline filters__select_padding">
                    <app-input :data="client.inputContract"></app-input>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select filters__select_padding">
                    <app-select :data="client.selectLoy"></app-select>
                </div>
                <div class="filters__header-select filters__header-input_inline filters__select">
                    <button class="filters__clean">Очистить</button>
                </div>
                <div class="clear"></div>
            </div>
            <div class="filters__button-save">
                <button class="button button__save">Применить фильтр</button>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</template>

<script>

import Input from '../../../../semantic-blocks/forms/Input'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppCheckbox: Checkbox,
        FontAwesomeIcon
    },
    data () {
        return {
            isFilter: false,
            client: {
                inputFio: {
                    label: 'ФИО',
                    text: 'ФИО',
                    required: false
                },
                inputAddress: {
                    label: 'Адрес',
                    text: 'Адрес',
                    required: false
                },
                inputPhone: {
                    label: 'Телефон',
                    text: 'Телефон',
                    required: false
                },
                inputEmail: {
                    label: 'Email',
                    text: 'Email',
                    required: false
                },
                inputIp: {
                    label: 'IP адрес',
                    text: 'IP',
                    required: false
                },
                inputLogin: {
                    label: 'Логин',
                    text: 'Логин',
                    required: false
                },
                inputContract: {
                    label: 'Номер договора',
                    text: 'Номер договора',
                    required: false
                },
                selectLoy: {
                    label: 'Лояльность',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: '1',
                        },
                        {
                            key: 2,
                            val: '2',
                        },
                        {
                            key: 3,
                            val: '3',
                        },
                        {
                            key: 4,
                            val: '4',
                        },
                        {
                            key: 5,
                            val: '5',
                        },
                        {
                            key: 6,
                            val: '6',
                        },
                        {
                            key: 7,
                            val: '7',
                        },
                        {
                            key: 8,
                            val: '8',
                        },
                        {
                            key: 9,
                            val: '9',
                        },
                        {
                            key: 10,
                            val: '10',
                        }
                    ]
                },
            },
            discount: {
                inputDiscount: {
                    label: 'Размер скидки',
                    text: 'Скидка',
                    required: false
                },
                selectDiscount: {
                    label: 'Скидка',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Пресет 1',
                        },
                        {
                            key: 2,
                            val: 'Пресет 2',
                        }
                    ]
                },
                selectStatus: {
                    label: 'Статус',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Пресет 1',
                        },
                        {
                            key: 2,
                            val: 'Пресет 2',
                        }
                    ]
                },
            },
            filter: {
                inputParser: {
                    label: '',
                    text: 'Введите название парсера',
                    required: false
                },
                checkboxParser: {
                    label: '',
                    text: 'Обещанный платеж',
                    required: false
                },
                inputDifferenceParser: {
                    label: 'Баланс',
                    textFrom: 'от -1000',
                    textTo: 'до 1000',
                    required: false
                },
                inputDifferencePrice: {
                    label: 'Цена до конца месяца',
                    textFrom: 'от -1000',
                    textTo: 'до 1000',
                    required: false
                },
                selectParser: {
                    label: '',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Пресет 1',
                        },
                        {
                            key: 2,
                            val: 'Пресет 2',
                        },
                        {
                            key: 3,
                            val: 'Пресет 3',
                        }
                    ]
                },
                selectInternet: {
                    label: 'Интернет тариф',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Тариф 1',
                        },
                        {
                            key: 2,
                            val: 'Тариф 2',
                        },
                        {
                            key: 3,
                            val: 'Тариф 3',
                        }
                    ]
                },
                selectTv: {
                    label: 'Смотрешка',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Тариф 1',
                        },
                        {
                            key: 2,
                            val: 'Тариф 2',
                        },
                        {
                            key: 3,
                            val: 'Тариф 3',
                        }
                    ]
                },
                selectRent: {
                    label: 'Аренда',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 1,
                            val: 'Аренда 1',
                        },
                        {
                            key: 2,
                            val: 'Аренрда 2',
                        },
                        {
                            key: 3,
                            val: 'Аренрда 3',
                        }
                    ]
                }
            }
        }
    },
    methods: {
        isFilterShow: function (){
            this.isFilter = !this.isFilter;
            console.log('isFilterShow');
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
        margin: 20px 0 20px;
    }
</style>
