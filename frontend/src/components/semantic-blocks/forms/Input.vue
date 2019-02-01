<template>
    <div class="fields__input fields_relative">
        <div class="fields__label" v-html="data.label" v-show="data.label">
            {{ data.label }} <span class="fields__required" v-show='data.isRequired'>*</span>
        </div>
        <input class="fields__field" :class="{fields_red: this.data.isError}" :type="getType" :placeholder="data.text" v-model="data.val">
        <div class="fields__error" v-show='this.data.isError'>
            {{ data.errorText }}
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['data', 'type'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
            input: this.data.val
        }
    },
    watch: {
        input: function () {
            this.data.val = this.input
        }
    },
    computed: {
        getType(){
            return this.type ? this.type : "text"
        }
    }
}
</script>

<style>

</style>
