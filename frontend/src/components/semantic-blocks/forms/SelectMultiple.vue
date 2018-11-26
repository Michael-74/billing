<template>
    <div class="fields__select">
        <div v-show="data.label">
            <div class="fields__label">{{data.label}} <span class="fields__required" v-show='data.required'>*</span></div>
        </div>
        <div class="select__position">
            <div class="select__title" @click="showList" :class="{select__title_active: isSelects}">
                <span class="select__data-first">{{getSelectsName}}</span>
                <font-awesome-icon class="select__arrow" :icon="getIcon" :class="{select__arrow_active: isSelects}"></font-awesome-icon>
            </div>
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" v-for="item in data.items" @click="selected(item.key, item.val)" :class="{select__option_active: getIndex(item.val)}">
                    <font-awesome-icon class="select__icon" icon="check-square" v-if="selecteds.indexOf(item.val) != '-1'"></font-awesome-icon>
                    <font-awesome-icon class="select__icon" icon="square" v-else></font-awesome-icon>
                    {{ item.val }}
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
            select: '',
            selecteds: []
        }
    },
    methods: {
        selected (key, name) {
            if(this.selecteds.indexOf(name) != '-1'){
                var idx = this.selecteds.indexOf(name)
                this.selecteds.splice(idx, 1)
            } else {
                this.selecteds.push(name)
            }
        },
        showList () {
            this.isShow = !this.isShow
        },
        getIndex (name) {
            if(this.selecteds.indexOf(name) != '-1') {
                return true;
            } else {
                return false;
            }
        }
    },
    mounted () {
        /*
        document.body.onclick = function (e) {
            e = e || event;
            console.log(e.target)
        }
        */
    },
    computed: {
        getSelectsName () {
            if(this.selecteds.length != 0){
                if(this.selecteds.length == 1) {
                    return this.selecteds[0]
                } else {
                    return this.selecteds[0] + '...';
                }
            } else {
                return 'Не выбрано';
            }
        },
        isSelects () {
            if(this.selecteds.length != 0){
                return true;
            } else {
                return false;
            }
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
