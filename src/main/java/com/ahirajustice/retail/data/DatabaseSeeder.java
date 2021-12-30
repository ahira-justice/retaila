package com.ahirajustice.retail.data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.ahirajustice.retail.config.AppConfig;
import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.entities.Role;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.Roles;
import com.ahirajustice.retail.repositories.IPermissionRepository;
import com.ahirajustice.retail.repositories.IRoleRepository;
import com.ahirajustice.retail.repositories.IUserRepository;
import com.ahirajustice.retail.security.PermissionsProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private IPermissionRepository permissionRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private AppConfig appConfig;

    @Autowired
    public DatabaseSeeder(IUserRepository userRepository, IRoleRepository roleRepository,
            IPermissionRepository permissionRepository, BCryptPasswordEncoder passwordEncoder, AppConfig appConfig) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.appConfig = appConfig;
    }

    private void installPermissions() {
        Set<Permission> permissions = PermissionsProvider.getAllPermissions();

        for (Permission permission : permissions) {
            try {
                Optional<Permission> permissionExists = permissionRepository.findByName(permission.getName());

                if (permissionExists.isPresent()) {
                    continue;
                }

                permissionRepository.save(permission);
            } catch (Exception ex) {
                continue;
            }
        }
    }

    private void installDefaultRoles() {
        Set<Role> roles = PermissionsProvider.getDefaultRoles();

        Iterable<Permission> permissions = permissionRepository.findAll();

        for (Role role : roles) {
            Optional<Role> roleExists = roleRepository.findByName(role.getName());

            Set<Permission> rolePermissions = new HashSet<Permission>();

            for (Permission rolePermission : role.getPermissions()) {
                for (Permission permission : permissions) {
                    if (permission.getName().equals(rolePermission.getName())) {
                        rolePermissions.add(permission);
                    }
                }
            }

            if (roleExists.isPresent()) {
                Role currentRole = roleExists.get();
                currentRole.setPermissions(rolePermissions);
                roleRepository.save(currentRole);

            } else {
                role.setPermissions(rolePermissions);
                roleRepository.save(role);
            }
        }
    }

    private void seedSuperAdminUser() {
        try {
            Optional<User> superAdminExists = userRepository.findByEmail(appConfig.SUPERUSER_EMAIL);

            if (superAdminExists.isPresent()) {
                return;
            }

            User superAdmin = new User();
            Role superAdminRole = roleRepository.findByName(Roles.SUPERADMIN.name()).get();
            superAdmin.setEmail(appConfig.SUPERUSER_EMAIL);
            superAdmin.setFirstName(appConfig.SUPERUSER_FIRST_NAME);
            superAdmin.setLastName(appConfig.SUPERUSER_LAST_NAME);
            superAdmin.setEmailVerificationStatus(true);
            superAdmin.setEncryptedPassword(passwordEncoder.encode(appConfig.SUPERUSER_PASSWORD));
            superAdmin.setRole(superAdminRole);

            userRepository.save(superAdmin);
        } catch (Exception ex) {
            return;
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        installPermissions();
        installDefaultRoles();
        seedSuperAdminUser();
    }

}
