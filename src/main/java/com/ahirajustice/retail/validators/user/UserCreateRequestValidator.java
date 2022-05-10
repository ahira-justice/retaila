package com.ahirajustice.retail.validators.user;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.requests.user.UserCreateRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class UserCreateRequestValidator extends AbstractValidator<UserCreateRequest> {

    @Override
    public void rules() {
        ruleFor(UserCreateRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("username is required").withFieldName("username");
        ruleFor(UserCreateRequest::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserCreateRequest::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
        ruleFor(UserCreateRequest::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserCreateRequest::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
