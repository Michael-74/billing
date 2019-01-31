<template>
    <div class="index">
        <div class="clients__items">
            <app-smotreshka></app-smotreshka>
        </div>
    </div>
</template>

<script>
import Smotreshka from './smotreshka/Index'

export default {
    components: {
        AppSmotreshka: Smotreshka,
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss" scoped>

</style>
