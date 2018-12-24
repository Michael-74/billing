<template>
    <div class="index">
        <div class="clients__filters">
            <app-filter></app-filter>
        </div>
        <div class="clients__create">
            <app-create :editItem="selectedItem"></app-create>
        </div>
    </div>
</template>

<script>
import Filter from './Filter'
import Create from './Create'

export default {
    components: {
        AppFilter: Filter,
        AppCreate: Create,
    },
    data () {
        return {
            editItem: null
        }
    },
    computed: {
        selectedItem: function() {
            return this.editItem = this.$store.getters.getEditClient;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
