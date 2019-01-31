<template>
    <div class="index">

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
            tv: {
                id: input('ID', 'ID', 'id', false, false, null, null),
                name: input('Введите название', 'Введите название', 'name', true, false, null, null),
                url: input('Введите название', 'Введите название', 'url', true, false, null, null),
                login: input('Введите название', 'Введите название', 'login', true, false, null, null),
                password: input('Введите название', 'Введите название', 'password', true, false, null, null),
            }
        }
    },
    computed: {
        ...mapGetters([
            'getSelectedTasks'
        ])
    },
    methods: {

    }
}
</script>

<style lang="scss" scoped>
    .task__margin-top {
        margin-top: 20px;
    }
</style>
