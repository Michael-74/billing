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

export function checkErrors(obj, errors) {
    for(let item in obj) {
        obj[item].isError = false;
    }
    if(errors) {
        console.log("errors", errors.indexOf('datetime'));

        for(let item in errors) {
            if(item === 'datetime'){
                obj.type_write_off_rent.isError = true;
                obj.type_write_off_rent.errorText = errors[item];
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

