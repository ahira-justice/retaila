package com.ahirajustice.retaila.services.user.impl;

import com.ahirajustice.retaila.constants.SecurityConstants;
import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.exceptions.ValidationException;
import com.ahirajustice.retaila.properties.AppProperties;
import com.ahirajustice.retaila.repositories.UserRepository;
import com.ahirajustice.retaila.services.user.CurrentUserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserServiceImpl implements CurrentUserService {

    private final AppProperties appProperties;
    private final HttpServletRequest request;
    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            Optional<String> usernameExists = getUsernameFromToken(header);
            if (!usernameExists.isPresent())
                throw new ValidationException("Invalid access token");

            String username = usernameExists.get();
            Optional<User> userExists = userRepository.findByUsername(username);

            if (!userExists.isPresent())
                throw new ValidationException(String.format("User with username: '%s' specified in access token does not exist", username));

            return userExists.get();
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ValidationException("Error getting HttpServletRequest");
        }
    }

    private Optional<String> getUsernameFromToken(String header) {
        if (StringUtils.isNotBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String username = Jwts.parser().setSigningKey(appProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();

        return Optional.ofNullable(username);
    }

}
