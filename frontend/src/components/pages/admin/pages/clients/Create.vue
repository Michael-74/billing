<template>
    <div class="create">
        <div class="create__header">
            <button class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="plus"></font-awesome-icon>
                Добавить пользователя
            </button>
        </div>
        <div class="create__body_wrapper"  v-show="isCreate">
            <div class="create__body">
            <div class="create__input">
                <app-input :data="client.inputIp"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputLogin"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputBalance"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputContract"></app-input>
            </div>
            <div class="create__input">
                <app-checkbox :data="client.checkboxParser"></app-checkbox>
            </div>
            <div class="clear"></div>
            <div class="create__input">
                <app-input :data="client.inputFio"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputAddress"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputPhone"></app-input>
            </div>
            <div class="create__input">
                <app-input :data="client.inputEmail"></app-input>
            </div>
            <div class="clear"></div>
            <div class="create__input create__input_width-discount">
                <app-input :data="client.inputDiscount"></app-input>
            </div>
            <div class="create__input create__select_width">
                <app-select :data="client.selectTypeDiscount"></app-select>
            </div>
            <div class="create__input create__select_width">
                <app-select :data="client.selectStatus"></app-select>
            </div>
            <div class="create__input create__select_width">
                <app-select :data="client.selectLoy"></app-select>
            </div>
            <div class="clear"></div>
            <div class="create__package">
                <h2 class="create__package-h2">Настройка пакета услуг</h2>
                <div class="create__input">
                    <app-input :data="packages.inputName"></app-input>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="packages.selectInternet"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="packages.selectTv"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="packages.selectRent"></app-select>
                </div>
                <div class="create__input create__package-button">
                    <button class="button button__add" @click="show">Выбрать из существующих</button>
                </div>
                <div class="create__input create__package-button">
                    <button class="button button__save-package">Сохранить пакет</button>
                </div>
                <div class="clear"></div>
            </div>
            <div class="create__package create__note">
                <h2 class="create__package-h2">Заметки об абоненте</h2>
                <div class="create__input create__input_full-width">
                    <app-textarea :data="note.textareaNote"></app-textarea>
                </div>
                <div class="clear"></div>
            </div>
        </div>
            <div class="create__button-save">
                <button class="button button__save button__save-user">Сохранить пользователя</button>
                <button class="button button__save button__cancel-user button__cancel-user_margin" @click="isCreateClose">Отмена</button>
            </div>
        </div>
    </div>
</template>

<script>
import Input from '../../../../semantic-blocks/forms/Input'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import Textarea from '../../../../semantic-blocks/forms/Textarea'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppCheckbox: Checkbox,
        AppTextarea: Textarea,
        FontAwesomeIcon
    },
    data () {
        return {
            isCreate: false,
            note: {
                textareaNote: {
                    label: 'Введите текст',
                    text: 'Введите текст',
                    required: true
                },
            },
            packages: {
                inputName: {
                    label: 'Введите название',
                    text: 'Введите название пакета',
                    required: true
                },
                selectInternet: {
                    label: 'Интернет тариф',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
                        {
                            key: 1,
                            val: 'Тариф 1',
                        },
                        {
                            key: 2,
                            val: 'Тариф 2',
                        },
                    ]
                },
                selectTv: {
                    label: 'Смотрешка',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
                        {
                            key: 1,
                            val: '40 каналов',
                        },
                        {
                            key: 2,
                            val: '50 каналов',
                        },
                    ]
                },
                selectRent: {
                    label: 'Аренда оборудования',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
                        {
                            key: 1,
                            val: 'Тарелка',
                        },
                        {
                            key: 2,
                            val: 'Роутер',
                        },
                    ]
                },
            },
            client: {
                inputIp: {
                    label: 'IP адрес',
                    text: 'IP адрес',
                    required: true
                },
                inputLogin: {
                    label: 'Логин',
                    text: 'Логин',
                    required: true
                },
                inputBalance: {
                    label: 'Баланс',
                    text: 'Баланс',
                    required: true
                },
                inputContract: {
                    label: 'Номер договора',
                    text: 'Номер договора',
                    required: true
                },
                checkboxParser: {
                    label: 'Обещанный платеж',
                    text: '',
                    required: false
                },
                inputFio: {
                    label: 'ФИО',
                    text: 'ФИО',
                    required: true
                },
                inputAddress: {
                    label: 'Адрес',
                    text: 'Адрес',
                    required: true
                },
                inputPhone: {
                    label: 'Телефон',
                    text: 'Телефон',
                    required: true
                },
                inputEmail: {
                    label: 'Email',
                    text: 'Email',
                    required: true
                },
                inputDiscount: {
                    label: 'Скидка',
                    text: 'Скидка',
                    required: true
                },
                selectLoy: {
                    label: 'Лояльность',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
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
                selectTypeDiscount: {
                    label: 'Тип скидки',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
                        {
                            key: 1,
                            val: 'Максимальная скидка 10%',
                        },
                        {
                            key: 2,
                            val: 'Максимальная скидка 20%',
                        },
                    ]
                },
                selectStatus: {
                    label: 'Статус',
                    text: 'Не выбрано',
                    required: false,
                    multiplay: false,
                    items: [
                        {
                            key: 0,
                            val: 'Не выбрано',
                        },
                        {
                            key: 1,
                            val: 'Статус 1',
                        },
                        {
                            key: 2,
                            val: 'Статус 2',
                        },
                    ]
                },
            },
        }
    },
    methods: {
        show () {
            this.$modal.show({
                components:{
                    FontAwesomeIcon
                },
                template: `
                    <div class="modal__block">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon" icon="times-circle"></font-awesome-icon>
                            Закрыть
                        </div>
                        <h3 class="modal__h3">Список пакетов</h3>
                        <table class="modal__package-table items__table">
                            <thead class="items__thead">
                                <tr>
                                    <th class="modal__th items__th">N</th>
                                    <th class="modal__th items__th">Интернет</th>
                                    <th class="modal__th items__th">Смотрешка</th>
                                    <th class="modal__th items__th">Оборудование</th>
                                    <th class="modal__th items__th">Цена</th>
                                </tr>
                            </thead>
                            <tbody class="items__tbody">
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">1</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                                <tr class="modal__tr" @click="$emit('close')">
                                    <td class="items__td">2</td>
                                    <td class="items__td">Kvartira_40</td>
                                    <td class="items__td">Bandl_1</td>
                                    <td class="items__td">Роутер</td>
                                    <td class="items__td">550</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                  `
                },{
                },{
                    height: '400px',
                    clickToClose: false
                }
            );
        },
        hide () {
            this.$modal.hide('modal1');
        },
        isCreateShow: function () {
            this.isCreate = true;
        },
        isCreateClose: function () {
            this.isCreate = false;
        }
    }
}
</script>

<style lang="scss" scoped>
    .create {
        border-radius: 4px;
        margin: 60px 0;
        background: #FFF;
        box-shadow: 1px 2px 10px 2px rgba(0,0,0,0.2);
    }
    .create__header {
        padding: 20px;
    }
    .create__button-dashed {
        font-size: 12px;
        margin-right: 5px;
    }
    .create__body {
        padding: 20px;
    }
    .create__input {
        margin: 0 20px 30px 0;
        width: 230px;
        float: left;
    }
    .create__select_width {
        max-width: 160px;
    }
    .create__input_width-discount {
        width: 90px;
    }
    .create__package {
        border-radius: 4px;
        padding: 20px 20px 0 20px;
        box-shadow: 1px 2px 10px 2px rgba(0,0,0,0.2);
    }
    .create__package-h2 {
        margin-bottom: 20px;
    }
    .create__package-button {
        margin-top: 22px;
    }
    .create__note {
        margin: 20px 0 0;
        padding: 20px 20px 0 20px;
    }
    .create__input_full-width {
        width: 100%;
    }
    .create__button-save {
        padding: 0 20px 20px 20px;
    }
    .button__cancel-user_margin {
        margin-left: 20px;
    }
</style>
