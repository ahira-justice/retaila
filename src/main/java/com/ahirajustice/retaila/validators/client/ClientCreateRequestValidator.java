package com.ahirajustice.retaila.validators.client;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retaila.requests.client.ClientCreateRequest;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class ClientCreateRequestValidator extends AbstractValidator<ClientCreateRequest> {

    @Override
    public void rules() {
        ruleFor(ClientCreateRequest::getName)
                .must(not(stringEmptyOrNull())).withMessage("name is required").withFieldName("name");
        ruleFor(ClientCreateRequest::getAdminEmail)
                .must(not(stringEmptyOrNull())).withMessage("adminEmail is required").withFieldName("adminEmail");
    }

}
