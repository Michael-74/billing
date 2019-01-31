<template>
    <div class="settings">
        <div class="settings__block">
            <app-smotreshka></app-smotreshka>
        </div><br><br>
        <div class="settings__block">
            <app-mikrotik></app-mikrotik>
        </div>
    </div>
</template>

<script>
import Smotreshka from './smotreshka/Index'
import Mikrotik from './mikrotik/Index'

export default {
    components: {
        AppSmotreshka: Smotreshka,
        AppMikrotik: Mikrotik,
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss">

</style>
