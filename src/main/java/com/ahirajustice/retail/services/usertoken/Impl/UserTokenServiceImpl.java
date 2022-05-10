package com.ahirajustice.retail.services.usertoken.impl;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.requests.usertoken.VerifyUserTokenRequest;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.entities.UserToken;
import com.ahirajustice.retail.enums.UserTokenType;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.properties.AppProperties;
import com.ahirajustice.retail.repositories.UserTokenRepository;
import com.ahirajustice.retail.services.user.UserService;
import com.ahirajustice.retail.services.usertoken.UserTokenService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.usertoken.VerifyUserTokenRequestValidator;
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

    private final AppProperties appProperties;
    private final UserTokenRepository userTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateToken(User user, UserTokenType tokenType, long expiryInSeconds) {
        validateExpiry(expiryInSeconds);

        deleteOldTokenIfExists(user, tokenType);

        String token = generateToken();
        log.info("Generated token for {}: {} ({})", user.getUsername(), token, tokenType);
        String hashedToken = passwordEncoder.encode(token);

        LocalDateTime expiry = LocalDateTime.now().plusSeconds(expiryInSeconds);

        UserToken userToken = UserToken.builder().user(user).tokenType(tokenType).token(hashedToken).expiry(expiry).isValid(true).build();

        userTokenRepository.save(userToken);

        return token;
    }

    @Override
    public void verifyToken(VerifyUserTokenRequest request) {
        ValidatorUtils<VerifyUserTokenRequest> validator = new ValidatorUtils<>();
        validator.validate(new VerifyUserTokenRequestValidator(), request);

        User user = userService.verifyUserExists(request.getUsername());

        if (!EnumUtils.isValidEnum(UserTokenType.class, request.getTokenType())){
            throw new ValidationException("Invalid tokenType");
        }

        verifyToken(user, UserTokenType.valueOf(request.getTokenType()), request.getToken());
    }

    @Override
    public void useToken(User user, UserTokenType tokenType, String token) {
        boolean isValid = verifyToken(user, tokenType, token);
        if (!isValid) {
            throw new ValidationException("Token does not match available token");
        }

        userTokenRepository.deleteByUserAndTokenType(user, tokenType);
    }

    private boolean verifyToken(User user, UserTokenType tokenType, String token) {
        Optional<UserToken> userTokenExists = userTokenRepository.findFirstByUserAndTokenType(user, tokenType);

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

    private void deleteOldTokenIfExists(User user, UserTokenType tokenType) {
        userTokenRepository.deleteByUserAndTokenType(user, tokenType);
    }

    private String generateToken() {
        return CommonHelper.generateRandomString(appProperties.getUserTokenLength(), appProperties.getUserTokenKeyspace());
    }
}
