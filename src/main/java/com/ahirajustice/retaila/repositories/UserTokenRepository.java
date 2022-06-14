package com.ahirajustice.retaila.repositories;


import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.entities.UserToken;
import com.ahirajustice.retaila.enums.UserTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findFirstByUserAndTokenType(User user, UserTokenType tokenType);

    void deleteByUserAndTokenType(User user, UserTokenType tokenType);
}
