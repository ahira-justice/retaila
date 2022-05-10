package com.ahirajustice.retail.validators.auth;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.requests.auth.ForgotPasswordRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class ForgotPasswordRequestValidator extends AbstractValidator<ForgotPasswordRequest> {

    @Override
    public void rules() {
        ruleFor(ForgotPasswordRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("username is required").withFieldName("username");
    }

}
