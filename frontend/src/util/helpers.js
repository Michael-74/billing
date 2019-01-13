import Vue from "vue";
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'
import {getActionTime, getDatetimeFormat, getTypeWriteOffDatetime, getTypeWriteOffName} from './helpersTask'

/**
 * Подготавливаем новый массив содержащий название поля и значение
 * @param obj - содержит массив объектов
 * @return obj
 */
export function parseObj(obj) {
    var data = {};
    for(let item in obj) {
        data[obj[item].name] = obj[item].val;
    }

    return data;
}

export function checkErrors(obj, errors, options = {}) {
    for(let item in obj) {
        obj[item].isError = false;
    }
    if(errors) {
        for(let item in errors) {

            // Проверяем, что для сложных объектов есть совпадения
            if(Object.keys(options).length !== 0 && Object.keys(options).indexOf(item) === 0){
                obj[options[item]].isError = true;
                obj[options[item]].errorText = errors[item];
            } else {
                obj[item].isError = true;
                obj[item].errorText = errors[item];
            }
        }
    }
}

/**
 * Очищаем поля у объекта
 * @param objFields
 * @param checkedFields
 * @returns {*}
 */
export function clearFields(objFields, checkedFields) {
    for(var item in objFields) {
        for(var name in checkedFields) {
            if(checkedFields[name] === item) {
                if (objFields[item].val instanceof Array) {
                    objFields[item].val = [];
                } else {
                    objFields[item].val = null;
                }
            }
        }
    }
}

/**
 *
 * @param obj
 * @returns {*}
 */
export function selectIds(obj) {

    return obj.map(function(item, i, arr){
        return item.id;
    });
}

/**
 * Разбираем выбранный объект и конкретно для услуг парсим данные
 * Достаем только id
 * @param obj
 * @param item
 * @returns {*}
 */
export function parseServicesForId(obj, item) {

    switch(item){
        case "internet":
            return obj[item] !== null ? obj[item].id : null;
        case "tvs":
        case "rents":
            return selectIds(obj[item]);
        default:
            return obj[item];
    }
}

export function showPresets(items) {
    Vue.prototype.$modal.show({
            components:{
                FontAwesomeIcon
            },
            computed: {
                getPresets () {
                    return this.$store.getters.getPresets;
                }
            },
            methods: {
                deletePreset(id) {
                    this.$store.dispatch("deletePresetAsync", {id: id});
                },
                selectPreset(preset) {
                    this.$emit('close')
                    const settings = JSON.parse(preset.settings);

                    for(let item in items) {
                        let name = items[item].name;
                        items[item].val = settings[name];
                    }
                }
            },
            template: `
                    <div class="modal__block">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon" icon="times-circle"></font-awesome-icon>
                            Закрыть
                        </div>
                        <h3 class="modal__h3">Список пресетов</h3>
                        <table class="modal__package-table items__table">
                            <thead class="items__thead">
                                <tr>
                                    <th class="modal__th items__th">N</th>
                                    <th class="modal__th items__th">Название</th>
                                    <th class="modal__th items__th">Действия</th>
                                </tr>
                            </thead>
                            <tbody class="items__tbody">
                                <tr class="modal__tr" v-for="(preset, index) in getPresets">
                                    <td class="items__td">{{preset.id}}</td>
                                    <td class="items__td" @click="selectPreset(preset)">{{preset.name}}</td>
                                    <td class="items__td">
                                        <font-awesome-icon class="items__icon" icon="times-circle" @click="deletePreset(preset.id)"></font-awesome-icon>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                  `
        },{
        },{
            height: '400px',
            clickToClose: false
        }
    );
}

export function showTasks() {
    Vue.prototype.$modal.show({
            components:{
                FontAwesomeIcon
            },
            computed: {
                getTasks () {
                    return this.$store.getters.getTasks;
                }
            },
            methods: {
                getActionTime(task) {
                    return getActionTime(task);
                },
                getDatetimeFormat (date, format) {
                    return getDatetimeFormat (date, format);
                },
                getTypeWriteOffName (type) {
                    return getTypeWriteOffName(type);
                },
                getTypeWriteOffDatetime(task) {
                    return getTypeWriteOffDatetime(task);
                },
                selectEditTask(task) {
                    this.$store.commit('setEditTask', task);
                    this.$emit('close');
                },
                deleteTask(id) {
                    this.$store.dispatch("deleteTaskAsync", {id: id});
                    this.$store.commit("deleteSelectedTask", {id: id});
                },
                selectTask(taskId) {
                    this.$store.commit('pushSelectedTasks', taskId);
                }
            },
            template: `
                    <div class="modal__block">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon" icon="times-circle"></font-awesome-icon>
                            Закрыть
                        </div>
                        <h3 class="modal__h3">Список задач</h3>
                        <table class="modal__package-table items__table">
                            <thead class="items__thead">
                                <tr>
                                    <th class="modal__th items__th">N</th>
                                    <th class="modal__th items__th">Название</th>
                                    <th class="modal__th items__th">Сумма списания</th>
                                    <th class="modal__th items__th">Тип списания</th>
                                    <th class="modal__th items__th">Время списания</th>
                                    <th class="modal__th items__th">Время действия</th>
                                    <th class="modal__th items__th">Списывать аб/плату при нулевом балансе</th>
                                    <th class="modal__th items__th items__th_width110">Рассрочка</th>
                                    <th class="modal__th items__th">Сумма в рассрочку</th>
                                    <th class="modal__th items__th">Действия</th>
                                </tr>
                            </thead>
                            <tbody class="items__tbody">
                                <tr class="modal__tr" v-for="(task, index) in getTasks">
                                    <td class="items__td">{{task.id}}</td>
                                    <td class="items__td" @click="selectTask(task)">{{task.name}}</td>
                                    <td class="items__td" @click="selectTask(task)">{{task.price}}</td>
                                    <td class="items__td" @click="selectTask(task)">
                                        {{getTypeWriteOffName(task.typeWriteOff)}}
                                    </td>
                                    <td class="items__td" @click="selectTask(task)">
                                        {{getTypeWriteOffDatetime(task)}}
                                    </td>
                                    <td class="items__td" @click="selectTask(task)">
                                        {{getActionTime(task)}}
                                    </td>
                                    <td class="items__td" @click="selectTask(task)">
                                        <span v-show="task.isWriteOffRent">
                                            <span class="items__signal"></span>
                                            <span class="items__status-text">Включен</span>
                                        </span>
                                        <span v-show="!task.isWriteOffRent">
                                            <span class="items__signal items__signal_off"></span>
                                            <span class="items__status-text">Выключен</span>
                                        </span>                                        
                                    </td>                                    
                                    <td class="items__td" @click="selectTask(task)">
                                        <span v-show="task.isInstallments">
                                            <span class="items__signal"></span>
                                            <span class="items__status-text">Включена</span>
                                        </span>
                                        <span v-show="!task.isInstallments">
                                            <span class="items__signal items__signal_off"></span>
                                            <span class="items__status-text">Выключена</span>
                                        </span>
                                    </td>                                   
                                    <td class="items__td" @click="selectTask(task)">
                                        {{task.priceInstallments}}
                                    </td>
                                    <td class="items__td">
                                        <font-awesome-icon class="items__icon" icon="cog" @click="selectEditTask(task)"></font-awesome-icon>
                                        <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteTask(task.id)"></font-awesome-icon>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                  `
        },{
        },{
            height: '600px',
            width: '1000px',
            clickToClose: false
        }
    );
}

export function showSelectedTasks() {
    Vue.prototype.$modal.show({
            components:{
                FontAwesomeIcon
            },
            computed: {
                getSelectedTasks () {
                    return this.$store.getters.getSelectedTasks;
                }
            },
            methods: {
                getActionTime(task) {
                    return getActionTime(task);
                },
                getDatetimeFormat (date, format) {
                    return getDatetimeFormat (date, format);
                },
                getTypeWriteOffName (type) {
                    return getTypeWriteOffName(type);
                },
                getTypeWriteOffDatetime(task) {
                    return getTypeWriteOffDatetime(task);
                },
                deleteSelectedTask(id) {
                    console.log('deleteSelectedTask', id);
                    this.$store.commit('deleteSelectedTask', {id: id});
                }
            },
            template: `
                    <div class="modal__block">
                        <div class="modal__close" @click="$emit('close')">
                            <font-awesome-icon class="modal__icon" icon="times-circle"></font-awesome-icon>
                            Закрыть
                        </div>
                        <h3 class="modal__h3">Список задач</h3>
                        <table class="modal__package-table items__table">
                            <thead class="items__thead">
                                <tr>
                                    <th class="modal__th items__th">N</th>
                                    <th class="modal__th items__th">Название</th>
                                    <th class="modal__th items__th">Сумма списания</th>
                                    <th class="modal__th items__th">Тип списания</th>
                                    <th class="modal__th items__th">Время списания</th>
                                    <th class="modal__th items__th">Время действия</th>
                                    <th class="modal__th items__th">Списывать аб/плату при нулевом балансе</th>
                                    <th class="modal__th items__th items__th_width110">Рассрочка</th>
                                    <th class="modal__th items__th">Сумма в рассрочку</th>
                                    <th class="modal__th items__th">Действия</th>
                                </tr>
                            </thead>
                            <tbody class="items__tbody">
                                <tr class="modal__tr" v-for="(task, index) in getSelectedTasks">
                                    <td class="items__td">{{task.id}}</td>
                                    <td class="items__td">{{task.name}}</td>
                                    <td class="items__td">{{task.price}}</td>
                                    <td class="items__td">
                                        {{getTypeWriteOffName(task.typeWriteOff)}}
                                    </td>
                                    <td class="items__td">
                                        {{getTypeWriteOffDatetime(task)}}
                                    </td>
                                    <td class="items__td">
                                        {{getActionTime(task)}}
                                    </td>
                                    <td class="items__td">
                                        <span v-show="task.isWriteOffRent">
                                            <span class="items__signal"></span>
                                            <span class="items__status-text">Включен</span>
                                        </span>
                                        <span v-show="!task.isWriteOffRent">
                                            <span class="items__signal items__signal_off"></span>
                                            <span class="items__status-text">Выключен</span>
                                        </span>                                        
                                    </td>                                    
                                    <td class="items__td">
                                        <span v-show="task.isInstallments">
                                            <span class="items__signal"></span>
                                            <span class="items__status-text">Включена</span>
                                        </span>
                                        <span v-show="!task.isInstallments">
                                            <span class="items__signal items__signal_off"></span>
                                            <span class="items__status-text">Выключена</span>
                                        </span>
                                    </td>                                   
                                    <td class="items__td">
                                        {{task.priceInstallments}}
                                    </td>
                                    <td class="items__td">
                                        <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteSelectedTask(task.id)"></font-awesome-icon>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                  `
        },{
        },{
            height: '400px',
            width: '1000px',
            clickToClose: false
        }
    );
}

