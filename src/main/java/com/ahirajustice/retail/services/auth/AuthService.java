package com.ahirajustice.retail.services.auth;

import com.ahirajustice.retail.dtos.auth.AuthToken;
import com.ahirajustice.retail.dtos.auth.LoginDto;
import com.ahirajustice.retail.viewmodels.auth.LoginResponse;

public interface AuthService {

    LoginResponse createAccessToken(LoginDto loginDto);

    AuthToken decodeJwt(String token);

}
