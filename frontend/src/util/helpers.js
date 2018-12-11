/**
 * Подготавливаем новый объект содержащий название поля и значение
 * @param obj
 * @return obj
 */
export function parseObj(obj) {
    var data = {};
    for(let item in obj) {
        data[obj[item].name] = obj[item].val;
    }

    return data;
}

