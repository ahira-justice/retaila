package com.ahirajustice.retail.services.auth.impl;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.config.AppConfig;
import com.ahirajustice.retail.config.SpringApplicationContext;
import com.ahirajustice.retail.constants.SecurityConstants;
import com.ahirajustice.retail.dtos.auth.AuthToken;
import com.ahirajustice.retail.dtos.auth.LoginDto;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.TimeFactor;
import com.ahirajustice.retail.repositories.UserRepository;
import com.ahirajustice.retail.services.auth.AuthService;
import com.ahirajustice.retail.viewmodels.auth.LoginResponse;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Override
    public LoginResponse createAccessToken(LoginDto loginDto) {
        String subject = loginDto.getEmail();
        int expiry = loginDto.getExpires() > 0 ? loginDto.getExpires() : appConfig.ACCESS_TOKEN_EXPIRE_MINUTES;

        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + CommonHelper.convertToMillis(expiry, TimeFactor.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, appConfig.SECRET_KEY).compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    @Override
    public boolean authenticateUser(LoginDto loginDto) {
        Optional<User> userExists = userRepository.findByEmail(loginDto.getEmail());

        if (!userExists.isPresent()) {
            return false;
        }

        if (!verifyPassword(loginDto.getPassword(), userExists.get().getEncryptedPassword())) {
            return false;
        }

        return true;
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("appConfig");

        try{
            Claims claims = Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setExpiry(claims.getExpiration());
        }
        catch(ExpiredJwtException ex){
            return authToken;
        }

        return authToken;
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

}
