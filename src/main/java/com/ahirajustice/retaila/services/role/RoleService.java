package com.ahirajustice.retaila.services.role;

import com.ahirajustice.retaila.requests.role.RoleCreateRequest;
import com.ahirajustice.retaila.requests.role.RoleUpdateRequest;
import com.ahirajustice.retaila.viewmodels.role.RoleViewModel;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(RoleCreateRequest request);

    RoleViewModel updateRole(RoleUpdateRequest request, long id);

}
