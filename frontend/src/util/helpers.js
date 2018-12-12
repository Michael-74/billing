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

