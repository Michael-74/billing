<template>
    <div class="settings__create settings__create_border-left">
        <h2 class="create__package-h2">Настройки микротика</h2>
        <div class="create__input hide">
            <app-input :data="getClient.id"></app-input>
        </div>
        <div class="create__input settings__create_width300">
            <app-input :data="mikrotik.url"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="mikrotik.login"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="mikrotik.password"></app-input>
        </div>
        <div class="create__input create__input_width-inherit">
            <app-checkbox :data="mikrotik.isStatus"></app-checkbox>
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
            mikrotik: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                url: input('Хост', 'Введите хост', 'url', true, false, null, null),
                login: input('Логин', 'Введите логин', 'login', true, false, null, null),
                password: input('Пароль', 'Введите пароль', 'password', true, false, null, null),
                isStatus: checkbox('Статус', 'Включен', 'Выключен', 'isStatus', true, false, null, true),
            }
        }
    },
    computed: {
        getClient () {
            var isFlagFormCreate = true;
            if(this.editItem) {
                isFlagFormCreate = false;
                for (let item in this.mikrotik) {
                    this.mikrotik[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.mikrotik
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.mikrotik) {
                items[this.mikrotik[item].name] = this.mikrotik[item].val;
            }

            this.$store.dispatch('addMikrotikAsync', {items: this.mikrotik, obj: items, isFormCreate: this.isFormCreate, successFunction: () => { this.isCreateClose(); }})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.mikrotik) {
                this.mikrotik[item].isError = false;
                this.mikrotik[item].val = null;
            }
            console.log('clearCreateForm');
        },
        isCreateClose: function () {
            this.clearCreateForm();
            this.$store.commit('setMikrotik', null);
        }
    }
}
</script>

<style lang="scss" scoped>
.settings__create_width300 {
    width: 300px;
}
</style>
