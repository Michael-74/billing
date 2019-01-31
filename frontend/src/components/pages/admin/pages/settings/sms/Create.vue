<template>
    <div class="settings__create settings__create_border-left">
        <h2 class="create__package-h2">Настройки микротика</h2>
        <div class="create__input hide">
            <app-input :data="getClient.id"></app-input>
        </div>
        <div class="create__input settings__create_width300">
            <app-input :data="sms.host"></app-input>
        </div>
        <div class="create__input settings__create_width300">
            <app-input :data="sms.port"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="sms.login"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="sms.password"></app-input>
        </div>
        <div class="create__input create__input_width-inherit">
            <app-checkbox :data="sms.isStatus"></app-checkbox>
        </div>
        <div class="settings__button_padding">
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
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { input, checkbox } from '../../../../../../util/fields'
import { parseObj } from '../../../../../../util/helpers'

export default {
    components: {
        AppInput: Input,
        AppCheckbox: Checkbox,
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            isFormCreate: true,
            sms: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                host: input('Хост', 'Введите хост', 'host', true, false, null, null),
                port: input('Порт', 'Введите порт', 'port', true, false, null, null),
                login: input('Логин', 'Введите логин', 'login', true, false, null, null),
                password: input('Пароль', 'Введите пароль', 'password', true, false, null, null),
                isStatus: checkbox('Статус', 'Включен', 'Выключен', 'isStatus', true, false, null, true),
            }
        }
    },
    computed: {
        getData(){
            return this.sms;
        },
        getClient () {
            var isFlagFormCreate = true;
            if(this.editItem) {
                isFlagFormCreate = false;
                for (let item in this.sms) {
                    this.sms[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.sms
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.sms) {
                items[this.sms[item].name] = this.sms[item].val;
            }

            this.$store.dispatch('addSmsAsync', {items: this.sms, obj: items, isFormCreate: this.isFormCreate, successFunction: () => { this.isCreateClose(); }})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.sms) {
                this.sms[item].isError = false;
                this.sms[item].val = null;
            }
            console.log('clearCreateForm');
        },
        isCreateClose: function () {
            this.clearCreateForm();
            this.$store.commit('setSms', null);
        }
    }
}
</script>

<style lang="scss" scoped>
.settings__create_width300 {
    width: 300px;
}
.settings__create_border-left{
    border-left:2px solid #2b87db;
}
</style>
