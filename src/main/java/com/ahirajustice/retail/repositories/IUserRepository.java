package com.ahirajustice.retail.repositories;

import java.util.Optional;

import com.ahirajustice.retail.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("IUserRepository")
public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
