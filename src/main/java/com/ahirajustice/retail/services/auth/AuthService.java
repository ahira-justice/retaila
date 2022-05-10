package com.ahirajustice.retail.services.auth;

import com.ahirajustice.retail.dtos.auth.AuthToken;
import com.ahirajustice.retail.requests.auth.ForgotPasswordRequest;
import com.ahirajustice.retail.requests.auth.LoginRequest;
import com.ahirajustice.retail.requests.auth.ResetPasswordRequest;
import com.ahirajustice.retail.viewmodels.auth.LoginResponse;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    AuthToken decodeJwt(String token);

    void forgotPassword(ForgotPasswordRequest request);

    UserViewModel resetPassword(ResetPasswordRequest request);

}
