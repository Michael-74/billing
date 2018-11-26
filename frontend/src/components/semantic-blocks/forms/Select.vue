<template>
    <div class="fields__select">
        <div v-show="data.label">
            <div class="fields__label">{{data.label}} <span class="fields__required" v-show='data.required'>*</span></div>
        </div>
        <div class="select__position">
            <div class="select__title" @click="showList" :class="{select__title_active: isSelect}">
                <span class="select__data-first">{{getSelectName}}</span>
                <font-awesome-icon class="select__arrow" :icon="getIcon" :class="{select__arrow_active: isSelect}"></font-awesome-icon>
            </div>
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" :class="{'select__option_active': item.val == select}" v-for="item in data.items" @click="selected(item.val)">
                    {{item.val}}
                </div>
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
            isMultiple: this.data.multiple,
            requared: this.data.required,
            isShow: false,
            select: ''
        }
    },
    methods: {
        selected (name) {
            if(this.select == name) {
                this.select = ''
            } else {
                this.select = name
            }
            this.isShow = false
        },
        showList () {
            this.isShow = !this.isShow
        }
    },
    computed: {
        getSelectName () {
            if(this.select.length != 0){
                return this.select
            } else {
                return 'Не выбрано';
            }
        },
        isSelect () {
            if(this.select)
                return true;
            else
                return false;
        },
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
