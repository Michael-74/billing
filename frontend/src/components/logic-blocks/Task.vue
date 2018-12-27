<template>
    <div class="wrapper__task">
        <div class="task">
            <h2 class="create__package-h2">Создание задачи</h2>
            <div class="task__input">
                <app-input :data="task.name"></app-input>
            </div>
            <div class="task__input">
                <app-input :data="task.price"></app-input>
            </div>
            <div class="task__field_float task__select_padding">
                <app-type-write-off></app-type-write-off>
            </div>
            <div class="clear"></div>
            <div class="task__field_float task__select_padding task_margin-top">
                <app-action-time label="Время действия" :isRequired=true :data="task.actionTime"></app-action-time>
            </div>
            <div class="clear"></div>
            <h2 class="create__package-h2">Дополнительно</h2>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import Input from '../semantic-blocks/forms/Input'
import DifferenceInput from '../semantic-blocks/forms/DifferenceInput'
import Select from '../semantic-blocks/forms/Select'
import TypeWriteOff from '../logic-blocks/TypeWriteOff'
import ActionTime from '../semantic-blocks/forms/ActionTime'

import { input, select, selectMultiple, checkbox, datepicker, inputDifference, actionTime } from '../../util/fields'

export default {
    components: {
        AppInput: Input,
        AppDifferenceInput: DifferenceInput,
        AppSelect: Select,
        AppTypeWriteOff: TypeWriteOff,
        AppActionTime: ActionTime,
        FontAwesomeIcon
    },
    data () {
        return {
            task: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                price: input('Введите сумму', 'Введите сумму', 'price', true, false, null, null),
                actionTime: actionTime('actionTime', false, null, {
                    day_start: null,
                    month_start: null,
                    day_end: null,
                    month_end: null
                }),
            }
        }
    },
    methods: {

    },
    computed: {

    }
}
</script>

<style lang="scss" scoped>
    .create__package-h2 {
        margin-bottom: 20px;
    }
    .task__field_float {
        float: left;
        margin-right: 20px;
    }
    .task__select {
        width: 160px;
    }
    .task__select_padding {
        margin: 0px 20px 20px 0;
    }
    .task__input {
        margin: 0 20px 30px 0;
        width: 230px;
        float: left;
    }
    .task_margin-top {
        margin-top: -30px;
    }
</style>
