<template>
    <div class="fields__select fields_relative">
        <div v-show="data.label">
            <div class="fields__label">{{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span></div>
        </div>
        <div class="select__position" v-click-outside="closeList">
            <div class="select__title" @click="showList" :class="{fields_red: this.data.isError, select__title_active: isSelect}">
                <span class="select__data-first">{{getSelectName}}</span>
                <font-awesome-icon class="select__arrow" :icon="getIcon" :class="{select__arrow_active: isSelect}"></font-awesome-icon>
            </div>
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" :class="{'select__option_active': item.val == select}" v-for="item in data.items" @click="selected(item.val, item.key)">
                    {{item.val}}
                </div>
            </div>
        </div>
        <div class="fields__error" v-show='this.data.isError'>
            Поле не заполнено
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
            isShow: false,
            select: ''
        }
    },
    methods: {
        selected (name, key) {
            if(this.select == name) {
                this.select = ''
                this.data.val = '';
            } else {
                this.select = name
                this.data.val = key;
            }
            this.isShow = false
        },
        showList () {
            this.isShow = !this.isShow
        },
        closeList () {
            this.isShow = false
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
