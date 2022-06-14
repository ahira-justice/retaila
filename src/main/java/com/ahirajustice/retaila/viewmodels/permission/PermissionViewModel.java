package com.ahirajustice.retaila.viewmodels.permission;

import com.ahirajustice.retaila.viewmodels.BaseViewModel;

public class PermissionViewModel extends BaseViewModel {

    private String name;

    private boolean isSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

}
