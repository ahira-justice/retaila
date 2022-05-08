package com.ahirajustice.retail.validators.role;

import br.com.fluentvalidator.AbstractValidator;
import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;

import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class RoleUpdateDtoValidator extends AbstractValidator<RoleUpdateDto> {

    @Override
    public void rules() {
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
