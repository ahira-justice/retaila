package com.ahirajustice.retail.dtos.role;

import java.util.List;

public class RoleCreateDto {

    private String name;
    private List<Long> permissionIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

}
