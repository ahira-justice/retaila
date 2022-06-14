package com.ahirajustice.retaila.validators.client;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.client.ClientUpdateRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class ClientUpdateRequestValidator extends AbstractValidator<ClientUpdateRequest> {

    @Override
    public void rules() {
        ruleFor(ClientUpdateRequest::getName)
                .must(not(stringEmptyOrNull())).withMessage("name is required").withFieldName("name");
        ruleFor(ClientUpdateRequest::getAdminEmail)
                .must(not(stringEmptyOrNull())).withMessage("adminEmail is required").withFieldName("adminEmail");
    }

}
