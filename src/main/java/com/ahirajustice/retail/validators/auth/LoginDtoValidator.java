package com.ahirajustice.retail.validators.auth;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.dtos.auth.LoginDto;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class LoginDtoValidator extends AbstractValidator<LoginDto> {

    @Override
    public void rules() {
        ruleFor(LoginDto::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(LoginDto::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
    }

}
