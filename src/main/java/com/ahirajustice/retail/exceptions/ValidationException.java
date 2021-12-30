package com.ahirajustice.retail.exceptions;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;

import br.com.fluentvalidator.context.Error;

public class ValidationException extends ApplicationDomainException {

    private Dictionary<String, String> failures;

    public ValidationException() {
        super("One or more validation failures have occurred", "UnprocessableEntity", 422);
        this.failures = new Hashtable<String, String>();
    }

    public ValidationException(Collection<Error> errors) {
        this();

        for (Error error : errors) {
            this.failures.put(error.getField(), error.getMessage());
        }
    }

    public Dictionary<String, String> getFailures() {
        return failures;
    }

    public void setFailures(Dictionary<String, String> failures) {
        this.failures = failures;
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
