package com.ahirajustice.retaila.mappings.role;

import com.ahirajustice.retaila.requests.role.RoleCreateRequest;
import com.ahirajustice.retaila.entities.Role;
import com.ahirajustice.retaila.viewmodels.role.RoleViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMappings {

    RoleViewModel roleToRoleViewModel(Role role);

    Role roleCreateRequestToRole(RoleCreateRequest roleCreateRequest);

}
