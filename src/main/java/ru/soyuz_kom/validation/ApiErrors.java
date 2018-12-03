package ru.soyuz_kom.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

class ApiErrors {
    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    ApiErrors(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String path1, String path, String message) {
        FieldError error = new FieldError(path1, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
