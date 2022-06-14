package com.ahirajustice.retaila.validators.auth;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.auth.ResetPasswordRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class ResetPasswordRequestValidator extends AbstractValidator<ResetPasswordRequest> {

    @Override
    public void rules() {
        ruleFor(ResetPasswordRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("username is required").withFieldName("username");
        ruleFor(ResetPasswordRequest::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
        ruleFor(ResetPasswordRequest::getToken)
                .must(not(stringEmptyOrNull())).withMessage("token is required").withFieldName("token");
    }

}
