<template>
    <div class="fields__input fields_relative">
        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <textarea class="fields__field fields__textarea" type="text" :class="{fields_red: this.data.isError}" :placeholder="data.text" v-model="data.val"></textarea>
        <div class="fields__error" v-show='this.data.isError'>
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
            input: '',
        }
    }
}
</script>

<style>

</style>
