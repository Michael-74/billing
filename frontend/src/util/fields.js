export function input(label, text, name, isRequired, isError, errorText, val) {
    return {
        label: label,
        text: text,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val
    }
}

export function inputDifference(label, textFrom, textTo, name, isRequired, isError, errorText, val) {
    return {
        label: label,
        textFrom: textFrom,
        textTo: textTo,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val
    }
}

export function textarea(label, text, name, isRequired, isError, errorText, val) {
    return {
        label: label,
        text: text,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val
    }
}

export function select(label, text, name, isRequired, isError, errorText, val, items) {
    return {
        label: label,
        text: text,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val,
        items: items
    }
}

export function selectMultiple(label, text, name, isRequired, isError, errorText, val, items) {
    return {
        label: label,
        text: text,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val,
        items: items
    }
}

export function checkbox(label, textTrue, textFalse, name, isRequired, isError, errorText, val, items) {
    return {
        label: label,
        textTrue: textTrue,
        textFalse: textFalse,
        name: name,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val
    }
}

export function datepicker(label, text, name, isDifference, isRequired, isError, errorText, val) {
    return {
        label: label,
        text: text,
        name: name,
        isDifference: isDifference,
        isRequired: isRequired,
        isError: isError,
        errorText: errorText,
        val: val
    }
}

