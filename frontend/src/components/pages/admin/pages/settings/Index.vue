<template>
    <div class="settings">
        <div class="settings__block">
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

<style lang="scss">

</style>
