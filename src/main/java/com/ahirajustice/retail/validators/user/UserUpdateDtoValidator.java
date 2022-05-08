package com.ahirajustice.retail.validators.user;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class UserUpdateDtoValidator extends AbstractValidator<UserUpdateDto> {

    @Override
    public void rules() {
        ruleFor(UserUpdateDto::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserUpdateDto::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserUpdateDto::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
