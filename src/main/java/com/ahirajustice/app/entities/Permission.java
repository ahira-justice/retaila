package com.ahirajustice.app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "permissions")
public class Permission extends BaseEntity {

    private String name;
    private boolean isSystem;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<Role>();

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
