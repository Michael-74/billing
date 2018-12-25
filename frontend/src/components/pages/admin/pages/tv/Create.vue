<template>
    <div class="create">
        <div class="create__header">
            <button v-if="isFormCreate" class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="plus"></font-awesome-icon>
                Добавить ТВ тариф
            </button>
            <button v-else class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="cog"></font-awesome-icon>
                Редактировать ТВ тариф
            </button>
        </div>
        <div class="create__body_wrapper"  v-show="isCreate">
            <div class="create__body">
                <div class="create__package">
                    <h2 class="create__package-h2">Настройка ТВ тарифа</h2>
                    <div class="create__input create__input_hide">
                        <app-input :data="getClient.id"></app-input>
                    </div>
                    <div class="create__input">
                        <app-input :data="tv.name"></app-input>
                    </div>
                    <div class="create__input create__select_width">
                        <app-checkbox :data="tv.status"></app-checkbox>
                    </div>
                    <!--
                    <div class="create__input create__package-button">
                        <button class="button button__add" @click="show(tv)">Выбрать из существующих</button>
                    </div>
                    <div class="create__input create__package-button">
                        <button class="button button__save-package">Сохранить пакет</button>
                    </div>
                    -->
                    <div class="clear"></div>
                </div>
                <!--
                <div class="create__package create__note">
                    <h2 class="create__package-h2">Создание задачи</h2>

                    <div class="create__input create__input_note">
                        <button class="button button__add" @click="isNote = !isNote">Добавить заметку</button>
                    </div>
                    <div class="clear"></div>
                </div>
                -->
            </div>
            <div class="create__button-save">
                <button v-if="isFormCreate" class="button button__save button__save-user" @click="addItem">Сохранить тариф</button>
                <button v-else class="button button__save button__save-user" @click="addItem">Редактировать тариф</button>
                <button class="button button__save button__cancel-user button__cancel-user_margin" @click="isCreateClose">Отмена</button>
            </div>
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
        FontAwesomeIcon
    },
    props: ['editItem'],
    data () {
        return {
            isFormCreate: true,
            isCreate: false,
            tv: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                status: checkbox('Статус', 'Включен', 'Выключен', 'status', true, false, null, true),
            }
        }
    },
    computed: {
        getClient () {
            var isFlagFormCreate = true;
            if(this.editItem) {
                isFlagFormCreate = false;
                for (let item in this.tv) {
                    this.tv[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.tv
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.tv) {
                items[this.tv[item].name] = this.tv[item].val;
            }
            this.$store.dispatch('addTvAsync', {items: this.tv, obj: items, isFormCreate: this.isFormCreate})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.tv) {
                this.tv[item].isError = false;
                this.tv[item].val = null;
            }
            console.log('clearCreateForm');
        },
        isCreateShow: function () {
            this.isCreate = true;
        },
        isCreateClose: function () {
            this.isCreate = false;
            this.changeForm(true);
            this.clearCreateForm();
            this.$store.commit('setTv', null);
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
