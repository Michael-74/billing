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
                <div class="select__option" :class="{'select__option_active': item.id === select}" v-for="item in data.items" @click="selected(item.val, item.id)">
                    {{item.val}}
                </div>
            </div>
        </div>
        <div class="fields__error" v-show='this.data.isError'>
            {{ data.errorText }}
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
            isShow: false,
            select: null,
            selectName: null
        }
    },
    methods: {
        getSelect () {
            return this.select
        },
        selected (name, id) {
            if(this.select === id) {
                this.selectName = null;
                this.select = null;
                this.data.val = null;
            } else {
                this.select = id;
                this.selectName = name;
                this.data.val = id;
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
            if(this.data.val != null && this.select === this.data.val){
                return this.selectName
            } else {
                if(this.data.val != null && this.data.val !== "") {
                    this.select = this.data.val;
                    var index = null;
                    this.data.items.forEach((item, i, array) => {
                        if(item.id === this.select) {
                            index = i
                        }
                    });
                    if(index != null) {
                        return this.selectName = this.data.items[index].val;
                    } else {
                        this.selectName = null;
                        this.select = null;
                        return 'не выбрано';
                    }
                } else {
                    this.selectName = null;
                    this.select = null;
                    return 'не выбрано';
                }
            }
        },
        isSelect () {
            if(this.selectName)
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
