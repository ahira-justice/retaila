package com.ahirajustice.retail.exceptions;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;

import br.com.fluentvalidator.context.Error;
import lombok.Getter;

@Getter
public class ValidationException extends ApplicationDomainException {

    private final Dictionary<String, String> failures;

    private ValidationException() {
        super("One or more validation failures have occurred", "UnprocessableEntity", 422);
        this.failures = new Hashtable<>();
    }

    public ValidationException(String message) {
        super(message, "UnprocessableEntity", 422);
        this.failures = new Hashtable<>();
    }

    public ValidationException(Collection<Error> errors) {
        this();

        for (Error error : errors) {
            this.failures.put(error.getField(), error.getMessage());
        }
    }

    @Override
    public ErrorResponse toErrorResponse() {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        errorResponse.setCode(getCode());
        errorResponse.setMessage(getMessage());
        errorResponse.setErrors(getFailures());

        return errorResponse;
    }
}
