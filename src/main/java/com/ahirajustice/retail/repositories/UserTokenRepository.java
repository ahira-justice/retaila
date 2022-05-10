package com.ahirajustice.retail.repositories;


import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.entities.UserToken;
import com.ahirajustice.retail.enums.UserTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findFirstByUserAndTokenType(User user, UserTokenType tokenType);

    void deleteByUserAndTokenType(User user, UserTokenType tokenType);
}
