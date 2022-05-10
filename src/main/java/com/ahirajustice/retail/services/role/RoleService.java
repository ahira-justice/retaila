package com.ahirajustice.retail.services.role;

import com.ahirajustice.retail.requests.role.RoleCreateRequest;
import com.ahirajustice.retail.requests.role.RoleUpdateRequest;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(RoleCreateRequest request);

    RoleViewModel updateRole(RoleUpdateRequest request, long id);

}
