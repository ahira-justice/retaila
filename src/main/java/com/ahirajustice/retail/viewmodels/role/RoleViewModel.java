package com.ahirajustice.retail.viewmodels.role;

import com.ahirajustice.retail.viewmodels.BaseViewModel;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleViewModel extends BaseViewModel {

    private String name;

    private boolean isSystem;

    private Set<PermissionViewModel> permissions = new HashSet<PermissionViewModel>();

}
