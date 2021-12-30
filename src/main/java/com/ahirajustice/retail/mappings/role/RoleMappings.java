package com.ahirajustice.retail.mappings.role;

import com.ahirajustice.retail.dtos.role.RoleCreateDto;
import com.ahirajustice.retail.entities.Role;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMappings {

    RoleViewModel roleToRoleViewModel(Role role);

    Role roleCreateDtoToRole(RoleCreateDto roleCreateDto);

}
