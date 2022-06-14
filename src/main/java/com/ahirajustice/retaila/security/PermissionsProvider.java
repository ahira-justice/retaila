package com.ahirajustice.retaila.security;

import com.ahirajustice.retaila.entities.Permission;
import com.ahirajustice.retaila.entities.Role;
import com.ahirajustice.retaila.enums.Roles;

import java.util.HashSet;
import java.util.Set;

public class PermissionsProvider {

    // User permissions
    public static Permission CAN_VIEW_USER = new Permission("CAN_VIEW_USER");
    public static Permission CAN_SEARCH_USERS = new Permission("CAN_SEARCH_USERS", true);
    public static Permission CAN_CREATE_USER = new Permission("CAN_CREATE_USER");
    public static Permission CAN_CREATE_ADMIN_USER = new Permission("CAN_CREATE_ADMIN_USER", true);
    public static Permission CAN_CREATE_SUPER_ADMIN_USER = new Permission("CAN_CREATE_SUPER_ADMIN_USER", true);
    public static Permission CAN_UPDATE_USER = new Permission("CAN_UPDATE_USER");

    // Client permissions
    public static Permission CAN_VIEW_CLIENT = new Permission("CAN_VIEW_CLIENT", true);
    public static Permission CAN_SEARCH_CLIENTS = new Permission("CAN_SEARCH_CLIENTS", true);
    public static Permission CAN_CREATE_CLIENT = new Permission("CAN_CREATE_CLIENT", true);
    public static Permission CAN_UPDATE_CLIENT = new Permission("CAN_UPDATE_CLIENT", true);

    // UserToken permissions
    public static Permission CAN_REQUEST_USER_TOKEN = new Permission("CAN_REQUEST_USER_TOKEN");
    public static Permission CAN_VERIFY_USER_TOKEN = new Permission("CAN_VERIFY_USER_TOKEN");

    // Permission permissions
    public static Permission CAN_VIEW_PERMISSION = new Permission("CAN_VIEW_PERMISSION", true);
    public static Permission CAN_VIEW_ALL_PERMISSIONS = new Permission("CAN_VIEW_ALL_PERMISSIONS", true);

    // Role permissions
    public static Permission CAN_VIEW_ROLE = new Permission("CAN_VIEW_ROLE", true);
    public static Permission CAN_VIEW_ALL_ROLES = new Permission("CAN_VIEW_ALL_ROLES", true);
    public static Permission CAN_CREATE_ROLE = new Permission("CAN_CREATE_ROLE", true);
    public static Permission CAN_UPDATE_ROLE = new Permission("CAN_UPDATE_ROLE", true);

    public static Set<Permission> getAllPermissions() {
        Set<Permission> permissions = new HashSet<>();

        // User permissions
        permissions.add(CAN_VIEW_USER);
        permissions.add(CAN_SEARCH_USERS);
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_CREATE_ADMIN_USER);
        permissions.add(CAN_CREATE_SUPER_ADMIN_USER);
        permissions.add(CAN_UPDATE_USER);

        // Client permissions
        permissions.add(CAN_VIEW_CLIENT);
        permissions.add(CAN_SEARCH_CLIENTS);
        permissions.add(CAN_CREATE_CLIENT);

        // UserToken permissions
        permissions.add(CAN_REQUEST_USER_TOKEN);
        permissions.add(CAN_VERIFY_USER_TOKEN);

        // Permission permissions
        permissions.add(CAN_VIEW_PERMISSION);
        permissions.add(CAN_VIEW_ALL_PERMISSIONS);

        // Role permissions
        permissions.add(CAN_VIEW_ROLE);
        permissions.add(CAN_VIEW_ALL_ROLES);
        permissions.add(CAN_CREATE_ROLE);
        permissions.add(CAN_UPDATE_ROLE);

        return permissions;
    }

    public static Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<>();

        Role user = new Role(Roles.USER.name());
        user.setPermissions(getUserPermissions());
        roles.add(user);

        Role admin = new Role(Roles.ADMIN.name());
        admin.setPermissions(getAdminPermissions());
        roles.add(admin);

        Role superAdmin = new Role(Roles.SUPERADMIN.name(), true);
        superAdmin.setPermissions(getSuperAdminPermissions());
        roles.add(superAdmin);

        return roles;
    }

    private static Set<Permission> getUserPermissions() {
        Set<Permission> permissions = new HashSet<>();

        // User permissions
        permissions.add(CAN_VIEW_USER);
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_UPDATE_USER);

        // UserToken permissions
        permissions.add(CAN_REQUEST_USER_TOKEN);
        permissions.add(CAN_VERIFY_USER_TOKEN);

        return permissions;
    }

    private static Set<Permission> getAdminPermissions() {
        return getUserPermissions();
    }

    private static Set<Permission> getSuperAdminPermissions() {
        return getAllPermissions();
    }

}
