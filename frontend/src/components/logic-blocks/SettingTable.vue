<template>
    <div class="cog">
        <button class="cog__button" @click="changeSetting">
            <font-awesome-icon class="cog__icon" icon="cog"></font-awesome-icon>
        </button>
        <div class="cog__fields" v-show="isSetting">
            <table>
                <thead>
                    <tr>
                        <th>-</th>
                        <th class="cog__fields-block"
                             v-for="column in columns"
                        >{{ column.name }}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            Порядок
                        </td>
                        <td class="cog__fields-block"
                            v-for="column in columns"
                        >
                            <input type="text" class="cog__input-sort" v-model="column.sort">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Вкл/Выкл
                        </td>
                        <td class="cog__fields-block"
                            v-for="column in columns"
                        ><input type="checkbox" class="cog__input-chexbox" v-model="column.isShow">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['columns'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
            isSetting: false
        }
    },
    methods: {
        changeSetting() {
            this.isSetting = !this.isSetting;
        }
    },
    computed: {

    }
}
</script>

<style lang="scss" scoped>

</style>
