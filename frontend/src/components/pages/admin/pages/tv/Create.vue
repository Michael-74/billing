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
                        <app-checkbox :data="tv.isStatus"></app-checkbox>
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
                <div class="create__package task__margin-top">
                    <app-task></app-task>
                </div>
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
            isFormCreate: true,
            isCreate: false,
            tv: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                isStatus: checkbox('Статус', 'Включен', 'Выключен', 'isStatus', true, false, null, true),
            }
        }
    },
    computed: {
        ...mapGetters([
            'getSelectedTasks'
        ]),
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
            items['tasks'] = this.getSelectedTasks;

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
            this.$store.commit('setSelectedTasks', []);
        }
    }
}
</script>

<style lang="scss" scoped>
    .task__margin-top {
        margin-top: 20px;
    }
</style>
