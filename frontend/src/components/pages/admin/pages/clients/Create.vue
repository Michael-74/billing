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
                    <app-checkbox :data="client.isPromisedPay"></app-checkbox>
                </div>
                <div class="clear"></div>
                <div class="create__input create__input_width-discount">
                    <app-input :data="client.discount"></app-input>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="client.typeDiscount"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-select :data="client.loyalty"></app-select>
                </div>
                <div class="create__input create__select_width">
                    <app-checkbox :data="client.isStatus"></app-checkbox>
                </div>
                <div class="clear"></div>
                <div class="create__package">
                    <h2 class="create__package-h2">Настройка пакета услуг</h2>
                    <div class="create__input">
                        <app-input :data="packages.name"></app-input>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select :data="client.internet"></app-select>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select-multiple :data="client.tvs"></app-select-multiple>
                    </div>
                    <div class="create__input create__select_width">
                        <app-select-multiple :data="client.rents"></app-select-multiple>
                    </div>
                    <div class="create__input create__package-button">
                        <button class="button button__add" @click="showPackage(packages, client)">Выбрать из существующих</button>
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
                        <app-textarea :data="client.note"></app-textarea>
                    </div>
                    <div class="clear"></div>
                </div>
                <!--
                <div class="create__package task__margin-top">
                    <app-task></app-task>
                </div>
                -->
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
import { parseObj, parseServicesForId } from '../../../../../util/helpers'

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
                isPromisedPay: checkbox('Обещанный платеж', 'Включен', 'Выключен', 'isPromisedPay', true, false, null, true),
                fio: input('ФИО', 'ФИО', 'fio', true, false, null, null),
                address: input('Адрес', 'Адрес', 'address', true, false, null, null),
                phone: input('Телефон', 'Телефон', 'phone', true, false, null, null),
                email: input('Email', 'Email', 'email', true, false, null, null),
                discount: input('Скидка', 'Скидка', 'discount', true, false, null, 0),
                loyalty: select('Лояльность', 'Не выбрано', 'loyalty', true, false, null, null, [{id:1, val:1}, {id:2, val:2}, {id:3, val:3}, {id:4, val:4}, {id:5, val:5}, {id:6, val:6}, {id:7, val:7}, {id:8, val:8}, {id:9, val:9}, {id:10, val:10}]),
                typeDiscount: select('Тип скидки', 'Не выбрано', 'typeDiscount', true, false, null, null, [{id:"discount10", val:"Скидка 10%"}, {id:"discount20", val:"Скидка 20%"}]),
                isStatus: checkbox('Статус', 'Включен', 'Выключен', 'isStatus', true, false, null, true),
                note: textarea(null, 'Введите текст', 'note', true, false, null, null),
                internet: select('Интернет тариф', 'Не выбрано', 'internet', true, false, null, null, []),
                tvs: selectMultiple('Смотрешка', 'Не выбрано', 'tvs', true, false, null, [], []),
                rents: selectMultiple('Аренда оборудования', 'Не выбрано', 'rents', true, false, null, [], []),
            },
            packages: {
                name: input('Введите название', 'Введите название пакета', 'name', true, false, null, null)
            }
        }
    },
    mounted () {
        this.client.internet.items = this.getListInternets;
        this.client.rents.items = this.getListRents;
        this.client.tvs.items = this.getListTvs;
    },
    watch: {
        getListInternets() {
            this.client.internet.items = this.getListInternets;
        },
        getListRents() {
            this.client.rents.items = this.getListRents;
        },
        getListTvs() {
            this.client.tvs.items = this.getListTvs;
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
                    this.client[item].val = parseServicesForId(this.editItem, item);
                }
            }
            this.changeForm(isFlagFormCreate);

            return this.client
        }
    },
    methods: {
        savePackage: function () {
            const unionPack = {
                name: this.packages.name,
                internet: this.client.internet,
                tvs: this.client.tvs,
                rents: this.client.rents,
            };
            var pack = {};
            for(let item in unionPack) {
                if(Array.isArray(unionPack[item].val)){
                    pack[unionPack[item].name] = unionPack[item].val.join()
                } else {
                    pack[unionPack[item].name] = unionPack[item].val;
                }
            }

            this.$store.dispatch('addPackAsync', {obj: pack, items: unionPack, isFormCreate: true})
        },
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addClient () {
            const client = {};
            //this.clearCreateForm();
            for(let item in this.client) {
                switch(item) {
                    case 'internet':
                        const internetArray = this.getListInternets.filter((obj, i) => {
                            if(obj.id === Number(this.client[item].val)){
                                return true;
                            }
                        });
                        let newObj = internetArray.length !== 0 ? internetArray[0] : null;
                        client[this.client[item].name] = newObj;
                        break;
                    case 'tvs':
                        const tvArray = this.getListTvs.filter((obj, i) => {
                            let isMatches = false;
                            this.client[item].val.map((obj2) => {
                                if(obj.id === Number(obj2)){
                                    isMatches = true;
                                }
                            });
                            return isMatches;
                        });
                        client[this.client[item].name] = tvArray;
                        break;
                    case 'rents':
                        const rentArray = this.getListRents.filter((obj, i) => {
                            let isMatches = false;
                            this.client[item].val.map((obj2) => {
                                if(obj.id === Number(obj2)){
                                    isMatches = true;
                                }
                            });
                            return isMatches;
                        });
                        client[this.client[item].name] = rentArray;
                        break;
                    default:
                        client[this.client[item].name] = this.client[item].val;
                        break;
                }

            }
            this.$store.dispatch('addClientAsync', {items: this.client, obj: client, isFormCreate: this.isFormCreate})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.client) {
                this.client[item].isError = false;
                if(Array.isArray(this.client[item].val))
                    this.client[item].val = [];
                else
                    this.client[item].val = null;
            }
        },
        showPackage (packages, client) {
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
                            let service = {id:null, name:null, internet:{name:null, val:null}, tv:{name:[], val:[]}, rent:{name:[], val:[]}};

                            service.id = this.getPacks[index].id;
                            service.name = this.getPacks[index].name;
                            data[index] = service;

                            this.getListInternets.filter(item => {

                                if(item.id === Number(this.getPacks[index].internet)) {
                                    data[index].internet.name = item.val;
                                    data[index].internet.val = Number(this.getPacks[index].internet);
                                }
                            });

                            var tvList = this.getPacks[index].tvs;
                            if(tvList !== null) {
                                let tv = tvList.split(',');
                                this.getListTvs.filter(item => {
                                    for (let nameTv in tv) {
                                        if (item.id === Number(tv[nameTv])) {
                                            data[index].tv.name.push(item.val);
                                            data[index].tv.val.push(Number(tv[nameTv]));
                                        }
                                    }
                                });
                            }

                            var rentList = this.getPacks[index].rents;
                            if(rentList !== null) {
                                let rent = rentList.split(',');
                                this.getListRents.filter(item => {
                                    for (let nameRent in rent) {
                                        if (item.id === Number(rent[nameRent])) {
                                            data[index].rent.name.push(item.val);
                                            data[index].rent.val.push(Number(rent[nameRent]));
                                        }
                                    }
                                });
                            }
                        }
                        return data;
                    }
                },
                methods: {
                    deletePackage (packageId) {
                        this.$store.dispatch('deletePackAsync', {id: packageId})
                    },
                    // Указываем выбранный пакет
                    selectPackage (item) {
                        packages.name.val = item.name;
                        client.internet.val = item.internet.val;
                        client.tvs.val = item.tv.val;
                        client.rents.val = item.rent.val;

                        this.$emit('close');
                    }
                },
                template: `
                    <div class="modal__block modal__block_width400">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon modal__icon_red" icon="times-circle"></font-awesome-icon>
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
                                    <td class="items__td" @click="selectPackage(item)">
                                        <div class="">
                                            <span class="items__pack" v-if="item.internet.name">{{ item.internet.name }}</span>
                                        </div>
                                    </td>
                                    <td class="items__td" @click="selectPackage(item)">
                                        <div class="" v-for="tv in item.tv.name">
                                            <span class="items__pack">{{ tv }}</span>
                                        </div>
                                    </td>
                                    <td class="items__td" @click="selectPackage(item)">
                                        <div class="" v-for="internet in item.rent.name">
                                            <span class="items__pack">{{ internet }}</span>
                                        </div>
                                    </td>
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
