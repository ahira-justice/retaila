package com.ahirajustice.retail.services.usertoken;

import com.ahirajustice.retail.enums.UserTokenType;
import com.ahirajustice.retail.dtos.usertoken.VerifyUserTokenRequest;

public interface UserTokenService {

    String generateToken(long userId, UserTokenType tokenType, long validityInSecs);

    boolean verifyToken(VerifyUserTokenRequest request);

    void useToken(long userId, UserTokenType tokenType, String token);

}
