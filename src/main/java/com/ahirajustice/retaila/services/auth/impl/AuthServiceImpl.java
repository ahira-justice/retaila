package com.ahirajustice.retaila.services.auth.impl;

import com.ahirajustice.retaila.common.CommonHelper;
import com.ahirajustice.retaila.constants.SecurityConstants;
import com.ahirajustice.retaila.dtos.auth.AuthToken;
import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.enums.TimeFactor;
import com.ahirajustice.retaila.enums.UserTokenType;
import com.ahirajustice.retaila.exceptions.UnauthorizedException;
import com.ahirajustice.retaila.properties.AppProperties;
import com.ahirajustice.retaila.repositories.UserRepository;
import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;
import com.ahirajustice.retaila.requests.auth.ForgotPasswordRequest;
import com.ahirajustice.retaila.requests.auth.LoginRequest;
import com.ahirajustice.retaila.requests.auth.ResetPasswordRequest;
import com.ahirajustice.retaila.services.auth.AuthService;
import com.ahirajustice.retaila.services.user.UserService;
import com.ahirajustice.retaila.services.usertoken.UserTokenMailingService;
import com.ahirajustice.retaila.services.usertoken.UserTokenService;
import com.ahirajustice.retaila.validators.ValidatorUtils;
import com.ahirajustice.retaila.validators.auth.ExternalLoginRequestValidator;
import com.ahirajustice.retaila.validators.auth.ForgotPasswordRequestValidator;
import com.ahirajustice.retaila.validators.auth.LoginRequestValidator;
import com.ahirajustice.retaila.validators.auth.ResetPasswordRequestValidator;
import com.ahirajustice.retaila.viewmodels.auth.LoginResponse;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserTokenService userTokenService;
    private final UserTokenMailingService userTokenMailingService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    public LoginResponse login(LoginRequest request) {
        ValidatorUtils<LoginRequest> validator = new ValidatorUtils<>();
        validator.validate(new LoginRequestValidator(), request);

        authenticateUser(request.getUsername(), request.getPassword());

        return generateAuthToken(request.getUsername(), request.getExpires());
    }

    @Override
    public LoginResponse externalLogin(ExternalLoginRequest request) {
        ValidatorUtils<ExternalLoginRequest> validator = new ValidatorUtils<>();
        validator.validate(new ExternalLoginRequestValidator(), request);

        Optional<User> userExists = userRepository.findByUsername(request.getUsername());

        if (!userExists.isPresent()) {
            userService.createSocialUser(request);
        }

        return generateAuthToken(request.getUsername(), request.getExpires());
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        try{
            Claims claims = Jwts.parser().setSigningKey(appProperties.getSecretKey()).parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setExpiry(claims.getExpiration());
        }
        catch(ExpiredJwtException ex){
            return authToken;
        }

        return authToken;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        ValidatorUtils<ForgotPasswordRequest> validator = new ValidatorUtils<>();
        validator.validate(new ForgotPasswordRequestValidator(), request);

        User user = userService.verifyUserExists(request.getUsername());

        String token = userTokenService.generateToken(user, UserTokenType.RESET_PASSWORD, appProperties.getUserTokenValidityInSeconds());

        userTokenMailingService.sendOtpEmailToUser(token, user, UserTokenType.RESET_PASSWORD);
    }

    @Override
    public UserViewModel resetPassword(ResetPasswordRequest request) {
        ValidatorUtils<ResetPasswordRequest> validator = new ValidatorUtils<>();
        validator.validate(new ResetPasswordRequestValidator(), request);

        User user = userService.verifyUserExists(request.getUsername());

        userTokenService.useToken(user, UserTokenType.RESET_PASSWORD, request.getToken());

        return userService.setUserPassword(user, request.getPassword());
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    private void authenticateUser(String username, String password) {
        Optional<User> userExists = userRepository.findByUsername(username);

        if (!userExists.isPresent()) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        if (!verifyPassword(password, userExists.get().getPassword())) {
            throw new UnauthorizedException("Incorrect username or password");
        }
    }

    private LoginResponse generateAuthToken(String subject, int expires) {
        int expiry = expires > 0 ? expires : appProperties.getAccessTokenExpireMinutes();

        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + CommonHelper.convertToMillis(expiry, TimeFactor.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, appProperties.getSecretKey()).compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

}
