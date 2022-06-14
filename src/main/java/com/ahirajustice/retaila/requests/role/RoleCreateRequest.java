package com.ahirajustice.retaila.requests.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleCreateRequest {

    private String name;

    private boolean isSystem;

    private List<Long> permissionIds;

}
