import Vue from "vue";
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

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

export function showTasks(items) {
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
                getDatetimeFormat (date, format) {
                    var date = new Date(date);
                    date.setHours(date.getHours() + 5);// 5 - раззница часового пояса

                    var dateString;
                    switch (format) {
                        case 'datetime':
                            dateString =
                                ("0" + date.getDate()).slice(-2) + "." +
                                ("0" + (date.getMonth() + 1)).slice(-2) + "." +
                                date.getFullYear() + " " +
                                ("0" + date.getHours()).slice(-2) + ":" +
                                ("0" + date.getMinutes()).slice(-2)
                            break;
                        case 'time':
                            dateString =
                                ("0" + date.getHours()).slice(-2) + ":" +
                                ("0" + date.getMinutes()).slice(-2)
                            break;

                    }
                    return dateString;
                },
                getTypeWriteOffName(type) {
                    var name;
                    switch (type) {
                        case 'onetime':
                            name = "Разово";
                            break;
                        case 'daily':
                            name = "Ежедневно";
                            break;
                        case 'monthly':
                            name = "Ежемесячно";
                            break;
                        default:
                            name = "Неизветный тип";
                            break;
                    }

                    return name;
                },
                getTypeWriteOffDatetime(task) {
                    var name;
                    switch (task.typeWriteOff) {
                        case 'onetime':
                            name = this.getDatetimeFormat(task.datetime, "datetime");
                            break;
                        case 'daily':
                            name = this.getDatetimeFormat(task.datetime, "time");
                            break;
                        case 'monthly':
                            name = task.dayInMonth + " числа в " + this.getDatetimeFormat(task.datetime, "time");
                            break;
                        default:
                            name = "Неизветный тип";
                            break;
                    }

                    return name;
                },
                deleteTask(id) {
                    this.$store.dispatch("deleteTaskAsync", {id: id});
                },
                selectTask(task) {
                    this.$emit('close')
                    const settings = JSON.parse(task.settings);

                    for(let item in items) {
                        let name = items[item].name;
                        //items[item].val = settings[name];
                    }
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
                                    <td class="items__td">
                                        <font-awesome-icon class="items__icon" icon="times-circle" @click="deleteTask(task.id)"></font-awesome-icon>
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

