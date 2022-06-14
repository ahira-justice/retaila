package com.ahirajustice.retaila.validators.user;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.user.UserUpdateRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class UserUpdateRequestValidator extends AbstractValidator<UserUpdateRequest> {

    @Override
    public void rules() {
        ruleFor(UserUpdateRequest::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserUpdateRequest::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserUpdateRequest::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
