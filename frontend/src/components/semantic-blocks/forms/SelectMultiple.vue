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
            {{ this.selecteds }} --
            {{ this.data.val }}
            <div class="select__block-select" v-show="isShow">
                <div class="select__option" v-for="item in data.items" @click="selected(item.id, item.val)" :class="{select__option_active: getIndex(item.id)}">
                    <font-awesome-icon class="select__icon" icon="check-square" v-if="getIndex(item.id)"></font-awesome-icon>
                    <font-awesome-icon class="select__icon" icon="square" v-else></font-awesome-icon>
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
            selectName: [],
            selecteds: []
        }
    },
    methods: {
        selected (id, name) {
            if(this.getIndex(id)) {
                var idx = this.selecteds.indexOf(id);
                this.selecteds.splice(idx, 1);
                this.selectName.splice(idx, 1);
                this.data.val.splice(idx, 1);
            }else {
                this.selecteds.push(id);
                this.selectName.push(name);
                this.data.val.push(id);
            }
        },
        showList () {
            this.isShow = !this.isShow
        },
        closeList () {
            this.isShow = false
        },
        getIndex (id) {
            var isFlag = false;
            this.selecteds.forEach((itemId, i) => {
                if(itemId === id){
                    isFlag = true;
                }
            });

            return isFlag;
        },
        getArrSelected () {
            var index = [];
            this.data.items.forEach((item, i1, array1) => {
                this.data.val.forEach((currentItem, i2, array2) => {
                    if(item.id === currentItem) {
                        index.push({'name': item.val, 'idx': i1});
                    }
                });
            });
            return index;
        },
        /**
         * Доастаем названия для вывода
         */
        getNameSelected () {
            if(this.selecteds.length === 1) {
                return this.selectName[0]
            } else {
                return this.selectName[0] + '...';
            }
        }
    },
    computed: {
        /**
         * Выводим названия и определяем что были переданы данные
         * @returns {*}
         */
        getSelectsName () {
            if((this.selecteds.length !== 0) && (this.data.val.length !== 0) && (this.isDifference.length === 0)){
                return this.getNameSelected();
            } else {
                if(this.data.val.length !== 0) {
                    var index = [];
                    var names = [];
                    this.data.val.sort();// Сортируем чтобы значения id были одинаково расположены в selecteds и data.val
                    this.data.items.forEach((item, i1, array1) => {
                        this.data.val.forEach((currentItem, i2, array2) => {
                            if(item.id === currentItem) {
                                index.push(item.id);
                                names.push(item.val);
                            }
                        });
                    });

                    if(index.length !== 0) {
                        this.selectName = [];
                        this.selecteds = [];
                        this.selecteds = this.selecteds.concat(index);
                        this.selectName = this.selectName.concat(names);
                        return this.getNameSelected();
                    } else {
                        this.selectName = [];
                        this.selecteds = [];
                        return 'не выбрано';
                    }

                } else {
                    this.selectName = [];
                    this.selecteds = [];
                    return 'не выбрано';
                }
            }
        },
        isDifference () {
            // Не должно быть совпадений, выдать true
            var difference = [];
            if(this.selecteds.length > this.data.val.length) {
                difference = this.selecteds.filter(n => this.data.val.indexOf(n) === -1);
            } else {
                difference = this.data.val.filter(n => this.selecteds.indexOf(n) === -1);
            }
            return difference
        },
        isSelects () {
            if(this.selecteds.length !== 0){
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
