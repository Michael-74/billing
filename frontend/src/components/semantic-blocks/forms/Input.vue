<template>
    <div class="fields__input">
        <div class="fields__label" v-show="data.label">
            {{data.label}} <span class="fields__required" v-show='data.required'>*</span>
        </div>
        <input class="fields__field" type="text" :placeholder="data.text" v-model="input">
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
            requared: this.data.required,
        }
    }
}
</script>

<style>

</style>
