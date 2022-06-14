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

            String username = getUsernameFromToken(header);
            Optional<User> userExists = userRepository.findByUsername(username);

            if (!userExists.isPresent()) {
                throw new ValidationException(String.format("User with username: '%s' specified in access token does not exist", username));
            }

            return userExists.get();
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ValidationException("Error getting HttpServletRequest");
        }
    }

    private String getUsernameFromToken(String header) {
        if (header == null) {
            return null;
        }

        String token = header.split(" ")[1];

        return Jwts.parser().setSigningKey(appProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

}
