<template>
    <div class="index">
        <div class="clients__filters">
            <app-filter></app-filter>
        </div>
        <div class="clients__create">
            <app-create :editItem="selectedItem"></app-create>
        </div>
        <div class="clients__items">
            <app-item :data="getTvs"></app-item>
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
            if(!this.$store.getters.getTvs.length) {
                this.$store.dispatch('getTvsAsync');
            }
        },
        computed: {
            selectedItem: function() {
                return this.editItem = this.$store.getters.getEditTv;
            },
            getTvs () {
                return this.$store.getters.getTvs.slice().reverse();
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>
