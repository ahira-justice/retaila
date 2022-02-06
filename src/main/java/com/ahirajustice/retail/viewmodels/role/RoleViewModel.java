package com.ahirajustice.retail.viewmodels.role;

import java.util.HashSet;
import java.util.Set;

import com.ahirajustice.retail.viewmodels.BaseViewModel;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;

public class RoleViewModel extends BaseViewModel {

    private String name;

    private Set<PermissionViewModel> permissions = new HashSet<PermissionViewModel>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionViewModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionViewModel> permissions) {
        this.permissions = permissions;
    }

}
