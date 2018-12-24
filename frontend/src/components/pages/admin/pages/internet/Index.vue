<template>
    <div class="index">
        <div class="clients__filters">
            <app-filter></app-filter>
        </div>
    </div>
</template>

<script>
import FilterClients from './Filter'

export default {
    components: {
        AppFilter: FilterClients,
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss" scoped>

</style>
