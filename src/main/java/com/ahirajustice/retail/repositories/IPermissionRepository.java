package com.ahirajustice.retail.repositories;

import java.util.Optional;

import com.ahirajustice.retail.entities.Permission;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("IPermissionRepository")
public interface IPermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

}