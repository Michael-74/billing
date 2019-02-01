<template>
    <div class="settings__create settings__create_border-left">
        <h2 class="create__package-h2">Настройки смс</h2>
        <div class="create__input hide">
            <app-input :data="getClient.id"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="email.host"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="email.port"></app-input>
        </div>
        <div class="create__input create__select_width">
            <app-select :data="email.typeEncryption"></app-select>
        </div>
        <div class="create__input">
            <app-input :data="email.login"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="email.password"></app-input>
        </div>
        <div class="create__input create__input_width-inherit">
            <app-checkbox :data="email.isStatus"></app-checkbox>
        </div>
        <div class="settings__button_padding create__input create__input_width-inherit">
            <button v-if="isFormCreate" class="button button__add" @click="addItem">Сохранить</button>
            <button v-else class="button button__add" @click="addItem">Редактировать</button>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Input from '../../../../../semantic-blocks/forms/Input'
import Checkbox from '../../../../../semantic-blocks/forms/Checkbox'
import Select from '../../../../../semantic-blocks/forms/Select'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { input, checkbox, select } from '../../../../../../util/fields'
import { parseObj } from '../../../../../../util/helpers'

export default {
    components: {
        AppInput: Input,
        AppCheckbox: Checkbox,
        AppSelect: Select,
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            isFormCreate: true,
            email: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                host: input('Хост', 'Введите хост', 'host', true, false, null, null),
                port: input('Порт', 'Введите порт', 'port', true, false, null, null),
                typeEncryption: select('Тип шифрования', 'Не выбрано', 'typeEncryption', true, false, null, null, [{id:"ssl", val:"SSL"}, {id:"web", val:"web"}]),
                login: input('Логин', 'Введите логин', 'login', true, false, null, null),
                password: input('Пароль', 'Введите пароль', 'password', true, false, null, null),
                isStatus: checkbox('Статус', 'Включен', 'Выключен', 'isStatus', true, false, null, true),
            }
        }
    },
    computed: {
        getData(){
            return this.email;
        },
        getClient () {
            var isFlagFormCreate = true;
            if(this.editItem) {
                isFlagFormCreate = false;
                for (let item in this.email) {
                    this.email[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.email
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.email) {
                items[this.email[item].name] = this.email[item].val;
            }

            this.$store.dispatch('addEmailsAsync', {items: this.email, obj: items, isFormCreate: this.isFormCreate, successFunction: () => { this.isCreateClose(); }})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.email) {
                this.email[item].isError = false;
                this.email[item].val = null;
            }
            console.log('clearCreateForm');
        },
        isCreateClose: function () {
            this.clearCreateForm();
            this.$store.commit('setEmail', null);
        }
    }
}
</script>

<style lang="scss" scoped>
.settings__create_width300 {
    width: 300px;
}
.settings__create_border-left{
    border-left:2px solid #8e2bdb;
}
</style>
