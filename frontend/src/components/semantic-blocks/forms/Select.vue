<template>
    <div class="fields__select">
        <div v-show="data.label">
            <div class="fields__label">{{data.label}} <span class="fields__required" v-show='data.required'>*</span></div>
        </div>
        <div class="select__position">
            <div class="select__title" @click="showList">
                <span class="select__data-first">{{select}}</span>
                <font-awesome-icon class="select__arrow" :icon="getIcon"></font-awesome-icon>
            </div>
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" :class="{'select__option_active': item.val == select}" v-for="item in data.items" @click="selected(item.val)">{{item.val}}</div>
            </div>
        </div>
    </div>
</template>

<script>
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export default {
    props: ['data'],
    components: {
        FontAwesomeIcon
    },
    data () {
        return {
            requared: this.data.required,
            isShow: false,
            select: this.data.items[0].val
        }
    },
    methods: {
        selected (name) {
            this.select = name
            this.isShow = false
        },
        showList () {
            this.isShow = !this.isShow
        }
    },
    computed: {
        getIcon () {
            if (this.isShow)
                return 'chevron-up';
            else
                return 'chevron-down';
        }
    }
}
</script>

<style scoped>
</style>
