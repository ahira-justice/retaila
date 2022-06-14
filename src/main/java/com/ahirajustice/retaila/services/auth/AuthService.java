package com.ahirajustice.retaila.services.auth;

import com.ahirajustice.retaila.dtos.auth.AuthToken;
import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;
import com.ahirajustice.retaila.requests.auth.ForgotPasswordRequest;
import com.ahirajustice.retaila.requests.auth.LoginRequest;
import com.ahirajustice.retaila.requests.auth.ResetPasswordRequest;
import com.ahirajustice.retaila.viewmodels.auth.LoginResponse;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse externalLogin(ExternalLoginRequest request);

    AuthToken decodeJwt(String token);

    void forgotPassword(ForgotPasswordRequest request);

    UserViewModel resetPassword(ResetPasswordRequest request);

}
