<template>
    <div class="settings__create settings__create_border-left">
        <h2 class="create__package-h2">Настройки смотрешка</h2>
        <div class="create__input hide">
            <app-input :data="getClient.id"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="smotreshka.url"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="smotreshka.login"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="smotreshka.password"></app-input>
        </div>
        <div class="create__input create__input_width-inherit">
            <app-checkbox :data="smotreshka.isStatus"></app-checkbox>
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
            smotreshka: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                url: input('URL', 'Введите url', 'url', true, false, null, null),
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
                for (let item in this.smotreshka) {
                    this.smotreshka[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.smotreshka
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.smotreshka) {
                items[this.smotreshka[item].name] = this.smotreshka[item].val;
            }

            this.$store.dispatch('addSmotreshkaAsync', {items: this.smotreshka, obj: items, isFormCreate: this.isFormCreate, successFunction: () => { this.isCreateClose(); }})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.smotreshka) {
                this.smotreshka[item].isError = false;
                this.smotreshka[item].val = null;
            }
            console.log('clearCreateForm');
        },
        isCreateClose: function () {
            this.clearCreateForm();
            this.$store.commit('setSmotreshka', null);
        }
    }
}
</script>

<style lang="scss" scoped>
.settings__create_width300 {
    width: 300px;
}
.settings__create_border-left{
    border-left:2px solid #42dfaa;
}
</style>
