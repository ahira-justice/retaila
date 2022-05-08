package com.ahirajustice.retail.validators.user;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.dtos.user.UserCreateDto;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class UserCreateDtoValidator extends AbstractValidator<UserCreateDto> {

    @Override
    public void rules() {
        ruleFor(UserCreateDto::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("username is required").withFieldName("username");
        ruleFor(UserCreateDto::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserCreateDto::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
        ruleFor(UserCreateDto::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserCreateDto::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
