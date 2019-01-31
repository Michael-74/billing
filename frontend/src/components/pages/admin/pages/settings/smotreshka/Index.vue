<template>
    <div class="create">
        <div class="clients__filters">
            123
            <app-create :editItem="selectedItem"></app-create>
        </div>
    </div>
</template>

<script>
import Create from './Create'
//import Items from './Item'

export default {
    components: {
        AppCreate: Create,
        //AppItem: Items
    },
    data () {
        return {
            editItem: null
        }
    },
    computed: {
        selectedItem: function() {
            return this.editItem = this.$store.getters.getEditTv;
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
