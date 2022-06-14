package com.ahirajustice.retaila.validators.auth;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.auth.LoginRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class LoginRequestValidator extends AbstractValidator<LoginRequest> {

    @Override
    public void rules() {
        ruleFor(LoginRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(LoginRequest::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
    }

}
