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
                    <app-input :data="client.login"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.ip"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.balance"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.contract"></app-input>
                </div>
                <div class="create__input">
                    <app-input :data="client.priceOverMonth"></app-input>
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
                <div class="create__input">
                    <app-checkbox :data="client.promised"></app-checkbox>
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
                        <app-input :data="packages.name"></app-input>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select :data="packages.internet"></app-select>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select-multiple :data="packages.tv"></app-select-multiple>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select-multiple :data="packages.rent"></app-select-multiple>
                    </div>
                    <div class="create__input create__package-button">
                        <button class="button button__add" @click="showPackage(packages)">Выбрать из существующих</button>
                    </div>
                    <div class="create__input create__package-button">
                        <button class="button button__save-package" @click="savePackage">Сохранить пакет</button>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="create__package create__note">
                    <h2 class="create__package-h2">Заметки об абоненте</h2>
                    <div class="create__input create__input_note">
                        <button class="button button__add" @click="isNote = !isNote">Добавить заметку</button>
                    </div>
                    <div class="create__input create__input_full-width" v-show="isNote">
                        <app-textarea :data="note.textareaNote"></app-textarea>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="create__package task__margin-top">
                    <app-task></app-task>
                </div>
            </div>
            <div class="create__button-save">
                <button v-if="isFormCreate" class="button button__save button__save-user" @click="addClient">Сохранить пользователя</button>
                <button v-else class="button button__save button__save-user" @click="addClient">Редактировать пользователя</button>
                <button class="button button__save button__cancel-user button__cancel-user_margin" @click="isCreateClose">Отмена</button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Input from '../../../../semantic-blocks/forms/Input'
import DifferenceInput from '../../../../semantic-blocks/forms/DifferenceInput'
import Select from '../../../../semantic-blocks/forms/Select'
import SelectMultiple from '../../../../semantic-blocks/forms/SelectMultiple'
import Textarea from '../../../../semantic-blocks/forms/Textarea'
import Checkbox from '../../../../semantic-blocks/forms/Checkbox'
import Task from '../../../../logic-blocks/Task'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { sendClient } from "../../../../../util/ws";
import { input, select, selectMultiple, checkbox, datepicker, inputDifference, textarea } from '../../../../../util/fields'
import { parseObj } from '../../../../../util/helpers'

export default {
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppSelectMultiple: SelectMultiple,
        AppCheckbox: Checkbox,
        AppTextarea: Textarea,
        AppTask: Task,
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            isNote: false,
            isFormCreate: true,
            isCreate: false,
            client: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                login: input('Логин', 'Логин', 'login', true, false, null, null),
                ip: input('IP адрес', 'IP адрес', 'ip', true, false, null, null),
                balance: input('Баланс', 'Баланс', 'balance', true, false, null, null),
                contract: input('Номер договора', 'Номер договора', 'contract', true, false, null, null),
                priceOverMonth: input('Цена до конца месяца', 'Цена до конца месяца', 'price_over_month', true, false, null, null),
                promised: checkbox('Обещанный платеж', 'Включен', 'Выключен', 'promised', true, false, null, true),
                fio: input('ФИО', 'ФИО', 'fio', true, false, null, null),
                address: input('Адрес', 'Адрес', 'address', true, false, null, null),
                phone: input('Телефон', 'Телефон', 'phone', true, false, null, null),
                email: input('Email', 'Email', 'email', true, false, null, null),
                discount: input('Скидка', 'Скидка', 'discount', true, false, null, null),
                loyalty: select('Лояльность', 'Не выбрано', 'loyalty', true, false, null, null, [{id:1, val:1}, {id:2, val:2}]),
                type_discount: select('Тип скидки', 'Не выбрано', 'type_discount', true, false, null, null, [{id:1, val:1}, {id:2, val:2}]),
                status: select('Статус', 'Не выбрано', 'status', true, false, null, null, [{id:1, val:1}, {id:2, val:2}])
            },
            packages: {
                name: input('Введите название', 'Введите название пакета', 'name', true, false, null, null),
                internet: select('Интернет тариф', 'Не выбрано', 'internet', true, false, null, null, []),
                tv: selectMultiple('Смотрешка', 'Не выбрано', 'tv', true, false, null, [], []),
                rent: selectMultiple('Аренда оборудования', 'Не выбрано', 'rent', true, false, null, [], []),
            },
             note: {
                textareaNote: textarea(null, 'Введите текст', 'note', true, false, null, null)
            }
        }
    },
    mounted () {
        this.packages.internet.items = this.getListInternets;
        this.packages.rent.items = this.getListRents;
        this.packages.tv.items = this.getListTvs;
    },
    watch: {
        getListInternets() {
            this.packages.internet.items = this.getListInternets;
        },
        getListRents() {
            this.packages.rent.items = this.getListRents;
        },
        getListTvs() {
            this.packages.tv.items = this.getListTvs;
        }
    },
    computed: {
        ...mapGetters([
            'getListInternets', 'getListRents', 'getListTvs'
        ]),
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
        savePackage: function () {
            console.log("savePackage", this.packages);
            var pack = {};
            for(let item in this.packages) {
                if(Array.isArray(this.packages[item].val)){
                    pack[this.packages[item].name] = this.packages[item].val.join()
                } else {
                    pack[this.packages[item].name] = this.packages[item].val;
                }
            }
            console.log("savePackage2", pack);
            this.$store.dispatch('addPackAsync', {obj: pack, items: this.packages, isFormCreate: true})
        },
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addClient () {
            const client = {};
            //this.clearCreateForm();
            for(let item in this.client) {
                client[this.client[item].name] = this.client[item].val;
            }
            this.$store.dispatch('addClientAsync', {items: this.client, obj: client, isFormCreate: this.isFormCreate})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.client) {
                this.client[item].isError = false;
                this.client[item].val = null;
            }
        },
        showPackage (packages) {
            this.$store.dispatch('getPacksAsync');

            this.$modal.show({
                components:{
                    FontAwesomeIcon
                },
                computed: {
                    ...mapGetters([
                        'getListInternets', 'getListRents', 'getListTvs', 'getPacks'
                    ]),
                    getPackage() {
                        var data = [];

                        for(let index in this.getPacks) {
                            let service = {id:null, name:null, internet:null, tv:null, rent:null};

                            service.id = this.getPacks[index].id;
                            service.name = this.getPacks[index].name;
                            data[index] = service;
                            this.getListInternets.filter(item => {

                                if(item.id === Number(this.getPacks[index].internet)) {
                                    data[index].internet = item.val;
                                }
                            });
                            var tv = this.getPacks[index].tv.split(',');
                            this.getListTvs.filter(item => {
                                for(let nameTv in tv){
                                    console.log("nameTv", nameTv);
                                    if(item.id === Number(tv[nameTv])) {
                                        data[index].tv += "<p>" + item.val + "</p>";
                                    }
                                }
                            });
                            //TODO:: добавить проверку на null и 0
                            var rent = this.getPacks[index].rent.split(',');
                            this.getListRents.filter(item => {
                                for(let nameRent in rent){
                                    if(item.id === Number(rent[nameRent])) {
                                        data[index].rent += "<p>" + item.val + "</p>";
                                    }
                                }
                            });

                        }

                        return data;
                    },
                    getInternet() {
                        console.log('getListInternets', this.getListInternets);
                        console.log('getPacks', this.getPacks);
                        return this.getListInternets.filter(function(item){
                            for(let pack in this.getPacks) {
                                console.log('pack', pack);
                                if(item.id === Number(pack.internet)) {
                                    console.log('item - pack', item);
                                    return true;
                                }
                            }
                        });
                    }
                },
                methods: {
                    deletePackage (packageId) {
                        this.$store.dispatch('deletePackAsync', {id: packageId})
                    },
                    // Проверяем чтобы в данных не было пустых значений
                    checkItemArray(item) {
                        if(item === "0" || item === "")
                            return [];
                        else
                            return item.split(',').map(el => {return Number(el)});
                    },
                    // Проверяем чтобы в данных не было пустых значений
                    checkItem(item) {
                        if(item === 0 || item === null)
                            return null;
                        else
                            return Number(item);
                    },
                    selectPackage (item) {
                        packages.name.val = item.name;
                        packages.internet.val = this.checkItem(item.internet);
                        packages.tv.val = this.checkItemArray(item.tv);
                        packages.rent.val = this.checkItemArray(item.rent);

                        console.log("data", this.$store.getters.getListInternets);

                        this.$emit('close');
                    }
                },
                template: `
                    <div class="modal__block modal__block_width400">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon" icon="times-circle"></font-awesome-icon>
                            Закрыть
                        </div>
                        <h3 class="modal__h3">Список пакетов</h3>
                        <table class="modal__package-table items__table">
                            <thead class="items__thead">
                                <tr>
                                    <th class="modal__th items__th">N</th>
                                    <th class="modal__th items__th">Название</th>
                                    <th class="modal__th items__th">Интернет</th>
                                    <th class="modal__th items__th">Смотрешка</th>
                                    <th class="modal__th items__th">Оборудование</th>
                                    <th class="modal__th items__th">Действие</th>
                                </tr>
                            </thead>
                            <tbody class="items__tbody">
                                <tr class="modal__tr" v-for="item in getPackage">
                                    <td class="items__td" @click="selectPackage(item)">{{ item.id }}</td>
                                    <td class="items__td" @click="selectPackage(item)">{{ item.name }}</td>
                                    <td class="items__td" @click="selectPackage(item)">{{ item.internet }}</td>
                                    <td class="items__td" @click="selectPackage(item)" v-html="item.tv"></td>
                                    <td class="items__td" @click="selectPackage(item)"v-html="item.rent"></td>
                                    <td class="items__td">
                                        <!--<font-awesome-icon class="items__icon" icon="cog" @click="editClient(item)"></font-awesome-icon>-->
                                        <font-awesome-icon class="items__icon" icon="times-circle" @click="deletePackage(item.id)"></font-awesome-icon>
                                    </td>
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
    .create__input_note {
        margin-bottom: 20px;
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
        margin: 20px 0 20px;
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
