<template>
    <div class="fields__input fields_relative">
        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <button :class="{active: data.val}" @click="data.val=!data.val" class="button button__add">{{this.getText}}</button>
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
        getText: function () {
            if(this.data.val)
                return this.data.textTrue
            else
                return this.data.textFalse
        }
    }
}
</script>

<style scoped>
    .active {
        color: #FFF;
        background: #2b87db;
    }
</style>
