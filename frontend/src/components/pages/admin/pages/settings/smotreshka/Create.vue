<template>
    <div class="settings__create settings__create_border-left">
        <h2 class="create__package-h2">Настройки смотрешка</h2>
        <div class="create__input hide">
            <app-input :data="getClient.id"></app-input>
        </div>
        <div class="create__input settings__create_width300">
            <app-input :data="smotreshka.url"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="smotreshka.login"></app-input>
        </div>
        <div class="create__input">
            <app-input :data="smotreshka.password"></app-input>
        </div>
        <div class="settings__button_padding">
            <button class="button button__add" @click="addItem">Сохранить</button>
        </div>
        <div class="clear"></div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Input from '../../../../../semantic-blocks/forms/Input'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import axios from "axios";
import { input } from '../../../../../../util/fields'
import { parseObj } from '../../../../../../util/helpers'

export default {
    components: {
        AppInput: Input,
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            smotreshka: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                url: input('URL', 'Введите url', 'url', true, false, null, null),
                login: input('Логин', 'Введите логин', 'login', true, false, null, null),
                password: input('Пароль', 'Введите пароль', 'password', true, false, null, null),
            }
        }
    },
    computed: {
        getClient () {
            if(this.editItem) {
                for (let item in this.smotreshka) {
                    this.smotreshka[item].val = this.editItem[item];
                }
            }
            return this.smotreshka
        }
    },
    methods: {
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
            for(let item in this.internet) {
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
</style>
