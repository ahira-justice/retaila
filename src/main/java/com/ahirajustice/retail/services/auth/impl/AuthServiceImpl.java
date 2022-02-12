package com.ahirajustice.retail.services.auth.impl;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.config.AppConfig;
import com.ahirajustice.retail.config.SpringApplicationContext;
import com.ahirajustice.retail.constants.SecurityConstants;
import com.ahirajustice.retail.dtos.auth.AuthToken;
import com.ahirajustice.retail.dtos.auth.ForgotPasswordRequest;
import com.ahirajustice.retail.dtos.auth.LoginDto;
import com.ahirajustice.retail.dtos.auth.ResetPasswordRequest;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.TimeFactor;
import com.ahirajustice.retail.enums.UserTokenType;
import com.ahirajustice.retail.exceptions.UnauthorizedException;
import com.ahirajustice.retail.repositories.UserRepository;
import com.ahirajustice.retail.services.auth.AuthService;
import com.ahirajustice.retail.services.user.UserService;
import com.ahirajustice.retail.services.usertoken.UserTokenMailingService;
import com.ahirajustice.retail.services.usertoken.UserTokenService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.auth.ForgotPasswordRequestValidator;
import com.ahirajustice.retail.validators.auth.LoginDtoValidator;
import com.ahirajustice.retail.validators.auth.ResetPasswordRequestValidator;
import com.ahirajustice.retail.viewmodels.auth.LoginResponse;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;
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
    private final AppConfig appConfig;

    @Override
    public LoginResponse createAccessToken(LoginDto loginDto) {
        ValidatorUtils<LoginDto> validator = new ValidatorUtils<>();
        validator.validate(new LoginDtoValidator(), loginDto);

        authenticateUser(loginDto);

        String subject = loginDto.getUsername();

        int expiry = loginDto.getExpires() > 0 ? loginDto.getExpires() : appConfig.getAccessTokenExpireMinutes();

        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + CommonHelper.convertToMillis(expiry, TimeFactor.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, appConfig.getSecretKey()).compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("appConfig");

        try{
            Claims claims = Jwts.parser().setSigningKey(appConfig.getSecretKey()).parseClaimsJws(token).getBody();
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

        String token = userTokenService.generateToken(user.getId(), UserTokenType.RESET_PASSWORD, appConfig.getUserTokenValidityInSeconds());

        userTokenMailingService.sendOtpEmailToUser(token, user.getId());
    }

    @Override
    public UserViewModel resetPassword(ResetPasswordRequest request) {
        ValidatorUtils<ResetPasswordRequest> validator = new ValidatorUtils<>();
        validator.validate(new ResetPasswordRequestValidator(), request);

        User user = userService.verifyUserExists(request.getUsername());

        userTokenService.useToken(user.getId(), UserTokenType.RESET_PASSWORD, request.getToken());

        return userService.setUserPassword(user, request.getPassword());
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    private void authenticateUser(LoginDto loginDto) {
        Optional<User> userExists = userRepository.findByUsername(loginDto.getUsername());

        if (!userExists.isPresent()) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        if (!verifyPassword(loginDto.getPassword(), userExists.get().getPassword())) {
            throw new UnauthorizedException("Incorrect username or password");
        }
    }

}
