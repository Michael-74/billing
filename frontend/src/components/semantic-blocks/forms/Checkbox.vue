<template>
    <div class="fields__input">
        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <button :class="{active: isCheckbox}" @click="isCheckbox=!isCheckbox " class="button button__add">{{this.getText}}</button>
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
            d: this.data,
            isCheckbox: false,
            requared: this.data.required,
        }
    },
    computed: {
        getText: function () {
            if(this.d.text !== '') {
                return this.d.text
            } else {
                if(this.isCheckbox)
                    return 'Включен'
                else
                    return 'Выключен'
            }
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
