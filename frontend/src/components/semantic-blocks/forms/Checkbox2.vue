<template>
    <div class="fields__input fields_relative">
        <div class="fields__label" v-show="data.label" @click="data.val=!data.val">
            <font-awesome-icon class="check__icon" :icon=getCheck></font-awesome-icon>
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <div class="fields__error" v-show='!data.isError'>
            {{ data.errorText }}
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['data'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {

        }
    },
    computed: {
        getCheck() {
            if(this.data.val)
                return "check-square";
            else
                return "square";
        }
    }
}
</script>

<style scoped>
    .fields__label {
        cursor: pointer;
    }
    .check__icon {
        color: #2b87db;
        margin-right: 5px;
        font-size: 16px;
    }
    .active {
        color: #FFF;
        background: #2b87db;
    }
</style>
