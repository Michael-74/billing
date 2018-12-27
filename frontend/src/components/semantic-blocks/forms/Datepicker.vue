<template>
    <div class="fields__input fields_relative">

        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <div class="select__position">
            <app-date-picker v-if="!data.isDifference" input-class="select__data-first select__title select__date-title" :type='type' :format="format" lang="ru" v-model="data.val" :first-day-of-week="1"></app-date-picker>
            <app-date-picker v-if="data.isDifference" range input-class="select__data-first select__title select__date-title" :type='type' :format="format" lang="ru" v-model="data.val" :first-day-of-week="1"></app-date-picker>
        </div>
        <div class="fields__error" v-show='this.data.isError'>
            {{ data.errorText }}
        </div>
    </div>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['data', 'type', 'format'],
    components: {
        appDatePicker: DatePicker,
        FontAwesomeIcon
    },
    data () {
        return {

        }
    },
    methods: {

    }
}
</script>

<style>
    .select__position .mx-datepicker{
        width: 100%;
    }
    .select__position:hover .select__date-title{
        color: #FFF;
        background: #2b87db;
    }
    .select__position .select__date-title{
        color: #2b87db;
        width: 100%;
        font-size: 14px;
        padding: 7px 40px 5px 20px;
        font-family: Lato;
    }
    .select__position:hover .select__date-title {
        color: #FFF;
    }
    .select__position .select__date-title::-webkit-input-placeholder{color:#2b87db}
    .select__position .select__date-title::-moz-placeholder{color:#2b87db}
    .select__position .select__date-title:-moz-placeholder{color:#2b87db}
    .select__position .select__date-title:-ms-input-placeholder{color:#2b87db}

    .select__position:hover .select__date-title::-webkit-input-placeholder{color:#FFF}
    .select__position:hover .select__date-title::-moz-placeholder{color:#FFF}
    .select__position:hover .select__date-title:-moz-placeholder{color:#FFF}
    .select__position:hover .select__date-title:-ms-input-placeholder{color:#FFF}

    .select__position:hover .mx-input-icon {
        background: #2b87db;
        color: #FFF;
    }
    .select__position:hover .mx-input-icon {
        top: 1px;
        left: 0;
        position: absolute;
    }

    .select__position:hover .mx-calendar-icon {
        background: #2b87db;
        color: #FFF;
    }
    .select__position .mx-calendar-icon {
        color: #2b87db;
    }

    .select__position .mx-input-append {
        right: 10px;
        top: 2px;
        width: 24px;
        height: 80%;
        padding: 4px;
        color: #2b87db;
        background: #FFF;
    }
    .select__position:hover .mx-input-append {
        background: #2b87db;
        color: #FFF;
    }
    .mx-datepicker-popup {
        z-index: 1;
    }
</style>
