package com.ahirajustice.retail.repositories;


import com.ahirajustice.retail.entities.UserToken;
import com.ahirajustice.retail.enums.UserTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findFirstByUser_IdAndTokenType(long userId, UserTokenType tokenType);

    void deleteByUser_IdAndTokenType(long userId, UserTokenType tokenType);
}
