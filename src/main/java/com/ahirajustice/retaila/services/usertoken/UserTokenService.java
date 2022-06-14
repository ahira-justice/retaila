package com.ahirajustice.retaila.services.usertoken;

import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.requests.usertoken.VerifyUserTokenRequest;
import com.ahirajustice.retaila.enums.UserTokenType;

public interface UserTokenService {

    String generateToken(User user, UserTokenType tokenType, long validityInSecs);

    void verifyToken(VerifyUserTokenRequest request);

    void useToken(User user, UserTokenType tokenType, String token);

}
