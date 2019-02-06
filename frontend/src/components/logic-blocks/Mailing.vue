<template>
    <div class="mail create__body">
        <div class="create__package">
            <h2 class="create__package-h2">Отправка сообщения</h2>
            <div class="create__input create__input_full-width create__input_margin">
                <app-textarea :data="mail.message"></app-textarea>
            </div>
            <div class="create__input create__input_full-width">
                <div class="mail__checkbox">
                    <app-checkbox :data="mail.isSms"></app-checkbox>
                </div>
                <div class="mail__checkbox">
                    <app-checkbox :data="mail.isEMail"></app-checkbox>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
            <div class="create__button-mail">
                <button class="button button__save button__save-user">Отправить</button>
            </div>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import { input, select, selectMultiple, checkbox2, datepicker, inputDifference, textarea } from '../../util/fields'
import Textarea from '../semantic-blocks/forms/Textarea'
import Checkbox2 from '../semantic-blocks/forms/Checkbox2'

export default {
    components: {
        FontAwesomeIcon,
        AppCheckbox: Checkbox2,
        AppTextarea: Textarea,
    },
    data () {
        return {
            mail: {
                isSms: checkbox2('Отправить по смс', 'isSms', false, false, null, false),
                isEMail: checkbox2('Отправить на Email', 'isEmail', false, false, null, false),
                message: textarea('Введите текст сообщения', 'Введите текст', 'message', false, false, null, null),
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
.mail__checkbox {
    float: left;
    margin-right: 20px;
}
</style>
