package com.ahirajustice.retail.validators.usertoken;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.requests.usertoken.VerifyUserTokenRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class VerifyUserTokenRequestValidator extends AbstractValidator<VerifyUserTokenRequest> {

    @Override
    public void rules() {
        ruleFor(VerifyUserTokenRequest::getUsername)
                .must(not(stringEmptyOrNull())).withMessage("username is required").withFieldName("username");
        ruleFor(VerifyUserTokenRequest::getTokenType)
                .must(not(stringEmptyOrNull())).withMessage("tokenType is required").withFieldName("tokenType");
        ruleFor(VerifyUserTokenRequest::getToken)
                .must(not(stringEmptyOrNull())).withMessage("token is required").withFieldName("token");
    }

}
