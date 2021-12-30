package com.ahirajustice.retail.repositories;

import java.util.Optional;

import com.ahirajustice.retail.entities.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("IRoleRepository")
public interface IRoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}