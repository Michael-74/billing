<template>
    <div class="index">
        <div class="clients__filters">
            <app-filter></app-filter>
        </div>
        <div class="clients__create">
            <app-create :editItem="selectedItem"></app-create>
        </div>
        <div class="clients__items">
            <app-item :data="getInternets"></app-item>
        </div>
    </div>
</template>

<script>
import Filter from './Filter'
import Create from './Create'
import Items from './Item'

export default {
    components: {
        AppFilter: Filter,
        AppCreate: Create,
        AppItem: Items
    },
    data () {
        return {
            editItem: null
        }
    },
    created() {
        if(!this.$store.getters.getInternets.length) {
            this.$store.dispatch('getInternetsAsync');
        }
    },
    computed: {
        selectedItem: function() {
            return this.editItem = this.$store.getters.getEditInternet;
        },
        getInternets () {
            return this.$store.getters.getInternets.slice().reverse();
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
