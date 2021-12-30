package com.ahirajustice.retail.validators.role;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThan;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;

import br.com.fluentvalidator.AbstractValidator;

public class RoleUpdateDtoValidator extends AbstractValidator<RoleUpdateDto> {

    @Override
    public void rules() {
        ruleFor(RoleUpdateDto::getId)
            .must(greaterThan(0l)).withMessage("id is required").withFieldName("id");
        ruleFor(RoleUpdateDto::getName)
            .must(not(stringEmptyOrNull())).withMessage("name is required").withFieldName("name")
            .must(this::nameIsUppercase).withMessage("name must be uppercase").withFieldName("name")
            .must(this::doesNotContainSpecialCharactersAndNumbers).withMessage("name must not contain special characters or numbers").withFieldName("name");
        ruleFor(RoleUpdateDto::getPermissionIds)
            .must(not(empty())).withMessage("At least one permission is required").withFieldName("permissionIds");
    }

    private boolean nameIsUppercase(String name) {
        return CommonHelper.isStringUpperCase(name);
    }

	private boolean doesNotContainSpecialCharactersAndNumbers(String name) {
        return !CommonHelper.containsSpecialCharactersAndNumbers(name);
	}

}
