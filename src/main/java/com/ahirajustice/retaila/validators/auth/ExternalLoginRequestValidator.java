package com.ahirajustice.retaila.validators.auth;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class ExternalLoginRequestValidator extends AbstractValidator<ExternalLoginRequest> {

    @Override
    public void rules() {
        ruleFor(ExternalLoginRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
    }

}
