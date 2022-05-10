package com.ahirajustice.retail.services.usertoken;

import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.requests.usertoken.VerifyUserTokenRequest;
import com.ahirajustice.retail.enums.UserTokenType;

public interface UserTokenService {

    String generateToken(User user, UserTokenType tokenType, long validityInSecs);

    void verifyToken(VerifyUserTokenRequest request);

    void useToken(User user, UserTokenType tokenType, String token);

}
