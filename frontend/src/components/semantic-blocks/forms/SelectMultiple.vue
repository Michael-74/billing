<template>
    <div class="fields__select fields_relative">
        <div v-show="data.label">
            <div class="fields__label">{{data.label}} <span class="fields__required" v-show='data.isRequired'>*</span></div>
        </div>
        <div class="select__position" v-click-outside="closeList">
            <div class="select__title" @click="showList" :class="{select__title_active: isSelects}">
                <span class="select__data-first">{{getSelectsName}}</span>
                <font-awesome-icon class="select__arrow" :icon="getIcon" :class="{fields_red: this.data.isError, select__arrow_active: isSelects}"></font-awesome-icon>
            </div>
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" v-for="item in data.items" @click="selected(item.key, item.val)" :class="{select__option_active: getIndex(item.val)}">
                    <font-awesome-icon class="select__icon" icon="check-square" v-if="selecteds.indexOf(item.val) != '-1'"></font-awesome-icon>
                    <font-awesome-icon class="select__icon" icon="square" v-else></font-awesome-icon>
                    {{ item.val }}
                </div>
            </div>
        </div>
        <div class="fields__error" v-show='this.data.isError'>
            {{ data.errorText }}
        </div>
        {{selecteds}}
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
            selectName: null,
            selecteds: []
        }
    },
    methods: {
        selected (key, name) {
            if(this.selecteds.indexOf(name) != '-1'){
                var idx = this.selecteds.indexOf(name)
                this.selecteds.splice(idx, 1)
                this.data.val.splice(idx, 1)
            } else {
                this.data.val.push(key);
                this.selecteds.push(name)
            }
        },
        showList () {
            this.isShow = !this.isShow
        },
        closeList () {
            this.isShow = false
        },
        getIndex (name) {
            if(this.selecteds.indexOf(name) != '-1') {
                return true;
            } else {
                return false;
            }
        }
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
                if(this.data.val.length != 0) {
                    this.selecteds = this.selecteds.concat(this.data.val);
                    var index = [];
                    this.data.items.forEach((item, i1, array1) => {
                        this.data.val.forEach((currentItem, i2, array2) => {
                            if(item.key == currentItem) {
                                index.push(i1);
                            }
                        });
                    });
                    if(index.length != 0) {
                        console.log("-----------------------multi", index)
                        //return this.selectName = this.data.items[index].val;
                    } else {
                        return 'не выбрано1';
                    }
                    /*
                    if(this.selecteds.length == 1) {
                        return this.selecteds[0]
                    } else {
                        return this.selecteds[0] + '...';
                    }
                    */
                } else {
                    return 'не выбрано';
                }
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
