<template>
    <div class="create">
        <div class="create__header">
            <button v-if="isFormCreate" class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="plus"></font-awesome-icon>
                Добавить пользователя
            </button>
            <button v-else class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="cog"></font-awesome-icon>
                Редактировать пользователя
            </button>
        </div>
        <div class="create__body_wrapper"  v-show="isCreate">
            <div class="create__body">
                <div class="create__input create__input_hide">
                    <app-input :data="getClient.id"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="getClient.ip"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.login"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.balance"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.contract"></app-input>
                </div>
                <div class="create__input">
                    <app-checkbox :data="client.promised"></app-checkbox>
                </div>
                <div class="clear"></div>
                <div class="create__input">
                    <app-input :data="client.fio"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.address"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.phone"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.email"></app-input>
                </div>
                <div class="clear"></div>
                <div class="create__input create__input_width-discount">
                    <app-input :data="client.discount"></app-input>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="client.type_discount"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="client.status"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="client.loyalty"></app-select>
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
                        <app-select-multiple :data="packages.selectTv"></app-select-multiple>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select-multiple :data="packages.selectRent"></app-select-multiple>
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
                <button v-if="isFormCreate" class="button button__save button__save-user" @click="addClient">Сохранить пользователя</button>
                <button v-else class="button button__save button__save-user" @click="addClient">Редактировать пользователя</button>
                <button class="button button__save button__cancel-user button__cancel-user_margin" @click="isCreateClose">Отмена</button>
            </div>
        </div>
        <div class="hide">
            {{isSendForm}}
            {{errors}}
        </div>
    </div>
</template>

<script>
import Input from '../../../../semantic-blocks/forms/Input'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import SelectMultiple from '../../../../semantic-blocks/forms/SelectMultiple'
import Textarea from '../../../../semantic-blocks/forms/Textarea'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { sendClient } from "../../../../../util/ws";

export default {
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppSelectMultiple: SelectMultiple,
        AppCheckbox: Checkbox,
        AppTextarea: Textarea,
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            isFormCreate: true,
            isCreate: false,
            client: {
                id: {
                    label: 'ID',
                    text: 'ID',
                    name: 'id',
                    isRequired: false,
                    isError: false,
                    errorText: null,
                    val: null
                },
                ip: {
                    label: 'IP адрес',
                    text: 'IP адрес',
                    name: 'ip',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                login: {
                    label: 'Логин',
                    text: 'Логин',
                    name: 'login',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                balance: {
                    label: 'Баланс',
                    text: 'Баланс',
                    name: 'balance',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                contract: {
                    label: 'Номер договора',
                    text: 'Номер договора',
                    name: 'contract',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                promised: {
                    label: 'Обещанный платеж',
                    textTrue: 'Включен',
                    textFalse: 'Выключен',
                    name: 'promised',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: true
                },
                fio: {
                    label: 'ФИО',
                    text: 'ФИО',
                    name: 'fio',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                address: {
                    label: 'Адрес',
                    text: 'Адрес',
                    name: 'address',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                phone: {
                    label: 'Телефон',
                    text: 'Телефон',
                    name: 'phone',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                email: {
                    label: 'Email',
                    text: 'Email',
                    name: 'email',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                discount: {
                    label: 'Скидка',
                    text: 'Скидка',
                    name: 'discount',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                loyalty: {
                    label: 'Лояльность',
                    text: 'Не выбрано',
                    name: 'loyalty',
                    multiple: false,
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null,
                    items: [
                        {
                            id: 1,
                            val: '1',
                        },
                        {
                            id: 2,
                            val: '2',
                        },
                        {
                            id: 3,
                            val: '3',
                        },
                        {
                            id: 4,
                            val: '4',
                        },
                        {
                            id: 5,
                            val: '5',
                        },
                        {
                            id: 6,
                            val: '6',
                        },
                        {
                            id: 7,
                            val: '7',
                        },
                        {
                            id: 8,
                            val: '8',
                        },
                        {
                            id: 9,
                            val: '9',
                        },
                        {
                            id: 10,
                            val: '10',
                        }
                    ]
                },
                type_discount: {
                    label: 'Тип скидки',
                    text: 'Не выбрано',
                    name: 'type_discount',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null,
                    multiple: false,
                    items: [
                        {
                            id: 1,
                            val: 'Максимальная скидка 10%',
                        },
                        {
                            id: 2,
                            val: 'Максимальная скидка 20%',
                        },
                    ]
                },
                status: {
                    label: 'Статус',
                    text: 'Не выбрано',
                    name: 'status',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null,
                    multiple: false,
                    items: [
                        {
                            id: 1,
                            val: 'Статус 1',
                        },
                        {
                            id: 2,
                            val: 'Статус 2',
                        },
                    ]
                },
            },
            packages: {
                inputName: {
                    label: 'Введите название',
                    text: 'Введите название пакета',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
                selectInternet: {
                    label: 'Интернет тариф',
                    text: 'Не выбрано',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null,
                    multiple: false,
                    items: [
                        {
                            id: 1,
                            val: 'Тариф 1',
                        },
                        {
                            id: 2,
                            val: 'Тариф 2',
                        },
                    ]
                },
                selectTv: {
                    label: 'Смотрешка',
                    text: 'Не выбрано',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: [],
                    multiple: true,
                    items: [
                        {
                            id: 1,
                            val: '40 каналов',
                        },
                        {
                            id: 2,
                            val: '50 каналов',
                        },
                        {
                            id: 3,
                            val: '60 каналов',
                        },
                        {
                            id: 4,
                            val: '10 + 60 каналов',
                        },
                        {
                            id: 5,
                            val: '50 каналов',
                        },
                    ]
                },
                selectRent: {
                    label: 'Аренда оборудования',
                    text: 'Не выбрано',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: [],
                    multiple: true,
                    items: [
                        {
                            id: 1,
                            val: 'Тарелка',
                        },
                        {
                            id: 2,
                            val: 'Роутер',
                        },
                    ]
                },
            },
            note: {
                textareaNote: {
                    label: 'Введите текст',
                    text: 'Введите текст',
                    isRequired: true,
                    isError: false,
                    errorText: null,
                    val: null
                },
            },
            errors: this.$store.getters.getClient,
            isSendForm: this.$store.getters.getSendForm
        }
    },
    computed: {
        getClient () {
            var isFlagFormCreate = true;
            if(this.editItem) {
                isFlagFormCreate = false;
                for (let item in this.client) {
                    this.client[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.client
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        clientStore: function (data) {
            //sendClient(data); // websocket

            axios
                .post('/admin/v1/client/create', data, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.$store.getters.getUser.token
                    }
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$store.commit('selectSendForm', true);
                        if(this.isFormCreate) {
                            this.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Абонент успешно добавлен'
                            });
                        } else {
                            this.$notify({
                                group: 'notify',
                                type: 'success ',
                                text: 'Абонент успешно отредактирован'
                            });
                        }
                        console.log('client-store', "save");
                    }
                })
                .catch(error => {
                    if(error.response.status === 422) {
                        console.log("error", error.response);
                        this.$store.commit('addErrors', error.response.data);
                        this.$store.commit('selectSendForm', false);
                        this.$notify({
                            group: 'notify',
                            type: 'error',
                            text: 'Проверьте введенные данные'
                        });
                        this.checkErrors();
                    }
                });

        },
        checkErrors () {
            for(let item in this.client) {
                this.client[item].isError = false;
            }
            if(this.$store.getters.getClient.errors) {
                for(let item in this.$store.getters.getClient.errors) {
                    this.client[item].isError = true;
                    this.client[item].errorText = this.$store.getters.getClient.errors[item];
                }
            }
        },
        addClient () {
            const client = {};
            //this.clearCreateForm();
            for(let item in this.client) {
                client[this.client[item].name] = this.client[item].val;
            }
            this.clientStore(client);
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.client) {
                this.client[item].isError = false;
                this.client[item].val = null;
            }
            console.log('clearCreateForm');
        },
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
            this.changeForm(true);
            this.clearCreateForm();
            this.$store.commit('setClient', null);
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
    .create__input_hide {
        display: none;
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
        margin-top: 20px;
        border: 2px solid transparent;
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
