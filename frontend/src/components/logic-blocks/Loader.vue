<template>
    <div class="loading" v-show="isLoader">
        <div class="loader"></div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
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
        ...mapGetters([
            'isLoader'
        ])
    }
}
</script>

<style lang="scss" scoped>

</style>
