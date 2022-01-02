package com.ahirajustice.retail.services.role;

import java.util.List;

import com.ahirajustice.retail.dtos.role.RoleCreateDto;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(RoleCreateDto roleDto);

    RoleViewModel updateRole(RoleUpdateDto roleDto);

}
