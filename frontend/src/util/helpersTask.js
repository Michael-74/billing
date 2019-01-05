import Vue from "vue";
import FontAwesomeIcon from '@fortawesome/vue-fontawesome'

export function getActionTime(task) {
    let name = "";
    if(task.dayStart == null || task.monthStart == null) {
        name += "С начала ";
    } else {
        name += "C " + task.dayStart + " дня " + task.monthStart + " месяца ";
    }
    if(task.dayEnd == null || task.monthEnd == null) {
        name += "По оставшееся время";
    } else {
        name += "По " + task.dayEnd + " день " + task.monthEnd + " месяца ";
    }

    return name;
}

export function getDatetimeFormat(date, format) {
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
}

export function getTypeWriteOffName(type) {
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
}

export function getTypeWriteOffDatetime(task) {
    var name;
    switch (task.typeWriteOff) {
        case 'onetime':
            name = getDatetimeFormat(task.datetime, "datetime");
            break;
        case 'daily':
            name = getDatetimeFormat(task.datetime, "time");
            break;
        case 'monthly':
            name = task.dayInMonth + " числа в " + getDatetimeFormat(task.datetime, "time");
            break;
        default:
            name = "Неизветный тип";
            break;
    }

    return name;
}

