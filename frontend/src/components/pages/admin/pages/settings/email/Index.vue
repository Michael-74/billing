<template>
    <div class="wrapper__settings">
        <div class="wrapper__create">
            <app-create :editItem="selectedItem"></app-create>
        </div>
        <div class="wrapper__item">
            <app-item :data="getItems"></app-item>
        </div>
    </div>
</template>

<script>
import Create from './Create'
import Items from './Item'

export default {
    components: {
        AppCreate: Create,
        AppItem: Items
    },
    data () {
        return {
            editItem: null
        }
    },
    created(){
        this.$store.dispatch('getEmailsAsync');
    },
    computed: {
        selectedItem: function() {
            return this.$store.getters.getEditSms;
        },
        getItems () {
            return this.$store.getters.getSmses;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
