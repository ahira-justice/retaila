package com.ahirajustice.retaila.validators;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import com.ahirajustice.retaila.exceptions.ValidationException;

import java.util.Collection;

public class ValidatorUtils<T> {

    public void validate(AbstractValidator<T> validator, T dto) {
        ValidationResult result = validator.validate(dto);

        if (!result.isValid()) {
            Collection<Error> errors = result.getErrors();
            ValidationException ex = new ValidationException(errors);
            throw ex;
        }
    }

}
