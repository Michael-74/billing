<template>
    <div class="create">
        <div class="create__header">
            <button v-if="isFormCreate" class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="plus"></font-awesome-icon>
                Добавить тариф оборудование
            </button>
            <button v-else class="button button__dased" @click="isCreateShow">
                <font-awesome-icon class="create__button-dashed" icon="cog"></font-awesome-icon>
                Редактировать тариф оборудования
            </button>
        </div>
        <div class="create__body_wrapper"  v-show="isCreate">
            <div class="create__body">
                <div class="create__package">
                    <h2 class="create__package-h2">Настройка тарифа оборудования</h2>
                    <div class="create__input create__input_hide">
                        <app-input :data="getClient.id"></app-input>
                    </div>
                    <div class="create__input">
                        <app-input :data="rent.name"></app-input>
                    </div>
                    <div class="create__input create__select_width">
                        <app-checkbox :data="rent.status"></app-checkbox>
                    </div>
                    <div class="clear"></div>
                    <div class="create__note">
                        <app-textarea :data="rent.description"></app-textarea>
                    </div>
                </div>
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
import { parseObj, selectIds } from '../../../../../util/helpers'

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
            rent: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                status: checkbox('Статус', 'Включен', 'Выключен', 'status', true, false, null, true),
                description: textarea('Описание', 'Описание арендованного оборудования', 'description', true, false, null, ""),
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
                for (let item in this.rent) {
                    this.rent[item].val = this.editItem[item];
                }
            }
            this.changeForm(isFlagFormCreate);
            return this.rent
        }
    },
    methods: {
        changeForm: function(flag) {
            this.isFormCreate = flag;
        },
        addItem () {
            const items = {};
            //this.clearCreateForm();
            for(let item in this.rent) {
                items[this.rent[item].name] = this.rent[item].val;
            }
            //console.log('task', selectIds(this.getSelectedTasks));
            //items['tasks'] = JSON.stringify(selectIds(this.getSelectedTasks));
            //const tasks = JSON.stringify(selectIds(this.getSelectedTasks));
            const tasks = (selectIds(this.getSelectedTasks)).join(',');
            console.log('rent', items);

            this.$store.dispatch('addRentAsync', {items: this.rent, obj: items, isFormCreate: this.isFormCreate, tasks: tasks})
        },
        clearCreateForm () {
            this.$store.commit('clearErrors');
            for(let item in this.rent) {
                this.rent[item].isError = false;
                this.rent[item].val = null;
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
            this.$store.commit('setRent', null);
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
        padding-bottom: 20px;
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

    .task__margin-top {
        margin-top: 20px;
    }
</style>
