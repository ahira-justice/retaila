package com.ahirajustice.retail.repositories;

import java.util.Optional;

import com.ahirajustice.retail.entities.Permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

}