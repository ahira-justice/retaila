package com.ahirajustice.retail.validators.user;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThan;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import com.ahirajustice.retail.dtos.user.UserUpdateDto;

import br.com.fluentvalidator.AbstractValidator;

public class UserUpdateDtoValidator extends AbstractValidator<UserUpdateDto> {

    @Override
    public void rules() {
        ruleFor(UserUpdateDto::getId)
                .must(greaterThan(0l)).withMessage("id is required").withFieldName("id");
        ruleFor(UserUpdateDto::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserUpdateDto::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserUpdateDto::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
