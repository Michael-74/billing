<template>
    <div class="fields__input">
        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <div class="fields__input-difference-block">
            <input class="fields__input-difference fields__input-difference-from fields__field" type="text" :placeholder="data.textFrom" v-model="inputFrom">
            <input class="fields__input-difference fields__input-difference-to fields__field" type="text" :placeholder="data.textTo" v-model="inputTo">
            <div class="clear"></div>
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
            inputFrom: '',
            inputTo: '',
            requared: this.data.required,
        }
    }
}
</script>

<style scoped>
    .fields__input-difference {
        float: left;
        width: 50%;
    }
    .fields__input-difference-from {
        border-top-right-radius: 0px;
        border-bottom-right-radius: 0px;
    }
    .fields__input-difference-to {
        border-top-left-radius: 0px;
        border-bottom-left-radius: 0px;
    }
</style>
