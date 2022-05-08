package com.ahirajustice.retail.exceptions;

import br.com.fluentvalidator.context.Error;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;
import lombok.Getter;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

@Getter
public class ValidationException extends ApplicationDomainException {

    private final Map<String, String> failures;

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
