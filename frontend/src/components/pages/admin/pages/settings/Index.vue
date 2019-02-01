<template>
    <div class="settings">
        <div class="settings__block">
            <app-smotreshka></app-smotreshka>
        </div><br><br>
        <div class="settings__block">
            <app-mikrotik></app-mikrotik>
        </div><br><br>
        <div class="settings__block">
            <app-email></app-email>
        </div><br><br>
        <div class="settings__block">
            <app-sms></app-sms>
        </div>
    </div>
</template>

<script>
import Smotreshka from './smotreshka/Index'
import Mikrotik from './mikrotik/Index'
import Sms from './sms/Index'
import Email from './email/Index'

export default {
    components: {
        AppSmotreshka: Smotreshka,
        AppMikrotik: Mikrotik,
        AppSms: Sms,
        AppEmail: Email,
    },
    data () {
        return {

        }
    }
}
</script>

<style lang="scss">

</style>
