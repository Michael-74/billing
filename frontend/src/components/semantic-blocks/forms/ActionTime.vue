<template>
    <div class="fields__select fields_relative">
        <div v-show="label">
            <div class="fields__label">{{label}} <span class="fields__required" v-show='isRequired'>*</span></div>
        </div>
        <div class="fields__item">
            <div class="select__title">
                <span class="fields_blue-black">C</span>
                <span class="fields_blue-color">
                    <input placeholder="__" maxlength="2" class="fields__action-time" v-model="data.val.dayStart">
                    дня</span>
                <span class="fields_blue-color">
                    <input placeholder="__" maxlength="2" class="fields__action-time" v-model="data.val.monthStart">
                    месяца</span>
                <span class="fields__hr"></span>
                <span class="fields_blue-black">По</span>
                <span class="fields_blue-color">
                    <input placeholder="__" maxlength="2" class="fields__action-time" v-model="data.val.dayEnd">
                    день</span>
                <span class="fields_blue-color">
                    <input placeholder="__" maxlength="2" class="fields__action-time" v-model="data.val.monthEnd">
                    месяца</span>
            </div>
        </div>
        <div class="fields__error" v-show='this.data.isError'>
            {{ data.errorText }}
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['data', 'label', 'isRequired'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
        }
    },
    methods: {

    },
    computed: {

    }
}
</script>

<style scoped>
    .fields__action-time {
        border: 0px;
        width: 20px;
        text-align: right;
    }
    .fields_blue-color {
        color: #398fdd;
    }
    .select__title:hover {
        background: #FFF;
        color: #398fdd;
        cursor: default;
    }
    .fields_blue-black {
        color: #333333;
    }
    .fields__hr {
        border-right: 1px solid #398fdd;
        height: 10px;
        margin: 0 5px;
        display: inline-block;
    }
</style>
