package com.ahirajustice.retail.services.usertoken.impl;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.config.AppConfig;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.entities.UserToken;
import com.ahirajustice.retail.enums.UserTokenType;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.repositories.UserTokenRepository;
import com.ahirajustice.retail.dtos.usertoken.VerifyUserTokenRequest;
import com.ahirajustice.retail.services.user.UserService;
import com.ahirajustice.retail.services.usertoken.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserTokenServiceImpl implements UserTokenService {

    private final AppConfig appConfig;
    private final UserTokenRepository userTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateToken(long userId, UserTokenType tokenType, long expiryInSeconds) {
        User user = userService.verifyUserExists(userId);
        validateExpiry(expiryInSeconds);

        deleteOldTokenIfExists(userId, tokenType);

        String token = generateToken();
        log.info("Generated token for {}: {} ({})", user.getUsername(), token, tokenType);
        String hashedToken = passwordEncoder.encode(token);

        LocalDateTime expiry = LocalDateTime.now().plusSeconds(expiryInSeconds);

        UserToken userToken = UserToken.builder().user(user).tokenType(tokenType).token(hashedToken).expiry(expiry).isValid(true).build();

        userTokenRepository.save(userToken);

        return token;
    }

    @Override
    public boolean verifyToken(VerifyUserTokenRequest request) {
        User user = userService.verifyUserExists(request.getUsername());

        if (!EnumUtils.isValidEnum(UserTokenType.class, request.getTokenType())){
            throw new ValidationException("Invalid tokenType");
        }

        return verifyToken(user.getId(), UserTokenType.valueOf(request.getTokenType()), request.getToken());
    }

    @Override
    public void useToken(long userId, UserTokenType tokenType, String token) {
        boolean isValid = verifyToken(userId, tokenType, token);
        if (!isValid) {
            throw new ValidationException("Token does not match available token");
        }

        userTokenRepository.deleteByUser_IdAndTokenType(userId, tokenType);
    }

    private boolean verifyToken(long userId, UserTokenType tokenType, String token) {
        Optional<UserToken> userTokenExists = userTokenRepository.findFirstByUser_IdAndTokenType(userId, tokenType);

        if (!userTokenExists.isPresent()) {
            throw new ValidationException(String.format("Token for token type: '%s' does not exist for given user", tokenType));
        }

        UserToken userToken = userTokenExists.get();

        if (LocalDateTime.now().isAfter(userToken.getExpiry())) {
            throw new ValidationException("Token has expired");
        }

        return passwordEncoder.matches(token, userToken.getToken());
    }

    private void validateExpiry(long validityInSecs) {
        if (validityInSecs <= 0)
            throw new IllegalArgumentException("Validity (in seconds) must be greater than 0");
    }

    private void deleteOldTokenIfExists(long userId, UserTokenType tokenType) {
        userTokenRepository.deleteByUser_IdAndTokenType(userId, tokenType);
    }

    private String generateToken() {
        return CommonHelper.generateRandomString(appConfig.USER_TOKEN_LENGTH, appConfig.USER_TOKEN_KEYSPACE);
    }
}
