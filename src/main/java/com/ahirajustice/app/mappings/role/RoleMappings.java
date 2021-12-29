package com.ahirajustice.app.mappings.role;

import com.ahirajustice.app.dtos.role.RoleCreateDto;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.viewmodels.role.RoleViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMappings {

    RoleViewModel roleToRoleViewModel(Role role);

    Role roleCreateDtoToRole(RoleCreateDto roleCreateDto);

}
