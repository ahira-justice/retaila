package com.ahirajustice.retail.repositories;

import com.ahirajustice.retail.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, QuerydslPredicateExecutor<Client> {

    boolean existsByName(String name);

    Optional<Client> findByIdentifier(String identifier);

}
