package com.ahirajustice.retaila.viewmodels.role;

import com.ahirajustice.retaila.viewmodels.BaseViewModel;
import com.ahirajustice.retaila.viewmodels.permission.PermissionViewModel;
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
